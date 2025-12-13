package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Robot;
import org.firstinspires.ftc.teamcode.moreTuning.PinpointDrive;

public class AutonFuncs  {
    boolean oscillateGate = false;
    int oscCounter = 0;
    Robot robot;
    PinpointDrive drive;
    public AutonFuncs(HardwareMap hardwareMap, Pose2d start){
        robot = new Robot(hardwareMap);
        drive = new PinpointDrive(hardwareMap,start);
    }

    public Action intakeOn(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.intake.setPower(Constants.INTAKEINPOWER);
                return true;
            }
        };
    }
    public Action intakeOnRet(){
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

    public Action hood(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                robot.oBlock.setPosition(Constants.SHOOTFAR);
                return false;
            }
        };
    }

    public Action gateOscillate() {
        ElapsedTime oscillate = new ElapsedTime();
        oscillate.reset();
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {



                robot.gate.setPosition(Constants.GATEOPEN - 0.15 * Math.cos(Math.toRadians(oscCounter)));
                    oscCounter+=5;

                if(oscCounter >= 360){
                    oscCounter = 0;
                }

                return oscillate.milliseconds()<5000;
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
}

