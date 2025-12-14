package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Robot;
@TeleOp(name = "pidtewst")
public class pidveltest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap);
        waitForStart();
        if(isStopRequested()) return;
        double rpm = 6000;
        ElapsedTime timer = new ElapsedTime();
        ElapsedTime starttimer = new ElapsedTime();
        PIDFCoefficients pidf = new PIDFCoefficients(
                100,   // P
                0.0,    // I
                20,    // D
                200  // F
        );

        robot.flywheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidf);
        while (opModeIsActive()){

            if(gamepad1.a&&timer.milliseconds()>300){
                robot.flywheel1.setVelocity(rpm*28/60);
                robot.flywheel2.setVelocity(rpm*28/60);
                timer.reset();
                starttimer.reset();
            }
            if(gamepad1.b&&timer.milliseconds()>300){
                robot.flywheel1.setVelocity(0);
                robot.flywheel2.setVelocity(0);
                timer.reset();
            }
            if(gamepad1.dpad_up&&timer.milliseconds()>300){
                rpm += 100;
                timer.reset();
            }
            if(gamepad1.dpad_down&&timer.milliseconds()>300){
                rpm -= 100;
                timer.reset();
            }


            telemetry.addData("RPM: ",rpm);telemetry.addData("tps: ",rpm*28/60);
            telemetry.addData("wheel 1 : ",robot.flywheel1.getVelocity());

            telemetry.update();

        }
    }
}
