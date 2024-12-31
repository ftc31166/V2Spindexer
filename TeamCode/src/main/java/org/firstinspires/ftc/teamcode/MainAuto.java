package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name = "MainAuto", group = "Autonomous")
public class MainAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData("Init Complete", false);
        telemetry.update();
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Subsystems.Claw claw = new Subsystems.Claw(hardwareMap);
        Subsystems.Arm arm = new Subsystems.Arm(hardwareMap);
        Subsystems.LinearSlide linearslide = new Subsystems.LinearSlide(hardwareMap);

        Action goToChamber = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(0, 32))
                .build();

        Action moveSamples = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(30, -38))
                .splineToConstantHeading(new Vector2d(35, -12), 1)
                .splineToConstantHeading(new Vector2d(40, -52), -2)
                .splineToConstantHeading(new Vector2d(50, -12), -1)
                .splineToConstantHeading(new Vector2d(56, -52), 1)
                .splineToLinearHeading(new Pose2d(56, -40, Math.toRadians(270)), Math.toRadians(270))
                .build();

        // actions that need to happen on init; for instance, a claw tightening.
        Actions.runBlocking(claw.Close());


        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addData("Init Complete", true);
            telemetry.update();
        }

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                goToChamber,
                                arm.ToChamber()
                        ),
                        new ParallelAction(
                                linearslide.ToMaxIn(),
                                arm.ToRest()
                        ),
                        moveSamples
                )
        );
    }
}