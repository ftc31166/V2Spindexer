package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class RobotTeleop extends LinearOpMode {
    public enum ShootingState{
        JUSTDRIVING,
        INTAKING,
        EJECTING,
        FLYWHEELING,
        FEEDING,
        OPENGATE
    }
    MecanumTeleop.ShootingState shooter = MecanumTeleop.ShootingState.JUSTDRIVING;
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("fl");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("bl");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("fr");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("br");
        Robot robot = new Robot(hardwareMap);
        Servo hood = hardwareMap.get(Servo.class, "hood");

        ElapsedTime timer = new ElapsedTime();

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            switch (shooter){
                case JUSTDRIVING:
                    robot.flywheel.setPower(0);
                    robot.intake.setPower(0);
                    robot.gate.setPosition(Constants.GATECLOSE);
                    if(gamepad1.a){
                        shooter = MecanumTeleop.ShootingState.INTAKING;
                    }

                    break;
                case INTAKING:
                    robot.intake.setPower(Constants.INTAKEINPOWER);
                    robot.gate.setPosition(Constants.GATECLOSE);
                    if(gamepad1.x){
                        shooter = MecanumTeleop.ShootingState.FLYWHEELING;
                        timer.reset();
                    }
                    if(gamepad1.b){
                        shooter = MecanumTeleop.ShootingState.EJECTING;
                    }
                    break;
                case EJECTING:
                    robot.intake.setPower(-Constants.INTAKEINPOWER);
                    if(gamepad1.b){
                        shooter = MecanumTeleop.ShootingState.JUSTDRIVING;
                    }
                    break;
                case FLYWHEELING:
                    robot.intake.setPower(0);
                    robot.flywheel.setPower(Constants.SHOOTCLOSE);
                    if(gamepad1.a && timer.milliseconds()>1000){
                        robot.gate.setPosition(Constants.GATEOPEN);
                        shooter= MecanumTeleop.ShootingState.FEEDING;
                        timer.reset();
                    }
                    break;
                case FEEDING:
                    if(timer.milliseconds()>300){
                        robot.intake.setPower(Constants.INTAKEINPOWER);
                    }
                    if(gamepad1.x ){

                        shooter= MecanumTeleop.ShootingState.JUSTDRIVING;
                    }
                    break;
            }
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
        }
    }
}