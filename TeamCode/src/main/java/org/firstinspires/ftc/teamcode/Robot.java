package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    DcMotor flywheel,intake,flywheel2;
    Servo gate,ballHolder;
    public Robot(HardwareMap hardwareMap) {
        flywheel = hardwareMap.get(DcMotor.class, "fly");
        intake = hardwareMap.get(DcMotor.class, "in");
        gate = hardwareMap.get(Servo.class, "gate");
        ballHolder = hardwareMap.get(Servo.class, "ball");
        flywheel2 = hardwareMap.get(DcMotor.class, "fly2");
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        ballHolder.setPosition(Constants.BALLHOLDERUP);
        gate.setPosition(Constants.GATECLOSE);
    }
    public void pid1(){
        while(flywheel.getPower() )
    }

}
