package org.firstinspires.ftc.teamcode.subsystems;

import android.text.InputFilter;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

@Config
public class Pivot {

    private DcMotorEx leftPivot, rightPivot;

    public static double power = 0.1;

    public static int intake = 35, max = 325, highBasket = 325, lowSpec = 120, highSpec = 200, idle = 300;
    private int pos;

    public static double kP = 0.1, kI = 0, kD = 0;

    PIDController pidController = new PIDController(kP, kI, kD);

    private int curLeft, curRight;

    public static int threshold = 10;

    public Pivot(HardwareMap hwMap, HashMap<String, String> config)
    {
        leftPivot = hwMap.get(DcMotorEx.class,config.get("leftPivot"));
        rightPivot = hwMap.get(DcMotorEx.class,config.get("rightPivot"));

        rightPivot.setDirection(DcMotorSimple.Direction.REVERSE);

        leftPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftPivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightPivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void update()
    {
        curLeft = leftPivot.getCurrentPosition();
        curRight = rightPivot.getCurrentPosition();

        leftPivot.setPower(pidController.calculate(curLeft, pos));
        rightPivot.setPower(pidController.calculate(curRight, pos));

        if (check(curLeft, pos, threshold))
        {
            leftPivot.setPower(0);
        }
        if (check(curRight, pos, threshold))
        {
            rightPivot.setPower(0);
        }
    }

    public void changePos(int tickChange)
    {
        pos = leftPivot.getCurrentPosition() + tickChange;
    }

    public void setPos(String pos)
    {
        switch (pos)
        {
            case "Specimen Intake":
                this.pos = intake;
                break;

            case "Sample Intake":
                this.pos = intake;
                break;

            case "Low Basket":
                this.pos = highBasket;
                break;

            case "High Basket":
                this.pos = highBasket;
                break;

            case "Low Specimen":
                this.pos = lowSpec;
                break;

            case "High Specimen":
                this.pos = highSpec;
                break;

            default:
                this.pos = idle;
                break;
        }
    }

    public boolean check(int cur, int target, int thresh)
    {
        return Math.abs(target - cur) < thresh;
    }
}
