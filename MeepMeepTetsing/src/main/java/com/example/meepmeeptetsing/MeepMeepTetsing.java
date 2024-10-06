package com.example.meepmeeptetsing;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(23.5, -60, 0))
                .strafeTo(new Vector2d(40,69))
                /*
                .turn(Math.toRadians(90))
                .strafeTo(new Vector2d(-50,49))
                .turn(Math.toRadians(420))
                .strafeTo(new Vector2d(30,-69))
                .turn(Math.toRadians(-270))
                .strafeTo(new Vector2d(0,0))
                .turn(Math.toRadians(2))
                 */
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}