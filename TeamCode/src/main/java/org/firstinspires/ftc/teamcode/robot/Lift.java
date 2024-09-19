package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

// Class for lift Actions
public class Lift {

    // test
    private DcMotorEx leftPivot;
    private DcMotorEx rightPivot;

    private DcMotorEx leftExtension;
    private DcMotorEx rightExtension;

    public Lift(HardwareMap hwMap) {
        leftPivot = hwMap.get(DcMotorEx.class, "left_pivot");                                       // TODO: Set to actual hwMap deviceName
        rightPivot = hwMap.get(DcMotorEx.class, "right_pivot");                                     // TODO: Set to actual hwMap deviceName
        leftExtension = hwMap.get(DcMotorEx.class, "left_slide");                                   // TODO: Set to actual hwMap deviceName
        rightExtension = hwMap.get(DcMotorEx.class, "left_slide");                                  // TODO: Set to actual hwMap deviceName

        leftPivot.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    // TODO: Define Actions
    public class Preset1 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            return false;
        }
    }

    // TODO: Wrap Actions as functions

}
