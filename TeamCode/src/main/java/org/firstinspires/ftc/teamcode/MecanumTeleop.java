package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp
public class MecanumTeleop extends LinearOpMode {
    public enum ShootingState{
        FLYWHEEL_ON,
        FEEDER_ON,
        ALL_OFF
    }
    ShootingState shooter = ShootingState.ALL_OFF;
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        Robot robot = new Robot(hardwareMap);
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("fl");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("bl");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("fr");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("br");
        Servo hood = hardwareMap.get(Servo.class, "hood");
        ElapsedTime timer = new ElapsedTime();

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.

            if (gamepad1.start) {//resets heading
                imu.resetYaw();
            }
            if (gamepad1.a){
                robot.intake.setPower(Constants.INTAKEINPOWER);
                robot.feeder.setPower(Constants.FEEDERIN);
//                robot.reverseFeeder.setPower(robot.feeder.getPower());  un comment this if the ball is leaving the turret before flywheel on
            }
            if (gamepad1.b){
                robot.intake.setPower(0);
            }
            if (gamepad1.x){
                hood.setPosition(hood.getPosition()+1);
            }
            if (gamepad1.y){
                hood.setPosition(hood.getPosition()-1);
            }
            if (gamepad1.right_bumper){
                robot.feeder.setPower(0.7);
            }
            if (gamepad1.left_bumper){
                robot.feeder.setPower(0);
            }
            switch (shooter){
                case ALL_OFF:
                    robot.flywheel.setPower(0);
                    robot.intake.setPower(0);
                    robot.feeder.setPower(0);
                    robot.reverseFeeder.setPower(0);
                    if(gamepad1.left_trigger>0){
                        shooter = ShootingState.FLYWHEEL_ON;
                        timer.reset();
                    }
                    break;
                case FLYWHEEL_ON:
                    robot.flywheel.setPower(Constants.SHOOTCLOSE);
                    if(timer.milliseconds() > 300){//change this if flywheel isnt fast enough
                        shooter = ShootingState.FEEDER_ON;

                    }
                    break;
                case FEEDER_ON:
                    robot.intake.setPower(Constants.INTAKEINPOWER);
                    robot.feeder.setPower(Constants.FEEDERIN);
                    robot.reverseFeeder.setPower(-robot.feeder.getPower());
                    if(gamepad1.right_trigger>0){
                        shooter=ShootingState.ALL_OFF;
                    }
                    break;
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
        }
    }
}
