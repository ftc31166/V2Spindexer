package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

@Config
public class Extension {

    private DcMotor leftExtension, rightExtension;

    public static double power = 0.4;

    public static int specIntake = 1000, sampleIntake = 10, max = 2450, highBasket = 2450, lowBasket = 100, lowSpec = 10, highSpec = 1000, idle = 10;
    private int pos;

    public Extension(HardwareMap hwMap, HashMap<String, String> config)
    {
        leftExtension = hwMap.dcMotor.get(config.get("leftExtension"));
        rightExtension = hwMap.dcMotor.get(config.get("rightExtension"));

        rightExtension.setDirection(DcMotorSimple.Direction.REVERSE);

        leftExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightExtension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void update()
    {
        leftExtension.setTargetPosition(pos);
        rightExtension.setTargetPosition(pos);
        leftExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftExtension.setPower(power);
        rightExtension.setPower(power);
    }

    public void setPos(String pos)
    {
        switch (pos)
        {
            case "Specimen Intake":
                this.pos = specIntake;
                break;

            case "Sample Intake":
                this.pos = sampleIntake;
                break;

            case "Low Basket":
                this.pos = lowBasket;
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

    public boolean isBusy(){
        return leftExtension.isBusy();
    }

}
