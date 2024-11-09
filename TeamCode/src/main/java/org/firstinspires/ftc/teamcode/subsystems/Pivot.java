package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
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

    public Pivot(HardwareMap hwMap, HashMap<String, String> config)
    {
        leftPivot = hwMap.get(DcMotorEx.class,config.get("leftPivot"));
        rightPivot = hwMap.get(DcMotorEx.class,config.get("rightPivot"));

        rightPivot.setDirection(DcMotorSimple.Direction.REVERSE);

        leftPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void update()
    {
        leftPivot.setTargetPosition(pos);
        rightPivot.setTargetPosition(pos);
        leftPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftPivot.setVelocity(Util.MAX_PIVOT_VELOCITY);
        leftPivot.setPower(power);
        rightPivot.setPower(power);
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
}
