package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous

public class robot_autonomous_attempt extends LinearOpMode{
    @Override
    public void runOpMode (){
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        //bring stuff from config
        Servo wrist            = hardwareMap.servo.get("wrist");
        Servo hand             = hardwareMap.servo.get("hand");
        DcMotor shoulder1      = hardwareMap.dcMotor.get("shoulder1");
        DcMotor shoulder2      = hardwareMap.dcMotor.get("shoulder2");
        DcMotor rightAntDrive  = hardwareMap.dcMotor.get("rightAntDrive");
        DcMotor rightPostDrive = hardwareMap.dcMotor.get("rightPostDrive");
        DcMotor leftPostDrive  = hardwareMap.dcMotor.get("leftPostDrive");
        DcMotor leftAntDrive   = hardwareMap.dcMotor.get("leftAntDrive");
        ElapsedTime timer = new ElapsedTime();

        //set directions and calibrate
        shoulder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder1.setDirection(DcMotor.Direction.REVERSE);
        shoulder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder2.setDirection(DcMotor.Direction.REVERSE);
        rightAntDrive.setDirection(DcMotor.Direction.REVERSE);
        rightPostDrive.setDirection(DcMotor.Direction.REVERSE);

        //this determines the speed of the arm

        double shoulderScale     = 0.0;

        //set up substitute values for controller inputs
        double drive  = 0;
        double strafe = 0;
        double turn   = 0;
        boolean grab   = false;

        //set servo positions
        wrist.setPosition(0.1);
        hand.setPosition(0.6);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        timer.reset();
        while (opModeIsActive()){

            //////////////EMULATE CONTROLLER INPUTS////////////////
            if (timer.seconds()> 1 && timer.seconds()<1.5){moveRobot(0, 0.3, 0, 30, 0.1, false);}  else {moveRobot(0,0,0,0, 0, false);}

            // if (timer.seconds()>0.5 && timer.seconds()<1) {moveRobot(0.5, 0, 0, 0, 0.15, false);}  else {moveRobot(0,0,0,0, 0, false);}
            // sleep(100);
            // if (timer.seconds()>1 && timer.seconds()<5) {moveRobot(0, 0, 0, 50, 0.15, false);}  else {moveRobot(0,0,0,0, 0, false);}
            // sleep(100);
            // if (timer.seconds()>0.5 && timer.seconds()<0.8) {moveRobot(0.5, 0, 0, 50, 0.15, false);}  else {moveRobot(0,0,0,0, 0, false);}
            //if (timer.seconds()>0.8 && timer.seconds()<3) {moveRobot(0, 0, 0, 50); wrist.setPosition(0.4);}  else {moveRobot(0,0,0,0);}
            // if (timer.seconds()>0.6 && timer.seconds()<1) {moveRobot(0, 0, 0, 45);} else {moveRobot(0,0,0,0); grab = false;}

            //////////////READ AND SET POSITIONS AND SPEEDS/////////


            //////////////TELEMETRY/////////////
//            telemetry.addData("shoulder scale: ", shoulderScale);
//            telemetry.addData("time: ", timer.seconds());
            telemetry.update();
        }

    }

    /////////////////////MOVE ROBOT METHOD///////////////
    public void moveRobot(double drive, double strafe, double turn, double shoulderScale, double wrist, boolean grab){
        //////////////SET UP//////////////////
        //Servo wrist            = hardwareMap.servo.get("wrist");
        Servo hand             = hardwareMap.servo.get("hand");
        DcMotor shoulder1      = hardwareMap.dcMotor.get("shoulder1");
        DcMotor shoulder2      = hardwareMap.dcMotor.get("shoulder2");
        DcMotor rightAntDrive  = hardwareMap.dcMotor.get("rightAntDrive");
        DcMotor rightPostDrive = hardwareMap.dcMotor.get("rightPostDrive");
        DcMotor leftPostDrive  = hardwareMap.dcMotor.get("leftPostDrive");
        DcMotor leftAntDrive   = hardwareMap.dcMotor.get("leftAntDrive");

        shoulder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder1.setDirection(DcMotor.Direction.REVERSE);
        shoulder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder2.setDirection(DcMotor.Direction.REVERSE);
        rightAntDrive.setDirection(DcMotor.Direction.REVERSE);
        rightPostDrive.setDirection(DcMotor.Direction.REVERSE);


        /////////////CALCULATE POWER/////////////
        double frontLeftPower  = (drive + strafe - turn);
        double backLeftPower   = (drive - strafe - turn);
        double frontRightPower = (drive - strafe + turn);
        double backRightPower  = (drive + strafe + turn);

        /////////////////SEND POWER////////////////
        rightAntDrive.setPower((double)frontRightPower);
        rightPostDrive.setPower((double)backRightPower);
        leftPostDrive.setPower((double)backLeftPower);
        leftAntDrive.setPower((double)frontLeftPower);

        //grabber
        if (grab) {
            hand.setPosition(0.75);
//            telemetry.addData("Hand", grab);
        }
        else {hand.setPosition(0.35);}

        //arm power
        shoulder1.setPower(0.8);
        shoulder1.setTargetPosition((int)(1098 * shoulderScale/100));
        shoulder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shoulder2.setPower(0.8);
        shoulder2.setTargetPosition((int)(1098 * shoulderScale/100));
        shoulder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

}




