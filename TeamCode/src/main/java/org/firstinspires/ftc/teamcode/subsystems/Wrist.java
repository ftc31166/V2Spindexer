package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
@Config
public class Wrist {

    Servo wrist;

    public static double intake = 0, basket = 1, idle = 0.2, lowSpec = 0.5, highSpec = 0.4, realIntake = 0.4;
    private double pos;

    public Wrist(HardwareMap hwMap, HashMap<String, String> config)
    {
        wrist = hwMap.servo.get(config.get("wrist"));
    }

    public void update()
    {
        wrist.setPosition(pos);
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
                this.pos = basket;
                break;

            case "High Basket":
                this.pos = basket;
                break;

            case "Low Specimen":
                this.pos = lowSpec;
                break;

            case "High Specimen":
                this.pos = highSpec;
                break;

            case "Intake":
                this.pos = realIntake;
                break;

            default:
                this.pos = idle;
                break;
        }
    }
}
