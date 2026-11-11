package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class NebulaServo {
    private final Servo servoPos;
    private final boolean isEnabled;
    public NebulaServo(HardwareMap hw, String deviceId,
                     Servo.Direction direction,
                     boolean isEnabled){
        servoPos = hw.get(Servo.class, deviceId);
        servoPos.scaleRange(0,1.0);
        servoPos.setDirection(direction);
        this.isEnabled = isEnabled;
    }
    
    public void setServoPos(double pos) {
        if (!isEnabled) return;
        else servoPos.setPosition(pos);
    }
    
    public double getServoPos(){
        return servoPos.getPosition();
    }

}
