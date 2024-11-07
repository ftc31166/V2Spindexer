package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;

import java.util.HashMap;
import java.util.Map;

@TeleOp
@Config
public class DriveTestSS extends LinearOpMode {
    public static double test1 = 0.6, test2 = 1;
    public static int  test3 = 100;
    public HashMap<String, String> deviceConf = new HashMap<String, String>();
    @Override
    public void runOpMode() throws InterruptedException {

        // Robot Config Mapping
        deviceConf.put("frontLeft",       "frontLeftMotor");
        deviceConf.put("backLeft",        "backLeftMotor");
        deviceConf.put("frontRight",      "frontRightMotor");
        deviceConf.put("backRight",       "backRightMotor");
        deviceConf.put("leftPivot",       "leftPivot");
        deviceConf.put("rightPivot",      "rightPivot");
        deviceConf.put("leftExtension",   "leftExtension");
        deviceConf.put("rightExtension",  "rightExtension");

        Drive drive = new Drive(hardwareMap, deviceConf);

        Pivot pivot = new Pivot(hardwareMap, deviceConf);

        DcMotor leftExtension = hardwareMap.dcMotor.get("leftExtension");
        DcMotor rightExtension = hardwareMap.dcMotor.get("rightExtension");

        Servo cpivot = hardwareMap.servo.get("pivot");

        leftExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            drive.getXYZ(gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);

            drive.update();

            if (gamepad1.a) {
                pivot.changePos(1);
            }
            else if (gamepad1.b) {
                pivot.changePos(-1);
            }
            else if (gamepad2.a)
            {
                pivot.setPos("Intake");
            }
            else if (gamepad2.b)
            {
                pivot.setPos("Basket");
            }

            pivot.update();


            if (gamepad1.y)
            {
                cpivot.setPosition(0);


            }
            if (gamepad1.x)
            {
                cpivot.setPosition(1);

            }

            if (gamepad2.dpad_up)
            {
                leftExtension.setTargetPosition(3425);
                rightExtension.setTargetPosition(-3425);
                leftExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftExtension.setPower(1);
                rightExtension.setPower(1);
            }
            if (gamepad2.dpad_down)
            {
                leftExtension.setTargetPosition(0);
                rightExtension.setTargetPosition(0);
                leftExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftExtension.setPower(1);
                rightExtension.setPower(1);
            }

            //telemetry.addData("tick", leftPivotMotor.getCurrentPosition());
            //telemetry.addData("r", rightPivotMotor.getCurrentPosition());
            telemetry.addData("tick", leftExtension.getCurrentPosition());
            telemetry.addData("r", rightExtension.getCurrentPosition());
            telemetry.addData("pivot", cpivot.getPosition());

            telemetry.update();


        }
    }
}