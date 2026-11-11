package org.firstinspires.ftc.teamcode.subsystem;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.NebulaMotor;

public class Shooter extends SubsystemBase {
	public enum ShooterValue {
		SHOOT (0),
		HOLD  (0),
		SHOOT_FAR(0),
		STOP    (0);
		
		public final double power;
		ShooterValue(double power) {
			this.power = power;
		}
	}
	private final NebulaMotor shooterL, shooterR;
	private final Telemetry tl;
	private final PIDFController controller = new PIDFController(0,0,0,0);
	public Shooter(Telemetry tl, HardwareMap hw, boolean isEnabled) {
		this.shooterL = new NebulaMotor(hw, "intakeL",
			DcMotorSimple.Direction.FORWARD,
			DcMotor.ZeroPowerBehavior.FLOAT,
			isEnabled);
		this.shooterR = new NebulaMotor(hw, "intakeR",
			DcMotorSimple.Direction.REVERSE,
			DcMotor.ZeroPowerBehavior.FLOAT,
			isEnabled);
		this.tl = tl;
	}
	
	@Override
	public void periodic() {
		shooterL.setPower(controller.calculate());
		shooterR.setPower(controller.calculate());
		tl.addData("Intake SetPoint:", controller.getSetPoint());
	}
	
	public Command setPosition(ShooterValue value){
		switch(value) {
			default:
				return new SequentialCommandGroup(
					new InstantCommand(()-> setSetPoint(value))
				);
		}
	}
	//Auto?
	public Action setPositionAction(ShooterValue value){
		return (Action) setPosition(value);
	}
	public void setSetPoint(ShooterValue value){
		controller.setSetPoint(value.power);
	}
}
