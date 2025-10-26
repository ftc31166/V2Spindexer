package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    DcMotor flywheel,intake;
    Servo turret,hood,lifter,spindexer;
    public Robot(HardwareMap hardwareMap){
        flywheel = hardwareMap.get(DcMotor.class, "fly");
        intake = hardwareMap.get(DcMotor.class, "in");
        turret = hardwareMap.get(Servo.class, "tur");
        hood = hardwareMap.get(Servo.class, "hood");
        lifter = hardwareMap.get(Servo.class, "lift");
        spindexer = hardwareMap.get(Servo.class, "spin");
    }
    
}
