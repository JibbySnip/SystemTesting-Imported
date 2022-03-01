// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.concurrent.CancellationException;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.commands.RunDriveMotorsCommand;
import frc.robot.utilities.XboxController;
import frc.robot.utilities.XboxController.Axis;
import frc.robot.utilities.XboxController.Button;
import frc.robot.utilities.XboxController.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  XboxController driveController = new XboxController(0);

  CANSparkMax climber1 = new CANSparkMax(14, MotorType.kBrushless);
  CANSparkMax climber2 = new CANSparkMax(15, MotorType.kBrushless);

  CANSparkMax leftMotor1 = new CANSparkMax(4, MotorType.kBrushless);
  CANSparkMax leftMotor2 = new CANSparkMax(5, MotorType.kBrushless);
  CANSparkMax leftMotor3 = new CANSparkMax(6, MotorType.kBrushless);
  CANSparkMax rightMotor1 = new CANSparkMax(1, MotorType.kBrushless);
  CANSparkMax rightMotor2 = new CANSparkMax(2, MotorType.kBrushless);
  CANSparkMax rightMotor3 = new CANSparkMax(3, MotorType.kBrushless);

  CANSparkMax indexer = new CANSparkMax(12, MotorType.kBrushless);

  CANSparkMax intake = new CANSparkMax(11, MotorType.kBrushless);

  CANSparkMax yaw = new CANSparkMax(13, MotorType.kBrushless);

  CANSparkMax pitch = new CANSparkMax(0, MotorType.kBrushless); // fix this :(

  CANSparkMax flywheel1 = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax flywheel2 = new CANSparkMax(8, MotorType.kBrushless);

  // The robot's subsystems and commands are defined here...

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
  private void configureButtonBindings() {
    
    driveController.toggleWhenPressed(XboxController.Button.LEFT_BUMPER, 
    new StartEndCommand(() -> {
      pitch.set(0.5);
    }, 
    () -> {
      pitch.set(-0.5);
    }));

    driveController.toggleWhenPressed(XboxController.Button.RIGHT_BUMPER, 
    new StartEndCommand(() -> {
      pitch.set(-0.5);
    }, 
    () -> {
      pitch.set(0);
    }));

    driveController.toggleWhenPressed(XboxController.POV.LEFT, 
    new StartEndCommand(() -> {
      yaw.set(0.5);
    }, 
    () -> {
      yaw.set(-0.5);
    }));

    driveController.toggleWhenPressed(XboxController.POV.RIGHT, 
    new StartEndCommand(() -> {
      yaw.set(-0.5);
    }, 
    () -> {
      yaw.set(0);
    }));

    driveController.toggleWhenPressed(XboxController.POV.UP,
    new StartEndCommand(
      () -> {
        climber1.set(0.6);
        climber2.set(-0.6);
      }, 
      () -> {
        climber1.set(0);
        climber2.set(0);
    }));

    driveController.toggleWhenPressed(XboxController.POV.DOWN,
    new StartEndCommand(
      () -> {
        climber1.set(-0.6);
        climber2.set(0.6);
      }, 
      () -> {
        climber1.set(0);
        climber2.set(0);
    }));

    driveController.toggleWhenPressed(Button.X, 
    new StartEndCommand(
      () -> {
        indexer.set(0.6);
      }, 
      () -> {
        indexer.set(0);
      }));

    driveController.toggleWhenPressed(Button.A,
    new StartEndCommand(
      () -> {
        flywheel1.set(-0.6);
        flywheel2.set(0.6);
      }, 
      () -> {
        flywheel1.set(0);
        flywheel2.set(0);
      }));
  }

  public void doJoysticks() {
    setDriveMotors(driveController.getAxisValue(Axis.LEFT_Y), driveController.getAxisValue(Axis.RIGHT_Y));
    flywheel1.set(driveController.getAxisValue(Axis.RIGHT_TRIGGER));
    flywheel2.set(-driveController.getAxisValue(Axis.RIGHT_TRIGGER));
    intake.set(driveController.getAxisValue(Axis.LEFT_TRIGGER));
  }

  private void setDriveMotors(double left, double right) {
    leftMotor1.set(left);
    leftMotor2.set(left);
    leftMotor3.set(left);
    rightMotor1.set(right);
    rightMotor2.set(right);
    rightMotor3.set(right);
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    
    leftMotor1.setIdleMode(IdleMode.kCoast);
    leftMotor2.setIdleMode(IdleMode.kCoast);
    leftMotor3.setIdleMode(IdleMode.kCoast);
    rightMotor1.setIdleMode(IdleMode.kCoast);
    rightMotor2.setIdleMode(IdleMode.kCoast);
    rightMotor3.setIdleMode(IdleMode.kCoast);

    // An ExampleCommand will run in autonomous
    return new SequentialCommandGroup(
      new ParallelRaceGroup(
        new RunDriveMotorsCommand(leftMotor1, rightMotor1),
        new WaitCommand(3)),
      new ParallelRaceGroup(
        new RunDriveMotorsCommand(leftMotor2, rightMotor2),
        new WaitCommand(3)),
      new ParallelRaceGroup(
        new RunDriveMotorsCommand(leftMotor3, rightMotor3),
        new WaitCommand(3))
    );
  }
}
