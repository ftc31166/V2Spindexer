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
        double kP = 0.015;
        int hangTarget = 0;
        DcMotor hang = hardwareMap.dcMotor.get("hang");
        Servo servo1 = hardwareMap.servo.get("servo1");
        double targetPos = 0;
        double currentPos = 0;

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.left_bumper) {
                hangTarget += 15;
            } else if (gamepad1.right_bumper && hangTarget > 15) {
                hangTarget -= 15;
            }
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
            int hangError = hangTarget - hang.getCurrentPosition();
            hang.setPower(kP * hangError);
            telemetry.addData("hang pos", hang.getCurrentPosition());
            telemetry.addData("hang target", hangTarget);
            telemetry.update();

        }

        while (true) {
            sleep(20);
        }
    }
}
