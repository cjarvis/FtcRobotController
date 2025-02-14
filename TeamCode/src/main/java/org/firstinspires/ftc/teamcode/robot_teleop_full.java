
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class robot_teleop_full extends LinearOpMode{

    //this determines how fast the shoulder moves
    double shoulderIncrement = 0.6;
    //coefficient for total drive base speed
    double driveCoefficient = 0.75;

    //initialize
    public void runOpMode(){

        //get servos and motors from configuration
        Servo wrist = hardwareMap.servo.get("wrist");
        Servo hand  = hardwareMap.servo.get("hand");
        DcMotor shoulder1 = hardwareMap.dcMotor.get("shoulder1");
        DcMotor shoulder2 = hardwareMap.dcMotor.get("shoulder2");
        //Commented out for sake of testing safety; uncomment for function
        DcMotor rightAntDrive  = hardwareMap.dcMotor.get("rightAntDrive");
        DcMotor rightPostDrive = hardwareMap.dcMotor.get("rightPostDrive");
        DcMotor leftPostDrive  = hardwareMap.dcMotor.get("leftPostDrive");
        DcMotor leftAntDrive   = hardwareMap.dcMotor.get("leftAntDrive");

        shoulder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder1.setDirection(DcMotor.Direction.REVERSE);
        shoulder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder2.setDirection(DcMotor.Direction.REVERSE);
        leftAntDrive.setDirection(DcMotor.Direction.REVERSE);
        leftPostDrive.setDirection(DcMotor.Direction.REVERSE);



        //scale and increments
        double shoulderScale = 0.0;

        //default positions
        wrist.setPosition(0.5);
//        hand.setPosition(0.35);

        //hit run
        waitForStart();
        while (opModeIsActive()){

            //drive train
            double drive  =  gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn   =  -gamepad1.right_stick_x;
            double frontLeftPower  = (drive + strafe - turn);
            double backLeftPower   = (drive - strafe - turn);
            double frontRightPower = (drive - strafe + turn);
            double backRightPower  = (drive + strafe + turn);

            //hand movement
            if (gamepad2.right_bumper) {
                hand.setPosition(0.78);
                telemetry.addData("Hand", gamepad1.right_bumper);
            }
            else {hand.setPosition(0.35);}

            //arm movement
            //down/default position
            if (gamepad2.b) {
                //shoulder
                if (shoulderScale < 0.0) {shoulderScale = 0.0;}
                if (shoulderScale > 0.0) {shoulderScale -= shoulderIncrement;}
                //wrist
                wrist.setPosition(0.18);
            }
            //pickup position
            if (gamepad2.x) {
                if (shoulderScale < 99) {shoulderScale += shoulderIncrement;}
                if (shoulderScale > 99) {shoulderScale = 99;}
                wrist.setPosition(0.85);
            }
            //reaching position
            if (gamepad2.y) {
                if (shoulderScale < 47) {shoulderScale += shoulderIncrement;}
                if (shoulderScale > 47) {shoulderScale -= shoulderIncrement;}
                wrist.setPosition(0.43);
            }
            //inside submeresible
            if (gamepad2.a){
                if (shoulderScale < 87.5) {shoulderScale+=shoulderIncrement;}
                if (shoulderScale > 87.5) {shoulderScale -= shoulderIncrement;}
                wrist.setPosition(0.33);
            }

            //prevent bashing into ground
            if (shoulderScale>90){wrist.setPosition(0.70);}

            //hanging help
            double fudge = gamepad2.right_trigger*5;

            //speed modulation
            if (gamepad1.right_trigger>0.25) {
                driveCoefficient = 0.3;
            }
            else {driveCoefficient = 0.75;}

            //failsafe
            if (gamepad2.dpad_down){
                shoulder1.setPower(-1);
                shoulder2.setPower(-1);
                shoulder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                shoulder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }


            //send power
            rightAntDrive.setPower(Math.pow(frontRightPower, 3)*driveCoefficient);
            rightPostDrive.setPower(Math.pow(backRightPower, 3)*driveCoefficient);
            leftPostDrive.setPower(Math.pow(backLeftPower, 3)*driveCoefficient);
            leftAntDrive.setPower(Math.pow(frontLeftPower, 3)*driveCoefficient);

            //arm power
            shoulder1.setPower(0.8);
            shoulder1.setTargetPosition((int)(1098 * (shoulderScale-fudge)/100));
            shoulder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            shoulder2.setPower(0.8);
            shoulder2.setTargetPosition((int)(1098 * (shoulderScale-fudge)/100));
            shoulder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //telemetry
            telemetry.addData("shoulder difference: ", shoulder1.getCurrentPosition()-shoulder2.getCurrentPosition());
            telemetry.update();
            telemetry.addData("wrist position", wrist.getPosition());
        }
    }
}



