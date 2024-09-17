package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

// Classes for all intake/outtake Actions
public class Intake {

    public class Claw {
        private Servo claw;

        public Claw(HardwareMap hwMap) {
            claw = hwMap.get(Servo.class, "claw");                                       // TODO: Set to actual hwMap deviceName
        }

        // Define Actions
        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) { // This makes the method run while it returns true and is always needed idk
                claw.setPosition(1);                                                                // TODO: Set to actual claw position
                return false; // Makes method run once
            }
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0);                                                                // TODO: Set to actual claw position
                return false;
            }
        }

        // Wrap Actions as functions
        public Action closeClaw() {
            return new CloseClaw();
        }

        public Action openClaw() {
            return new OpenClaw();
        }
    }

    public class Spinner {
        private CRServo spinner;

        public Spinner(HardwareMap hwMap) {
            spinner = hwMap.get(CRServo.class, "spinner");                               // TODO: Set to actual hwMap deviceName
        }

        // Define Actions
        public class SpinnerOn implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinner.setPower(1);
                return false;
            }
        }

        public class SpinnerOff implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinner.setPower(0);
                return false;
            }
        }

        public class SpinnerReverse implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinner.setPower(-1);
                return false;
            }
        }

        // Wrap Actions as functions
        public Action spinnerOn() {
            return new SpinnerOn();
        }

        public Action spinnerOff() {
            return new SpinnerOff();
        }

        public Action spinnerReverse() {
            return new SpinnerReverse();
        }
    }

    public class Tilt {
        private Servo tilt;

        public Tilt(HardwareMap hwMap) {
            tilt = hwMap.get(Servo.class, "tilt");                                       // TODO: Set to actual hwMap deviceName
        }

        public class TiltZero implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                return false;
            }
        }
    }

}
