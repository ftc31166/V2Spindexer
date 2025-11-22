package org.firstinspires.ftc.teamcode.AUTO;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.RaceAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AutonFuncs;
import org.firstinspires.ftc.teamcode.moreTuning.MecanumDrive;
import org.firstinspires.ftc.teamcode.moreTuning.PinpointDrive;

//import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
@Autonomous(name = "RedClose", group = "Autonomous")
public class RedClose extends LinearOpMode {

    private Pose2d curPos;
    private double curDistance;

    @Override
    public void runOpMode() {
        AutonFuncs robot = new AutonFuncs(hardwareMap);
        Pose2d initPose = new Pose2d(-52, 52, Math.toRadians(135));
        PinpointDrive drive = new PinpointDrive(hardwareMap, initPose);

        curPos = drive.pose;
        curDistance = 0;

    
        Action scorePreload = drive.actionBuilder(initPose)
                .stopAndAdd(robot.gateClose())
                .stopAndAdd(robot.flywheelOn())
                .stopAndAdd(robot.intakeOn())
                .setTangent(Math.toRadians(-45))
                .splineToConstantHeading(new Vector2d(-10, 10), Math.toRadians(-45))
                .stopAndAdd(robot.intakeOff())
                .stopAndAdd(robot.gateOpen())
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeOn())
                .stopAndAdd(new SleepAction(3))
                .build();

        Action intakeFirstSet = drive.actionBuilder(new Pose2d(new Vector2d(-10, 10), initPose.heading))
                .stopAndAdd(robot.gateClose())
                .splineToLinearHeading(new Pose2d(-10, 10, Math.toRadians(90)), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-15, 58), Math.toRadians(90))
                .stopAndAdd(new SleepAction(1))
                .build();

        Action goToScoreFirstSet = drive.actionBuilder(new Pose2d(-15, 58, Math.toRadians(90)))
                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(new Pose2d(-15, 20, Math.toRadians(135)), Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-10, 10, Math.toRadians(135)), Math.toRadians(135))
                .stopAndAdd(robot.intakeOff())
                .stopAndAdd(robot.gateOpen())
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeOn())
                .stopAndAdd(new SleepAction(3))
                .build();

        Action intakeSecondSet = drive.actionBuilder(new Pose2d(new Vector2d(-10, 10), Math.toRadians(135)))
                .stopAndAdd(robot.gateClose())
                .setTangent(Math.toRadians(-45))
                .splineToLinearHeading(new Pose2d(12, 15, Math.toRadians(90)), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(10, 70), Math.toRadians(90))
                .stopAndAdd( new SleepAction(1))
                .build();

        Action goToScoreSecondSet = drive.actionBuilder(new Pose2d(10, 70, Math.toRadians(90)))
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(new Pose2d(-10, 10, Math.toRadians(135)), Math.toRadians(45))
                .stopAndAdd(robot.gateOpen())
                .stopAndAdd(new SleepAction(3))
                .build();

        Action park = drive.actionBuilder(new Pose2d(-10, 10, Math.toRadians(135)))
                .splineToLinearHeading(new Pose2d(-15, 20,Math.toRadians(90)),Math.toRadians(90))
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
