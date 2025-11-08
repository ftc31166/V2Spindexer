package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    DcMotor flywheel,intake, feeder;
    public Robot(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotor.class, "fly");
        intake = hardwareMap.get(DcMotor.class, "in");
        feeder = hardwareMap.get(DcMotor.class, "fee");
    }
}
