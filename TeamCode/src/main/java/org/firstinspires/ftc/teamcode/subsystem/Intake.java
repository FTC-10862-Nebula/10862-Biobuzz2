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

public class Intake extends SubsystemBase {
	public enum IntakeValue {
        OUTTAKE (0),//
        INTAKE  (0),
        INTAKE_MORE(0),
        STOP    (0);
		
        public final double power;
        IntakeValue(double power) {
            this.power = power;
        }
    }
	private final NebulaMotor intakeL, intakeR;
	private final Telemetry tl;
	private final PIDFController controller = new PIDFController(0,0,0,0);
	public Intake(Telemetry tl, HardwareMap hw, boolean isEnabled) {
		this.intakeL = new NebulaMotor(hw, "intakeL",
			DcMotorSimple.Direction.FORWARD,
			DcMotor.ZeroPowerBehavior.FLOAT,
			isEnabled);
		this.intakeR = new NebulaMotor(hw, "intakeR",
			DcMotorSimple.Direction.REVERSE,
			DcMotor.ZeroPowerBehavior.FLOAT,
			isEnabled);
		this.tl = tl;
	}
	
	@Override
	public void periodic() {
		intakeL.setPower(controller.calculate());
		intakeR.setPower(controller.calculate());
		tl.addData("Intake SetPoint:", controller.getSetPoint());
	}
	
	public Command setPosition(IntakeValue intakeValue){
		switch(intakeValue) {
			default:
				return new SequentialCommandGroup(
					new InstantCommand(()-> setSetPoint(intakeValue))
				);
		}
	}
	//Auto?
	public Action setPositionAction(IntakeValue intakeValue){
		return (Action) setPosition(intakeValue);
	}
	public void setSetPoint(IntakeValue intakeValue){
		controller.setSetPoint(intakeValue.power);
	}
}
