package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.ElapsedTime
import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx
import dev.frozenmilk.dairy.cachinghardware.CachingServo
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.vision.BlockDetectorProcessor
import org.firstinspires.ftc.vision.VisionPortal
import kotlin.math.abs


class Scoring(
    hardwareMap: HardwareMap,
    private var telemetry: Telemetry,
    private val gamepad1: Gamepad,
    private val gamepad2: Gamepad) {
    var readyForTransfer = false
    private val timeSinceStateChange = ElapsedTime()
    private val timer = ElapsedTime()
    private val wrist: CachingServo = CachingServo(hardwareMap.servo["wristI"])
    private val claw: CachingServo = CachingServo(hardwareMap.servo["clawI"])
    private val CV4B0I: CachingServo = CachingServo(hardwareMap.servo["CV4B0I"])
    private val CV4B1I: CachingServo = CachingServo(hardwareMap.servo["CV4B1I"])
    val blockDetector = BlockDetectorProcessor(telemetry)
    private var wristPosition = 0.5
    //TODO CV4B0I: Transfer 0.0, Scoring 0.9
    //TODO CV4B1I: Transfer 0.88, Scoring 0.05
    //TODO FIX GRABBING LOGIC TO USE COLOR instead of distance.
    var visionPortal: VisionPortal = org.firstinspires.ftc.vision.VisionPortal.Builder()
        .setCamera(hardwareMap.get<WebcamName>(WebcamName::class.java, "Webcam 1"))
        .addProcessor(blockDetector)
        .build()

    private enum class State {
        IDLE,
        EXTENDING,
        ALIGNING,
        GRABBING,
        GRABBED,
    }
    val horz = CachingDcMotorEx(hardwareMap.dcMotor["horz"] as DcMotorEx)
    val vert1 = CachingDcMotorEx(hardwareMap.dcMotor["vert1"] as DcMotorEx)
    val vert0 = CachingDcMotorEx(hardwareMap.dcMotor["vert0"] as DcMotorEx)

    val pid = PIDController(0.02,0.0,0.001)
    var target = 0.0
    private var state = State.ALIGNING
    var pin0 = hardwareMap.digitalChannel["i0"]
    var pin1 = hardwareMap.digitalChannel["i1"]

    init {
        horz.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        horz.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        vert0.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        vert1.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        horz.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        vert1.direction = DcMotorSimple.Direction.REVERSE
        vert0.direction = DcMotorSimple.Direction.FORWARD
    }

    fun update() {
        val block = blockDetector.closestBlock
        val angle = blockDetector.angle
        val size = blockDetector.size
        if (abs(horz.currentPosition.toDouble() - target) > 10) {
            horz.power = pid.calculate(horz.currentPosition.toDouble(), target)
        } else {
            horz.power = 0.0
        }
        if (gamepad2.left_bumper) {
            claw.position = 0.4
        }
        if (gamepad2.right_bumper) {
            claw.position = 1.0
        }
        wrist.position = wristPosition
        //horz.power = gamepad2.right_stick_y.toDouble()
        when (state) {
            State.ALIGNING -> {
                state = State.GRABBING
            }
            State.GRABBING -> {
                CV4B1I.position = 0.05
                target= 200.0
                var deltaW = 0.0
                if (size > 5000) {
                    if (angle in 90.0..180.0) {
                        deltaW = (90 - (angle - 90)) / 90 / 20
                        wristPosition -= deltaW
                    } else if (angle in 0.0..90.0) {
                        deltaW = angle / 90 / 20
                        wristPosition += deltaW
                    }
                }
                if (gamepad2.a) {
                    timer.reset()
                    CV4B0I.position = 0.85
                } else {
                    CV4B0I.position = 0.6
                }

                if (gamepad2.x) {
                    state = State.GRABBED
                }
                //wrist.position = (gamepad2.left_stick_x.toDouble() + 1.0) / 2
            }
            State.GRABBED -> {
                target = 0.0
                CV4B1I.position = 0.85
                CV4B0I.position = 0.0
                if (gamepad2.b) {
                    state = State.IDLE
                    timeSinceStateChange.reset()
                }
                if (gamepad2.y) {
                    claw.position = 0.4
                }
                //wristPosition = 0.5
            }

            State.IDLE -> {
                target = 0.0
            }
            State.EXTENDING -> TODO()
        }
        telemetry.addData("State", state)
        telemetry.addData("Wrist Position", wristPosition)
        telemetry.addData("Block Angle", angle)
        telemetry.addData("Block Size", size)
        telemetry.addData("Time", timeSinceStateChange.seconds())
        telemetry.addData("Horz Encoder", horz.currentPosition)
        vert0.power = gamepad2.right_stick_y.toDouble()
        vert1.power = gamepad2.right_stick_y.toDouble()
        telemetry.addData("Vertical Power", vert0.power)
        telemetry.addData("FPS", visionPortal.fps)

    }
}