package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class Robot {
    private final Servo wrist;
    private final Servo hand;
    private final DcMotor shoulder1;
    private final DcMotor shoulder2;
    private final DcMotor rightAntDrive;
    private final DcMotor rightPostDrive;
    private final DcMotor leftPostDrive;
    private final DcMotor leftAntDrive;

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

    // These are sample positions, feel free to modify
    enum ArmPosition {
        initial,
        travelling,

        // Collecting...
        collectFromGround,
        collectFromSubmersible,

        // Specimen placement
        highChamberReach, // > 26"
        highChamberHook, // 26"

        lowChamberReach, // >13"
        lowChamberHook, // 13"

        // Sample placement
        highBasket, // > 43"

        lowBasket, // > 25.75"

        // Rungs
        highRung, // 36"
        lowRung, // 20"
    }

    public void moveArmToPosition(ArmPosition newPosition) {
        switch (newPosition) {
            case initial:
                break;
            case travelling:
                break;
            case collectFromGround:
                break;
            case collectFromSubmersible:
                break;
            case highChamberReach:
                break;
            case highChamberHook:
                break;
            case lowChamberReach:
                break;
            case lowChamberHook:
                break;
            case highBasket:
                break;
            case lowBasket:
                break;
            case highRung:
                break;
            case lowRung:
                break;
        }
    }

    public void handGrab() {
        // TODO make the grabber wheel spin
    }

    public void handGrip() {
        // the robot has grabbed something, now we maintain just enough power to keep it
        // TODO
    }

    public void handRelease() {
        // TODO make the grabber when reverse
    }
}