package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Robot;

public class AutonFuncs  {
    Robot robot;
    public AutonFuncs(HardwareMap hardwareMap){
        robot = new Robot(hardwareMap);
    }

    public Action intakeOn(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.intake.setPower(Constants.INTAKEINPOWER);
                return false;
            }
        };
    }
    public Action intakeOff(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.intake.setPower(0);
                return false;
            }
        };
    }
    public Action intakeRev(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.intake.setPower(Constants.INTAKEOUTPOWER);
                return false;
            }
        };
    }
    public Action gateClose(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.gate.setPosition(Constants.GATECLOSE);
                return false;
            }
        };
    }
    public Action gateOpen(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.gate.setPosition(Constants.GATEOPEN);
                return false;
            }
        };
    }
    public Action ballOpen(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.frontGate.setPosition(Constants.BALLHOLDERUP);
                return false;
            }
        };
    }
    public Action ballClose(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.frontGate.setPosition(Constants.BALLHOLDERDOWN);
                return false;
            }
        };
    }
    public Action flywheelOn(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.shootClose();
                return false;
            }
        };
    }
    public Action flywheelOff(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.stop();
                return false;
            }
        };
    }

}
