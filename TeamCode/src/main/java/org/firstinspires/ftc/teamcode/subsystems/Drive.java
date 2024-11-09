package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

@Config
public class Drive {

    private DcMotor frontLeft, backLeft, frontRight, backRight;

    private double x, y, rx, d;

    public static double speedMult = 1, rotMult = 0.5;

    public Drive(HardwareMap hwMap, HashMap<String, String> config)
    {
        frontLeft = hwMap.dcMotor.get(config.get("frontLeft"));
        backLeft = hwMap.dcMotor.get(config.get("backLeft"));
        frontRight = hwMap.dcMotor.get(config.get("frontRight"));
        backRight = hwMap.dcMotor.get(config.get("backRight"));

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void getXYZ(double x, double y, double rx)
    {
        this.x =x * speedMult;
        this.y = y * speedMult;
        this.rx = rx * rotMult;
    }

    public void update()
    {
        d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeft.setPower((y + x + rx) / d);
        backLeft.setPower((y - x + rx) / d);
        frontRight.setPower((y + x - rx) / d);
        backRight.setPower((y - x - rx) / d);
    }
}
