package org.firstinspires.ftc.teamcode.AUTO;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.RaceAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.moreTuning.MecanumDrive;

//import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
@Autonomous(name = "RedClose", group = "Autonomous")
public class RedClose extends LinearOpMode {

    private Pose2d curPos;
    private double curDistance;

    @Override
    public void runOpMode() {

        Pose2d initPose = new Pose2d(-52, 52, Math.toRadians(135));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initPose);

        curPos = drive.pose;
        curDistance = 0;

    
        Action scorePreload = drive.actionBuilder(initPose)
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(-20, 20), Math.toRadians(-45))
                .build();

        Action intakeFirstSet = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), initPose.heading))
                .splineToLinearHeading(new Pose2d(-15, 15, Math.toRadians(90)), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-15, 58), Math.toRadians(90))
                .build();

        Action goToScoreFirstSet = drive.actionBuilder(new Pose2d(-15, 58, Math.toRadians(90)))
                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(-15, 20, Math.toRadians(135)), Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-20, 20, Math.toRadians(135)), Math.toRadians(135))
                .build();

        Action intakeSecondSet = drive.actionBuilder(new Pose2d(new Vector2d(-20, 20), Math.toRadians(135)))
                .setTangent(Math.toRadians(-45))
                .splineToLinearHeading(new Pose2d(12, 15, Math.toRadians(90)), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(12, 70), Math.toRadians(90), new TranslationalVelConstraint(40))
                .waitSeconds(0.1)
                .build();

        Action goToScoreSecondSet = drive.actionBuilder(new Pose2d(12, 70, Math.toRadians(90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-20, 20, Math.toRadians(135)), Math.toRadians(135))
                .build();

        Action park = drive.actionBuilder(new Pose2d(-20, 20, Math.toRadians(135)))
                .strafeTo(new Vector2d(-60, 30))
                .build();


        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        scorePreload,
                        intakeFirstSet,
                        goToScoreFirstSet,
                        intakeSecondSet,
                        goToScoreSecondSet,
                        park
                )
        );
    }
}
