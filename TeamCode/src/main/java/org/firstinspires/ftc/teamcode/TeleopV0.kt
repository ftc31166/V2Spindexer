package org.firstinspires.ftc.teamcode

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.hardware.lynx.LynxModule
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

    val DT_ACTIVE = true
    override fun runOpMode() {
        val allHubs : List<LynxModule> = hardwareMap.getAll(LynxModule::class.java)
        for (hub in allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

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

        var state = State.INTAKING_SAMPLE

        val horz = hardwareMap.dcMotor["horz"]
        horz.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        horz.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        var lastTime = 0.0

        waitForStart()
        while (opModeIsActive()) {

            when (state) {
                State.INTAKING_SAMPLE -> {
                    scoring.update()
                }
                State.OUTTAKING_SAMPLE -> TODO()
                State.INTAKING_SPECIMEN -> TODO()
                State.OUTTAKING_SPECIMEN -> TODO()
                State.TRANSFERRING -> TODO()
            }
            val y = -gamepad1.left_stick_y.toDouble() // Remember, Y stick value is reversed
            val x = -gamepad1.left_stick_x * 1.1 // Counteract imperfect strafing
            val rx = -gamepad1.right_stick_x.toDouble()
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            var denominator: Double = Math.max(abs(x) + abs(y) + abs(rx), 1.0)
            var frontLeftPower = (y + x + rx) / denominator
            var backLeftPower = (y - x + rx) / denominator
            var frontRightPower = (y - x - rx) / denominator
            var backRightPower = (y + x - rx) / denominator

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

            telemetry.addData("Time", System.currentTimeMillis() - lastTime)
            telemetry.update()
            lastTime = System.currentTimeMillis().toDouble()
            for (hub in allHubs) {
                hub.clearBulkCache()
            }
        }
    }

}
