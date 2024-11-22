package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.ServoImplEx
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.vision.BlockDetectorProcessor
import org.firstinspires.ftc.vision.VisionPortal
import kotlin.math.abs
import kotlin.math.min

class Scoring(
    hardwareMap: HardwareMap,
    private var telemetry: Telemetry,
    private val gamepad1: Gamepad,
    private val gamepad2: Gamepad) {
    var readyForTransfer = false
    private val timeSinceStateChange = ElapsedTime()
    private val timer = ElapsedTime()
    private val wrist: ServoImplEx = hardwareMap.get(ServoImplEx::class.java, "wristI")
    private val blockDetector: BlockDetectorProcessor = BlockDetectorProcessor(telemetry) // Assuming BlockDetectorProcessor is accessible
    private val claw = hardwareMap.get(ServoImplEx::class.java, "clawI")
    private val sensor = hardwareMap.get(RevColorSensorV3::class.java, "Color")
    private var wristPosition = 0.5
    private val CV4B0I = hardwareMap.servo.get("CV4B0I")
    private val CV4B1I = hardwareMap.servo.get("CV4B1I")
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



    private var state = State.ALIGNING

    fun update() {
        val block = blockDetector.closestBlock
        val angle = blockDetector.angle
        val size = blockDetector.size

        when (state) {
            State.ALIGNING -> {
                CV4B0I.position = 0.5
                CV4B1I.position = 0.05
                var deltaW = 0.7
                if (size > 5000) {
                    if (angle in 90.0..180.0) {
                        deltaW = (90 - (angle - 90)) / 90 / 20
                        wristPosition -= deltaW
                    } else if (angle in 0.0..90.0) {
                        deltaW = angle / 90 / 20
                        wristPosition += deltaW
                    }
                    claw.position = 0.55
                    if (min(angle, (90 - (angle - 90))) < 1) {
                        state = State.GRABBING
                        timeSinceStateChange.reset()
                    }
                }
            }
            State.GRABBING -> {
                if (timeSinceStateChange.seconds() > 1) {
                    state = State.ALIGNING
                    timeSinceStateChange.reset()
                }
                if (sensor.getDistance(DistanceUnit.MM) < 45) {
                    claw.position = 0.8
                    if (timeSinceStateChange.seconds() > 0.5) {
                        state = State.GRABBED
                        timeSinceStateChange.reset()
                    }
                } else {
                    claw.position = 0.55
                }
                if (gamepad1.a) {
                    state = State.ALIGNING
                    timeSinceStateChange.reset()
                }
                if (timeSinceStateChange.seconds() < 0.15) {
                    CV4B0I.position = 0.75
                } else {
                    CV4B0I.position = 0.79
                }
            }
            State.GRABBED -> {
                CV4B0I.position = 0.5
                if (gamepad1.a || (sensor.getDistance(DistanceUnit.MM) > 50 && timeSinceStateChange.seconds() > 0.5)) {
                    state = State.ALIGNING
                    timeSinceStateChange.reset()
                    claw.position = 0.7
                }
                wristPosition = 0.5
            }

            State.IDLE -> TODO()
            State.EXTENDING -> TODO()
        }
        telemetry.addData("State", state)
        telemetry.addData("Wrist Position", wristPosition)
        telemetry.addData("Block Angle", angle)
        telemetry.addData("Block Size", size)
        telemetry.addData("Block Distance", sensor.getDistance(DistanceUnit.MM))
        telemetry.addData("Time", timeSinceStateChange.seconds())
        readyForTransfer = state == State.GRABBED
        wrist.position = wristPosition
    }
}