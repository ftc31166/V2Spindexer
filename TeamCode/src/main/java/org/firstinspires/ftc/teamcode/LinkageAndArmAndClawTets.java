
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp
public class LinkageAndArmAndClawTets extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        int target=100;
        int extendo=0;
        int clawpos=1;
        boolean pressing=false;
        Servo linkage1 = hardwareMap.servo.get("linkage1");
        Servo linkage2 = hardwareMap.servo.get("linkage2");
        Servo claw = hardwareMap.servo.get("claw");
        DcMotorEx arm = hardwareMap.get(DcMotorEx.class,"arm");
        linkage1.setDirection(Servo.Direction.REVERSE);
        linkage1.setPosition(0);
        linkage2.setPosition(0);
        waitForStart();
        if (isStopRequested()) {return;}
        while (opModeIsActive()) {
            if (gamepad1.left_bumper){
                target-=15;
            }
            else if (gamepad1.right_bumper){
                target+=15;
            }
            if (gamepad1.left_trigger>0){
                extendo-=15;
            }
            else if (gamepad1.right_trigger>0){
                extendo+=15;
            }
            if (gamepad1.a){
                if (!pressing){
                    clawpos=Math.abs(1-clawpos);
                }
                pressing=true;
            }
            else{
                pressing=false;
            }

            int error1 = (target - arm.getCurrentPosition());
            arm.setPower(-error1 * 0.015);
            linkage1.setPosition(extendo);
            linkage1.setPosition(extendo);
            claw.setPosition(clawpos);
            telemetry.addData("armtarget", target);
            telemetry.addData("extendo", extendo);
            telemetry.addData("clawpos", clawpos);
            telemetry.update();
        }

    }
}
