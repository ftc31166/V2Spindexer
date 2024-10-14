package com.example.meepmeeptetsing;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
//2:07:59
/*
1 preloaded specimen
get three color samples from sample zone into observation
put them on the long bar
get da human player specimen
go to other side of field and score three yellows into high bucket
 */

public class MeepMeepTetsing {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        Action trajectory1=myBot.getDrive().actionBuilder(new Pose2d(23.5, -60, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(10,-47),Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(23,-47),Math.toRadians(45))
                .waitSeconds(1)
                .turnTo(Math.toRadians(-30))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(33,-40),Math.toRadians(33))
                .waitSeconds(1)
                .turnTo(Math.toRadians(-45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(40,-35), Math.toRadians(22))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(23,-47),Math.toRadians(-45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(10,-43),Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(23,-47),Math.toRadians(-45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(10,-43),Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(23,-47),Math.toRadians(-45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(10,-43),Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(23,-47),Math.toRadians(-45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(10,-43),Math.toRadians(90))
                .waitSeconds(1)
                .build();
        Action trajectory2=myBot.getDrive().actionBuilder(new Pose2d(23.5, -60, Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(-10,-47),Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-48,-55),Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-55,-55),Math.toRadians(45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-58,-52),Math.toRadians(90))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-56,-56),Math.toRadians(45))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-57,-43), Math.toRadians(120))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(-56,-56),Math.toRadians(45))
                .build();
        myBot.runAction(trajectory2);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}