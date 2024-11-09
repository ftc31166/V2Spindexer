package org.firstinspires.ftc.teamcode.subsystems;

public class Util {
    public static double MAX_PIVOT_VELOCITY = 1075.5; // 4pi rad/sec
    public static double MAX_PIVOT_ACCEL = 716.9; // 2pi rad/sec^2

    public static boolean inThresh(double val, double val2, double tol) {
        return Math.abs(val - val2) > tol;
    }
}
