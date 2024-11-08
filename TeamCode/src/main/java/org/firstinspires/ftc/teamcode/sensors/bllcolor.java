package org.firstinspires.ftc.teamcode.sensors;


import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class bllcolor extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        RevColorSensorV3 sensor = hardwareMap.get(RevColorSensorV3.class, "Color");

        Servo claw = hardwareMap.get(Servo.class, "claw2");

        waitForStart();
        while (opModeIsActive()) {
            // read all 3 color channels in one I2C transmission:
            telemetry.addData("Distance", sensor.getDistance(DistanceUnit.MM));
            telemetry.update();
            if (sensor.getDistance(DistanceUnit.MM) < 40 && !gamepad1.a) {
                claw.setPosition(0.32);
            } else {
                claw.setPosition(0.65);
            }
        }
    }
}
