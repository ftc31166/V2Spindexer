package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Pose2d;

public class Constants {
    public static final  double INTAKEINPOWER  = -1;
    public static final  double INTAKEOUTPOWER  = 0.7;

    public static final double DEFAULT = 0.825;


    public static final  double GATEOPEN  = 0.15;
    public static final  double GATECLOSE  = 0.3;
    public static final  double BALLHOLDERUP  = 0.25;
    public static final  double BALLHOLDERDOWN  = 0.0;

    public static final double SHOOTFAR = .975;

    public static final double TICKS_PER_REV = 28;
    public static final double POWERCLOSE = .5;
    public static final double POWERFAR = 1;
    public static Pose2d endPose = new Pose2d(0,0,0);
    public static final Pose2d redGoal = new Pose2d(-64,64,0);
    public static final Pose2d blueGoal = new Pose2d(-64,-64,0);


}
