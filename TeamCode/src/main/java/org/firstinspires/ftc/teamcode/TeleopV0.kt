package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Intake

@TeleOp
class TeleopV0 : LinearOpMode() {
    override fun runOpMode() {
        val intake = Intake(hardwareMap, telemetry, gamepad1, gamepad2)
        waitForStart()
        while (opModeIsActive()) {
            intake.update()
            telemetry.update()
        }
    }

}
