package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.IMU;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name = "Auton-ish", group = "Autonomous")
public class Autonish extends LinearOpMode{
    DcMotor frontLeftMotor = hardwareMap.dcMotor.get("fl");
    DcMotor backLeftMotor = hardwareMap.dcMotor.get("bl");
    DcMotor frontRightMotor = hardwareMap.dcMotor.get("fr");
    DcMotor backRightMotor = hardwareMap.dcMotor.get("br");

    Robot robot = new Robot(hardwareMap);
    public void straight(double power) {
        //In positive, goes forward
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public void strafe(double power){
        //In positive, it will go left
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(-power);
    }
    public void stopAll(){
        //Stops all motors; sets power to zero
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void Shoot(){

        robot.flywheel.setPower(Constants.SHOOTFAR);
        sleep(1000);
        robot.intake.setPower(Constants.INTAKEINPOWER);
        robot.feeder.setPower(1);
        sleep(3000);
        robot.intake.setPower(0);
        robot.feeder.setPower(0);
        robot.flywheel.setPower(0);
    }
    @Override

    public void runOpMode() {
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        straight(-1);
        sleep(500);
        stopAll();
        Shoot();
        strafe(1);
        sleep(500);
        stopAll();
    }

}
