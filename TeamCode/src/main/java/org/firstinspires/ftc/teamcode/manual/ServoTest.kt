package org.firstinspires.ftc.teamcode.manual

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp
class ServoTest : LinearOpMode() {
    override fun runOpMode() {
        val servo = hardwareMap.servo.get("CV4B1I")
        val analog = hardwareMap.analogInput.get("CV4B0IA")
        waitForStart()
        while (opModeIsActive()) {
            servo.position = (gamepad1.left_stick_y + 1.0) /2
            telemetry.addData("Servo Position", servo.position)
            telemetry.addData("Analog Input", (analog.voltage/3.3))
            telemetry.update()
        }
    }
}