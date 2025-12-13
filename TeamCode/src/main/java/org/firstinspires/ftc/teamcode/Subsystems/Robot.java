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

    }

    public void shootClose(){
        flywheel1.setPower(Constants.POWERCLOSE);
        flywheel2.setPower(Constants.POWERCLOSE);
    }
    public void shootFar(){
        flywheel1.setPower(Constants.POWERFAR);
        flywheel2.setPower(Constants.POWERFAR);
    }
    public void stop(){
        flywheel1.setVelocity(0);
        flywheel2.setVelocity(0);
    }

}
