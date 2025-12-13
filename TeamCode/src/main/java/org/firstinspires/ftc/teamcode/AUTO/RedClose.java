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
import org.firstinspires.ftc.teamcode.Subsystems.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Robot;
import org.firstinspires.ftc.teamcode.moreTuning.MecanumDrive;
import org.firstinspires.ftc.teamcode.moreTuning.PinpointDrive;

@Config
@Autonomous(name = "RedClose", group = "Autonomous")
public class RedClose extends LinearOpMode {

    private Pose2d curPos;
    private double curDistance;

    @Override
    public void runOpMode() {


        // *** X-axis inversion = flip Y signs AND angle signs ***
        Pose2d initPose = new Pose2d(-52, 52, Math.toRadians(130));
        AutonFuncs robot = new AutonFuncs(hardwareMap, initPose);
        Robot rbt = new Robot(hardwareMap);
        PinpointDrive drive = new PinpointDrive(hardwareMap, initPose);
        int oscCounter = 0;
        curPos = drive.pose;
        curDistance = 0;

        Action scorePreload = drive.actionBuilder(initPose)
                .stopAndAdd(robot.flywheelOn())

                .setTangent(Math.toRadians(-45))
                .splineToLinearHeading(new Pose2d(-14, 15,Math.toRadians(135)), Math.toRadians(-45), new TranslationalVelConstraint(100))
                .stopAndAdd(robot.intakeOn())
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeRev())
                .stopAndAdd(new SleepAction(0.2))
                .stopAndAdd(robot.intakeOn())

                .strafeTo(new Vector2d(-15, 16), new TranslationalVelConstraint(100))
                .strafeTo(new Vector2d(-13, 14), new TranslationalVelConstraint(100))
                .build();

        Action intakeFirstSet = drive.actionBuilder(new Pose2d(new Vector2d(-13, 14), Math.toRadians(135)))
                .stopAndAdd(robot.ballOpen())
                .setTangent(45)
                .splineToLinearHeading(new Pose2d(-14, 12, Math.toRadians(90)), Math.toRadians(90), new TranslationalVelConstraint(100))

                .splineToConstantHeading(new Vector2d(-14, 50), Math.toRadians(90))

                .stopAndAdd(new SleepAction(0.5))
                .stopAndAdd(robot.ballClose())
                .stopAndAdd(new SleepAction(0.5))
                .stopAndAdd(robot.intakeOff())
                .stopAndAdd(new SleepAction(0.5))
                .build();

        Action goToScoreFirstSet = drive.actionBuilder(new Pose2d(-14, 56, Math.toRadians(90)))

                .splineToSplineHeading(new Pose2d(-15, 20, Math.toRadians(135)), Math.toRadians(-90), new TranslationalVelConstraint(100))
                .splineToLinearHeading(new Pose2d(-13, 14, Math.toRadians(135)), Math.toRadians(135), new TranslationalVelConstraint(100))

                .stopAndAdd(robot.intakeOn())
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeRev())
                .stopAndAdd(new SleepAction(0.2))
                .stopAndAdd(robot.intakeOn())

                .strafeTo(new Vector2d(-16, 17), new TranslationalVelConstraint(100))
                .strafeTo(new Vector2d(-13, 14), new TranslationalVelConstraint(100))
                .build();

        Action intakeSecondSet = drive.actionBuilder(new Pose2d(new Vector2d(-13, 14), Math.toRadians(135)))
                .stopAndAdd(robot.ballOpen())

                .setTangent(Math.toRadians(45))
                .strafeToLinearHeading(new Vector2d(13, 12), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(13, 50), Math.toRadians(90))

                .stopAndAdd(new SleepAction(0.5))
                .stopAndAdd(robot.ballClose())
                .stopAndAdd(new SleepAction(0.5))
                .stopAndAdd(robot.intakeOff())
                .stopAndAdd(new SleepAction(0.5))
                .build();

        Action goToScoreSecondSet = drive.actionBuilder(new Pose2d(12, 63, Math.toRadians(90)))
                .setTangent(Math.toRadians(-90))

                .splineToLinearHeading(new Pose2d(-14, 15, Math.toRadians(135)), Math.toRadians(45), new TranslationalVelConstraint(100))
                .stopAndAdd(robot.intakeOff())
                .stopAndAdd(robot.intakeOn())
                .stopAndAdd(new SleepAction(1))
                .stopAndAdd(robot.intakeRev())
                .stopAndAdd(new SleepAction(0.2))
                .stopAndAdd(robot.intakeOn())

                .strafeTo(new Vector2d(-16, 17), new TranslationalVelConstraint(100))
                .strafeTo(new Vector2d(-13, 14), new TranslationalVelConstraint(100))
                .build();

        Action park = drive.actionBuilder(new Pose2d(-13, 14, Math.toRadians(135)))
                .strafeTo(new Vector2d(10, 50), new TranslationalVelConstraint(100))
                .build();


        Actions.runBlocking(robot.ballClose());Actions.runBlocking(robot.hood());

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
        Constants.endPose = drive.pose;
        while (opModeIsActive()){
            if (rbt. flywheel1.getVelocity() > 50) {
                rbt.gate.setPosition(Constants.GATEOPEN + 0.12 * Math.sin(Math.toRadians(oscCounter)));
                oscCounter += 5;

            } else {
                rbt.gate.setPosition(Constants.GATECLOSE);
                oscCounter = 0; // reset wave
            }
            if (oscCounter == 360){
                oscCounter =0;
            }
        }
    }
}
