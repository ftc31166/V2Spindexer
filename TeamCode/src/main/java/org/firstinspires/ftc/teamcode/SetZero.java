package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class SetZero extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor hang = hardwareMap.dcMotor.get("hang");
        Servo servo1 = hardwareMap.servo.get("servo1");
        double targetPos = 0;
        double currentPos = 0;

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.left_bumper) {
                servo1.setPosition(1);
                telemetry.addLine("servo up");
                telemetry.update();
            }
            if (gamepad1.right_bumper) {
                servo1.setPosition(0);
                telemetry.addLine("servo down");
                telemetry.update();
            }
            if (gamepad1.right_trigger > 0.5) {
                hang.setPower(1);
                telemetry.addLine("hang up");
                telemetry.update();
            }
            if (gamepad1.left_trigger > 0.5) {
                hang.setPower(-1);
                telemetry.addLine("hang down");
                telemetry.update();
            }

        }

        while (true) {
            sleep(20);
        }
    }
}
