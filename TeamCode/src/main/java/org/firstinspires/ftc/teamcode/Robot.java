package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    DcMotor flywheel,intake;
    Servo gate;
    public Robot(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotor.class, "fly");
        intake = hardwareMap.get(DcMotor.class, "in");
        gate = hardwareMap.get(Servo.class, "gate");
    }
}
