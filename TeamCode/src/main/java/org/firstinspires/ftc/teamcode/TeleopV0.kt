package org.firstinspires.ftc.teamcode

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.subsystems.Scoring
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


@TeleOp
class TeleopV0 : LinearOpMode() {



    private enum class State {
        INTAKING_SAMPLE,
        TRANSFERRING,
        OUTTAKING_SAMPLE,
        INTAKING_SPECIMEN,
        OUTTAKING_SPECIMEN,
    }

    private var targetHeading: Double? = null

    val DT_ACTIVE = true
    override fun runOpMode() {
        val scoring = Scoring(hardwareMap, telemetry, gamepad1, gamepad2)
        val backLeft = hardwareMap.dcMotor.get("backLeft")
        val backRight = hardwareMap.dcMotor.get("backRight")
        val frontLeft = hardwareMap.dcMotor.get("frontLeft")
        val frontRight = hardwareMap.dcMotor.get("frontRight")
        frontLeft.direction = DcMotorSimple.Direction.REVERSE
        backLeft.direction = DcMotorSimple.Direction.REVERSE
        backLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        backRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        frontLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        frontRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE


        val imu = hardwareMap.get(IMU::class.java, "imu")

        // Adjust the orientation parameters to match your robot
        val parameters = IMU.Parameters(
            RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
            )
        )

        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters)

        var state = State.INTAKING_SAMPLE

        val horz = hardwareMap.dcMotor["horz"]
        horz.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        horz.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        val pid = PIDController(0.03,0.0,0.001)
        var horztarget = 15.0

        waitForStart()
        imu.resetYaw()
        var headingLockActive = false
        var lastTurnInputTime = System.currentTimeMillis()
        while (opModeIsActive()) {
            when (state) {
                State.INTAKING_SAMPLE -> {
                    scoring.update()
                    if (scoring.readyForTransfer) {
                        state = State.TRANSFERRING
                    }
                }

                State.TRANSFERRING -> {
                    //state = State.OUTTAKING_SAMPLE
                    state = State.INTAKING_SAMPLE
                }
                State.OUTTAKING_SAMPLE -> TODO()
                State.INTAKING_SPECIMEN -> TODO()
                State.OUTTAKING_SPECIMEN -> TODO()
            }

            if (gamepad1.a) {
                horztarget = 200.0
            }
            if (gamepad1.b) {
                horztarget = 15.0
            }

            if (abs(horz.currentPosition.toDouble() - horztarget) > 2) {
                horz.power = pid.calculate(horz.currentPosition.toDouble(), horztarget)
            } else {
                horz.power = 0.0
            }
            val y = gamepad1.left_stick_y.toDouble() // Remember, Y stick value is reversed
            val x = gamepad1.left_stick_x * 1.1 // Counteract imperfect strafing
            var rx = gamepad1.right_stick_x.toDouble()

            if (abs(rx) < 0.1) {
                lastTurnInputTime = System.currentTimeMillis()
                headingLockActive = true
            } else {
                headingLockActive = false
                targetHeading = null
            }
            if ((System.currentTimeMillis() - lastTurnInputTime) < 50) {
                headingLockActive = true
                if (targetHeading == null) {
                    targetHeading = imu.robotYawPitchRollAngles.getYaw(AngleUnit.RADIANS)
                }
            }

            val botHeading: Double = imu.robotYawPitchRollAngles.getYaw(AngleUnit.RADIANS)

            if (headingLockActive) {
                val headingError = botHeading - targetHeading!!
                //rx = headingError * 0.5 // Adjust the gain as needed
            }



            val rotX = x * cos(botHeading) - y * sin(botHeading)
            val rotY = x * sin(botHeading) + y * cos(botHeading)

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            var denominator: Double = Math.max(abs(rotY) + abs(rotX) + abs(rx), 1.0)
            var frontLeftPower = (rotY + rotX + rx) / denominator
            var backLeftPower = (rotY - rotX + rx) / denominator
            var frontRightPower = (rotY - rotX - rx) / denominator
            var backRightPower = (rotY + rotX - rx) / denominator

            if (DT_ACTIVE) {
                frontLeft.power = frontLeftPower
                backLeft.power = backLeftPower
                frontRight.power = frontRightPower
                backRight.power = backRightPower
            } else {
                frontLeft.power = 0.0
                backLeft.power = 0.0
                frontRight.power = 0.0
                backRight.power = 0.0
            }
            telemetry.addData("Target Heading", targetHeading)
            telemetry.addData("Bot Heading", botHeading)
            telemetry.addData("Time", System.currentTimeMillis() - lastTurnInputTime)
            telemetry.update()

        }
    }

}
