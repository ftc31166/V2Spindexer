package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

@Config
public class Pivot {

    private DcMotor leftPivot, rightPivot;

    public static double power = 1;
    public static int tickChange = 5;

    public static int intake = 0, max = -325, basket = -325;
    private int pos;

    public Pivot(HardwareMap hwMap, HashMap<String, String> config)
    {
        leftPivot = hwMap.dcMotor.get(config.get("leftPivot"));
        rightPivot = hwMap.dcMotor.get(config.get("rightPivot"));

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
        leftPivot.setPower(power);
        rightPivot.setPower(power);
    }

    public void changePos(int dir)
    {
        switch(dir)
        {
            case 1:
                pos = leftPivot.getCurrentPosition() + tickChange;
                break;

            case -1:
                pos = leftPivot.getCurrentPosition() - tickChange;
                break;

            default:
                pos = leftPivot.getCurrentPosition();
                break;
        }
    }

    public void setPos(String pos)
    {
        switch (pos)
        {
            case "Intake":
                this.pos = intake;
                break;

            case "Basket":
                this.pos = basket;
                break;
        }
    }
}
