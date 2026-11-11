package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.NebulaServo;

//2 motors
public class Hood extends SubsystemBase {
	public enum Value {
		UP (0,0),
		DOWN  (0,0);
		
		private final double posL, posR;
		Value(double posL, double posR) {
			this.posL = posL;
			this.posR = posR;
		}
	}
	private final NebulaServo hoodL, hoodR;
	private final Telemetry tl;
	private Value value;
	public Hood(Telemetry tl, HardwareMap hw, boolean isEnabled) {
		this.hoodL = new NebulaServo(hw, "hoodL", Servo.Direction.FORWARD, isEnabled);
		this.hoodR = new NebulaServo(hw, "hoodR", Servo.Direction.REVERSE, isEnabled);
		this.tl = tl;
	}
	
	@Override
	public void periodic() {
		tl.addData("Hood Pos:", value);
	}
	
	public Command setPosition(Value value){
		switch(value) {
			default:
				return new SequentialCommandGroup(
					new InstantCommand(()-> setSetPoint(value))
				);
		}
	}
	//Auto?
	public Action setPositionAction(Value value){
		return (Action) setPosition(value);
	}
	public void setSetPoint(Value value){
		hoodL.setServoPos(value.posL);
		hoodR.setServoPos(value.posR);
		this.value=value;
	}
}
