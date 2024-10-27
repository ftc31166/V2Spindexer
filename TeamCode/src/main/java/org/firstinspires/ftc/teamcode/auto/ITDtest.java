package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;

@Autonomous
public final class ITDtest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, beginPose);

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineToLinearHeading(new Pose2d(10, 30, Math.toRadians(90)), Math.toRadians(0))
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(40, 30, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(55, 50, Math.toRadians(90)), Math.toRadians(-90))
                        .setReversed(true)
                        .splineToLinearHeading(new Pose2d(55, 20, Math.toRadians(90)), Math.toRadians(90))
                        .setReversed(false)
                        .splineToLinearHeading(new Pose2d(67, 55, Math.toRadians(90)), Math.toRadians(-90))
                        .setReversed(true)
                        .splineToLinearHeading(new Pose2d(67, 20, Math.toRadians(90)), Math.toRadians(90))
                        .setReversed(false)
                        .splineToLinearHeading(new Pose2d(78, 55, Math.toRadians(90)), Math.toRadians(-90))
                        .setReversed(true)
                        .splineToLinearHeading(new Pose2d(78, 15, Math.toRadians(90)), Math.toRadians(90))
                        .build());

    }
}