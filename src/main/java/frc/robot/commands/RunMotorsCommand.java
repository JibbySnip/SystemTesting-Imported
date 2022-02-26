// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** An example command that uses an example subsystem. */
public class RunMotorsCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private CANSparkMax motor1, motor2;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RunMotorsCommand(CANSparkMax motor1, CANSparkMax motor2) {
    this.motor1 = motor1;
    // Use addRequirements() here to declare subsystem dependencies.
    this.motor2 = motor2;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    motor1.set(0.1);
    motor2.set(-0.1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    StringBuilder motor1Obs = new StringBuilder("Motor 1 Encoder Position: ");
    motor1Obs.append(motor1.getEncoder().getPosition());
    motor1Obs.append(" Motor 1 Encoder Velocity");
    motor1Obs.append(motor1.getEncoder().getVelocity());
    StringBuilder motor2Obs = new StringBuilder("Motor 2 Encoder Position: ");
    motor2Obs.append(motor2.getEncoder().getPosition());
    motor2Obs.append(" Motor 2 Encoder Velocity: ");
    motor2Obs.append(motor2.getEncoder().getVelocity());
    
    new PrintCommand(motor1Obs.toString());
    new PrintCommand(motor2Obs.toString());

    motor1.setVoltage(0);
    motor2.setVoltage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
