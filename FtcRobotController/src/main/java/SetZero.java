import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class SetZero extends LinearOpMode {
    @Override
    public void runOpMode(){
        Servo servo1 = hardwareMap.servo.get("servo1");
        waitForStart();
        
        servo1.setPosition(0);

        while(true){
            sleep(20);
        }
    }
}
