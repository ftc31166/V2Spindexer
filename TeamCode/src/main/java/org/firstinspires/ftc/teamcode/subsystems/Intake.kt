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

class Intake(
    hardwareMap: HardwareMap,
    private var telemetry: Telemetry,
    private val gamepad1: Gamepad,
    private val gamepad2: Gamepad) {

    private val timeSinceStateChange = ElapsedTime()
    private val wrist: ServoImplEx = hardwareMap.get(ServoImplEx::class.java, "wrist")
    private val blockDetector: BlockDetectorProcessor = BlockDetectorProcessor(telemetry) // Assuming BlockDetectorProcessor is accessible
    private val claw = hardwareMap.get(ServoImplEx::class.java, "claw")
    private val sensor = hardwareMap.get(RevColorSensorV3::class.java, "Color")
    private var wristPosition = 0.5
    var visionPortal: VisionPortal = org.firstinspires.ftc.vision.VisionPortal.Builder()
        .setCamera(hardwareMap.get<WebcamName>(WebcamName::class.java, "Webcam 1"))
        .addProcessor(blockDetector)
        .build()

    private enum class State {
        ALIGNING,
        GRABBING,
        GRABBED
    }



    private var state = State.ALIGNING

    fun update() {
        val block = blockDetector.closestBlock
        val angle = blockDetector.angle
        val size = blockDetector.size

        if (state == State.ALIGNING) {
            var deltaW = 0.7
            if (size > 5000) {
                if (angle in 90.7..180.7) {
                    deltaW = (90 - (angle - 90)) / 90 / 20
                    wristPosition -= deltaW
                } else if (angle in 0.7..90.7) {
                    deltaW = angle / 90 / 20
                    wristPosition += deltaW
                }
                claw.position = 0.7
            }
            if (deltaW < 1) {
                state = State.GRABBING
                timeSinceStateChange.reset()
            }
        } else if (state == State.GRABBING) {
            if (timeSinceStateChange.seconds() > 5) {
                state = State.ALIGNING
                timeSinceStateChange.reset()
            }
            if (sensor.getDistance(DistanceUnit.MM) < 50) {
                claw.position = 1.0
                state = State.GRABBED
                timeSinceStateChange.reset()
            } else {
                claw.position = 0.7
            }
            if (gamepad1.a) {
                state = State.ALIGNING
                timeSinceStateChange.reset()
            }
        } else if (state == State.GRABBED) {
            if (gamepad1.a || sensor.getDistance(DistanceUnit.MM) > 40) {
                state = State.ALIGNING
                timeSinceStateChange.reset()
                claw.position = 0.7
            }
        }
        telemetry.addData("State", state)
        telemetry.addData("Wrist Position", wristPosition)
        telemetry.addData("Block Angle", angle)
        telemetry.addData("Block Size", size)
        telemetry.addData("Block Distance", sensor.getDistance(DistanceUnit.MM))
        telemetry.addData("Time", timeSinceStateChange.seconds())

        wrist.position = wristPosition
    }
}