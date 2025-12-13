package org.firstinspires.ftc.teamcode.Subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    public DcMotorEx flywheel1,flywheel2,intake;
    public Servo frontGate, gate, oBlock;

    public Robot(HardwareMap hardwareMap){
        flywheel1 = hardwareMap.get(DcMotorEx.class,"fly1" );
        flywheel2 = hardwareMap.get(DcMotorEx.class,"fly2" );
        intake = hardwareMap.get(DcMotorEx.class, "in");
        frontGate = hardwareMap.get(Servo.class, "front");
        gate = hardwareMap.get(Servo.class, "gate");
        oBlock = hardwareMap.get(Servo.class, "hood");
        flywheel1.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        PIDFCoefficients pidf = new PIDFCoefficients(
                100,   // P
                0.0,    // I
                20,    // D
                200  // F
        );

        flywheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
        flywheel2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidf);
    }

    public void shootClose(){
        flywheel1.setVelocity(Constants.TICKS_PER_REV*Constants.SHOOTCLOSE*Constants.RPM/60);
        flywheel2.setVelocity(Constants.TICKS_PER_REV*Constants.SHOOTCLOSE*Constants.RPM/60);
    }
    public void stop(){
        flywheel1.setVelocity(0);
        flywheel2.setVelocity(0);
    }

}
