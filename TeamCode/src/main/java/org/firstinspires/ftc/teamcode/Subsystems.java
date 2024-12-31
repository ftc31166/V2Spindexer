package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import java.util.concurrent.TimeUnit;

public class Subsystems {
    public static class Arm {
        private DcMotorEx cap;
        private DcMotorEx cap2;
        private PIDController capPID = new PIDController(0.008, 0, 0.001);

        public Arm(HardwareMap hardwareMap) {
            cap = hardwareMap.get(DcMotorEx.class, "cap");
            cap2 = hardwareMap.get(DcMotorEx.class, "cap2");
            cap.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            cap.setDirection(DcMotorSimple.Direction.FORWARD);
            cap.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            cap.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            cap2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            cap2.setDirection(DcMotorSimple.Direction.REVERSE);
            capPID.setIntegrationBounds(0, 0.01);
            capPID.setTolerance(100, 100);
        }

        public class ToChamber implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    capPID.reset();
                    initialized = true;
                }
                double pos = cap.getCurrentPosition();
                cap.setPower(capPID.calculate(pos, 310));
                cap2.setPower(capPID.calculate(pos, 310));
                if (pos < 310.0) {
                    return true;
                } else {
                    cap.setPower(0);
                    cap2.setPower(0);
                    return false;
                }
            }
        }
        public Action ToChamber() {
            return new ToChamber();
        }
        public class ToRest implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    capPID.reset();
                    initialized = true;
                }
                double pos = cap.getCurrentPosition();
                cap.setPower(capPID.calculate(pos, 30));
                cap2.setPower(capPID.calculate(pos, 30));
                if (capPID.atSetPoint()) {
                    return true;
                } else {
                    cap.setPower(0);
                    cap2.setPower(0);
                    return false;
                }
            }
        }
        public Action ToRest() {
            return new ToRest();
        }
    }
    public static class LinearSlide {
        private DcMotorEx slide;
        private PIDController slidePID = new PIDController(0.008F, 0, 0.001F);

        public LinearSlide(HardwareMap hardwareMap) {
            slide = hardwareMap.get(DcMotorEx.class, "spindle");
            slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            slide.setDirection(DcMotorSimple.Direction.FORWARD);
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slidePID.setIntegrationBounds(0, 0.01);
            slidePID.setTolerance(100, 100);
        }

        public class ToMaxOut implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    slidePID.reset();
                    initialized = true;
                }
                double pos = slide.getCurrentPosition();
                slide.setPower(slidePID.calculate(pos, 2400));
                if (slidePID.atSetPoint()) {
                    return true;
                } else {
                    slide.setPower(0);
                    return false;
                }
            }
        }
        public Action ToMaxOut() {
            return new ToMaxOut();
        }
        public class ToMaxIn implements Action {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    slidePID.reset();
                    initialized = true;
                }
                double pos = slide.getCurrentPosition();
                slide.setPower(slidePID.calculate(pos, 0));
                if (slidePID.atSetPoint()) {
                    return true;
                } else {
                    slide.setPower(0);
                    return false;
                }
            }
        }
        public Action ToMaxIn() {
            return new ToMaxIn();
        }
    }
    public static class Claw {
        private ServoImplEx leftServo;
        private ServoImplEx rightServo;
        public Claw(HardwareMap hardwareMap) {
            // TODO: Make sure this is open all the way
            leftServo.setPosition(1);  // This one
            leftServo.setPwmEnable();
            leftServo.scaleRange(0, 1);
            rightServo.setPosition(1);  // And this one
            rightServo.setPwmEnable();
            rightServo.scaleRange(0, 1);
        }

        public class Open implements Action {
            private Timing.Timer wait = new Timing.Timer(700, TimeUnit.MILLISECONDS);
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    wait.start();
                    initialized = true;
                }
                leftServo.setPosition(0);
                rightServo.setPosition(1);
                return !wait.done();
            }
        }
        public Action Open() {
            return new Open();
        }
        public class Close implements Action {
            private Timing.Timer wait = new Timing.Timer(700, TimeUnit.MILLISECONDS);
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    wait.start();
                    initialized = true;
                }
                leftServo.setPosition(1);
                rightServo.setPosition(0);
                return !wait.done();
            }
        }
        public Action Close() {
            return new Close();
        }
    }
}
