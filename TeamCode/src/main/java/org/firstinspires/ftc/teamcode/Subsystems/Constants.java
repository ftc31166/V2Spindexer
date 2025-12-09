package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Pose2d;

public class Constants {
    public static final  double INTAKEINPOWER  = -1;
    public static final  double INTAKEOUTPOWER  = 0.7;


    public static final  double SHOOTCLOSE = 1;

    public static final  double GATEOPEN  = 0.1;
    public static final  double GATECLOSE  = 0.3;
    public static final  double BALLHOLDERUP  = 0.2;
    public static final  double BALLHOLDERDOWN  = 0.0;



    public static final double TICKS_PER_REV = 28;
    public static final double RPM = 6000;
    public static Pose2d endPose = new Pose2d(0,0,0);
    public static final Pose2d redGoal = new Pose2d(-64,64,0);
    public static final Pose2d blueGoal = new Pose2d(-64,-64,0);



}
