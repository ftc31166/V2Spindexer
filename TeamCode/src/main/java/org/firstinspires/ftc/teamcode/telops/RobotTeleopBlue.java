package org.firstinspires.ftc.teamcode.telops;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystems.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Robot;
import org.firstinspires.ftc.teamcode.moreTuning.PinpointDrive;

@TeleOp
public class RobotTeleopBlue extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("fl");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("bl");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("fr");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("br");
        Robot robot = new Robot(hardwareMap);




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

        double counter = 0;
        boolean rightBumper = false;
        boolean rightTrigger = false;
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            PinpointDrive drive = new PinpointDrive(hardwareMap, Constants.endPose);
            if(gamepad1.dpad_down){
                Actions.runBlocking(drive.actionBuilder(drive.pose).splineToLinearHeading(new Pose2d(30,-10,0),0).build());
            }

            if(gamepad1.a){
                robot.frontGate.setPosition(Constants.BALLHOLDERUP);
                robot.intake.setPower(Constants.INTAKEINPOWER);
                robot.gate.setPosition(Constants.GATECLOSE);

            }
            if (gamepad1.x){
                robot.intake.setPower(-Constants.INTAKEINPOWER);
            }
            if (gamepad1.b){
                robot.intake.setPower(0);
                robot.frontGate.setPosition(Constants.BALLHOLDERDOWN);
            }

            if(gamepad1.right_trigger>0){
                robot.gate.setPosition(Constants.GATEOPEN);
                robot.shootClose();
                rightTrigger = true;
            }
            if(gamepad1.left_trigger>0){
                robot.gate.setPosition(Constants.GATECLOSE);
                robot.stop();
                rightBumper = false;
                rightTrigger = false;
            }
            if(gamepad1.right_bumper){
                robot.intake.setPower(Constants.INTAKEINPOWER);
                robot.gate.setPosition(Constants.GATEOPEN);
                rightBumper = true;

            }

            if(rightTrigger){
                double turnTo = Math.atan2((Constants.blueGoal.position.y - drive.pose.position.y),(Constants.blueGoal.position.x - drive.pose.position.x)) ;
                Actions.runBlocking(drive.actionBuilder(drive.pose).turnTo(turnTo).build());
            }


            if(rightBumper){
                robot.gate.setPosition(Constants.GATEOPEN+.1*Math.sin(Math.toRadians(counter)));
            }
            counter += 1;
            if(counter==360){
                counter=0;
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