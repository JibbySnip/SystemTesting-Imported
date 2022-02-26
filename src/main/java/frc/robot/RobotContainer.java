// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.RunMotorsCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    CANSparkMax leftMotor1 = new CANSparkMax(4, MotorType.kBrushless);
    CANSparkMax leftMotor2 = new CANSparkMax(5, MotorType.kBrushless);
    CANSparkMax leftMotor3 = new CANSparkMax(6, MotorType.kBrushless);
    CANSparkMax rightMotor1 = new CANSparkMax(1, MotorType.kBrushless);
    CANSparkMax rightMotor2 = new CANSparkMax(2, MotorType.kBrushless);
    CANSparkMax rightMotor3 = new CANSparkMax(3, MotorType.kBrushless);

    // An ExampleCommand will run in autonomous
    return new SequentialCommandGroup(
      new ParallelRaceGroup(
        new RunMotorsCommand(leftMotor1, rightMotor1),
        new WaitCommand(3)),
      new ParallelRaceGroup(
        new RunMotorsCommand(leftMotor2, rightMotor2),
        new WaitCommand(3)),
      new ParallelRaceGroup(
        new RunMotorsCommand(leftMotor3, rightMotor3),
        new WaitCommand(3))
    );
  }
}
