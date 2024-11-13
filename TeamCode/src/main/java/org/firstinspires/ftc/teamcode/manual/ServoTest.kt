package org.firstinspires.ftc.teamcode.manual

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp
class ServoTest : LinearOpMode() {
    override fun runOpMode() {
        val servo = hardwareMap.servo.get("claw")
        waitForStart()
        while (opModeIsActive()) {
            servo.position = ((gamepad1.left_stick_y+1)/2).toDouble()
            telemetry.addData("Servo Position", ((gamepad1.left_stick_y+1)/2).toDouble())
            telemetry.update()
        }
    }
}