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
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import java.util.HashMap;
import java.util.Map;

@TeleOp
@Config
public class DriveTestSS extends LinearOpMode {

    public HashMap<String, String> deviceConf = new HashMap<String, String>();

    public static int tickChange = 5;

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
        deviceConf.put("wrist",           "pivot");

        Drive drive = new Drive(hardwareMap, deviceConf);

        Pivot pivot = new Pivot(hardwareMap, deviceConf);

        Extension extension = new Extension(hardwareMap, deviceConf);

        Wrist wrist = new Wrist(hardwareMap, deviceConf);

        boolean pivotReady, wristReady, extensionReady;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            pivotReady = wristReady = extensionReady = true;
            drive.getXYZ(gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);



            if (gamepad1.a && pivotReady) {
                pivot.changePos(tickChange);
                pivotReady = false;
            }
            if (gamepad1.b && pivotReady) {
                pivot.changePos(-tickChange);
                pivotReady = false;
            }
            if (gamepad2.a && pivotReady) {
                pivot.setPos("Intake");
                pivotReady = false;
            }
            if (gamepad2.b && pivotReady) {
                pivot.setPos("Basket");
                pivotReady = false;
            }

            if (gamepad1.y && wristReady) {
                wrist.setPos("Intake");
                wristReady = false;
            }
            if (gamepad1.x && wristReady) {
                wrist.setPos("Basket");
                wristReady = false;
            }

            if (gamepad2.dpad_up && extensionReady) {
                extension.setPos("High Basket");
                extensionReady  = false;
            }
            if (gamepad2.dpad_down && extensionReady) {
                extension.setPos("Intake");
                extensionReady = false;
            }

            drive.update();
            pivot.update();
            extension.update();
            wrist.update();

            //telemetry.addData("tick", leftPivotMotor.getCurrentPosition());
            //telemetry.addData("r", rightPivotMotor.getCurrentPosition());
            //telemetry.addData("tick", leftExtension.getCurrentPosition());
            //telemetry.addData("r", rightExtension.getCurrentPosition());
            //telemetry.addData("pivot", cpivot.getPosition());

            telemetry.update();


        }
    }
}