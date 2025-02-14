
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class robot_teleop_full extends LinearOpMode {
    //this determines how fast the shoulder moves
    double shoulderIncrement = 0.6;
    //coefficient for total drive base speed

    //initialize
    public void runOpMode() {

        Robot robot = new Robot(hardwareMap, telemetry);

        //set directions and calibrate
        robot.init();

        //scale and increments
        double shoulderScale = 0.0;

        //default positions
        robot.setWristPosition(0.5);
//        robot.setHandPosition(0.35);

        //hit run
        waitForStart();
        while (opModeIsActive()) {


            //hand movement
            if (gamepad2.right_bumper) {
                robot.setHandPosition(0.78);
//                telemetry.addData("Hand", gamepad1.right_bumper);
            } else {
                robot.setHandPosition(0.35);
            }

            //arm movement
            //down/default position
            if (gamepad2.b) {
                //shoulder
                if (shoulderScale < 0.0) {
                    shoulderScale = 0.0;
                }
                if (shoulderScale > 0.0) {
                    shoulderScale -= shoulderIncrement;
                }
                //wrist
                robot.setWristPosition(0.18);
//                robot.setArmPosition(80);
//                robot.setArmPosition(ArmPosition.Full); // ArmPosition.Ground, Hang, Travel,

            }
            //pickup position
            if (gamepad2.x) {
                if (shoulderScale < 99) {
                    shoulderScale += shoulderIncrement;
                }
                if (shoulderScale > 99) {
                    shoulderScale = 99;
                }
                robot.setWristPosition(0.85);
            }
            //reaching position
            if (gamepad2.y) {
                if (shoulderScale < 47) {
                    shoulderScale += shoulderIncrement;
                }
                if (shoulderScale > 47) {
                    shoulderScale -= shoulderIncrement;
                }
                robot.setWristPosition(0.43);
            }
            //inside submeresible
            if (gamepad2.a) {
                if (shoulderScale < 87.5) {
                    shoulderScale += shoulderIncrement;
                }
                if (shoulderScale > 87.5) {
                    shoulderScale -= shoulderIncrement;
                }
                robot.setWristPosition(0.33);
            }

            //prevent bashing into ground
            if (shoulderScale > 90) {
                robot.setWristPosition(0.70);
            }

            //hanging help
            double fudge = gamepad2.right_trigger * 5;

            //speed modulation
            boolean goSlow = gamepad1.right_trigger > 0.25;

            //failsafe
            if (gamepad2.dpad_down) {
                robot.stop();
            }

            //send power
            //drive train
            double drive = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;
            robot.move(drive, strafe, turn, goSlow);

            //arm power
            int shoulderPosition = (int) (1098 * (shoulderScale - fudge) / 100);
            robot.moveShoulderTo(shoulderPosition);

            //telemetry
//            telemetry.addData("shoulder difference: ", shoulder1.getCurrentPosition()-shoulder2.getCurrentPosition());
            telemetry.update();
//            telemetry.addData("wrist position", wrist.getPosition());
        }
    }
}



