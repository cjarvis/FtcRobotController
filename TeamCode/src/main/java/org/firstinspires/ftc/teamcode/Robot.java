package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class Robot {
    Servo wrist;
    Servo hand;
    DcMotor shoulder1;
    DcMotor shoulder2;
    DcMotor rightAntDrive;
    DcMotor rightPostDrive;
    DcMotor leftPostDrive;
    DcMotor leftAntDrive;

    Telemetry telemetry;

    Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        wrist = hardwareMap.servo.get("wrist");
        hand = hardwareMap.servo.get("hand");
        shoulder1 = hardwareMap.dcMotor.get("shoulder1");
        shoulder2 = hardwareMap.dcMotor.get("shoulder2");
        rightAntDrive = hardwareMap.dcMotor.get("rightAntDrive");
        rightPostDrive = hardwareMap.dcMotor.get("rightPostDrive");
        leftPostDrive = hardwareMap.dcMotor.get("leftPostDrive");
        leftAntDrive = hardwareMap.dcMotor.get("leftAntDrive");
        this.telemetry = telemetry;
    }

    public void init() {
        shoulder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder1.setDirection(DcMotor.Direction.REVERSE);
        shoulder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder2.setDirection(DcMotor.Direction.REVERSE);
        leftAntDrive.setDirection(DcMotor.Direction.REVERSE);
        leftPostDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setWristPosition(double v) {
        wrist.setPosition(v);
    }

    public void setHandPosition(double v) {
        hand.setPosition(v);
    }

    public void stop() {
        shoulder1.setPower(-1);
        shoulder2.setPower(-1);
        shoulder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void move(double drive, double strafe, double turn, boolean goSlow) {

        double driveCoefficient;
        if (goSlow) {
            driveCoefficient = 0.3;
        } else {
            driveCoefficient = 0.75;
        }

        double frontLeftPower = (drive + strafe - turn);
        double backLeftPower = (drive - strafe - turn);
        double frontRightPower = (drive - strafe + turn);
        double backRightPower = (drive + strafe + turn);

        rightAntDrive.setPower(Math.pow(frontRightPower, 3) * driveCoefficient);
        rightPostDrive.setPower(Math.pow(backRightPower, 3) * driveCoefficient);
        leftPostDrive.setPower(Math.pow(backLeftPower, 3) * driveCoefficient);
        leftAntDrive.setPower(Math.pow(frontLeftPower, 3) * driveCoefficient);
    }

    public void moveShoulderTo(int position) {
        shoulder1.setPower(0.8);
        shoulder1.setTargetPosition(position);
        shoulder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shoulder2.setPower(0.8);
        shoulder2.setTargetPosition(position);
        shoulder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}