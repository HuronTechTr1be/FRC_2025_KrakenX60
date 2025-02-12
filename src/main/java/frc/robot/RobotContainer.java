// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.ClimbDownCommand;
import frc.robot.Commands.ClimbStillCommand;
import frc.robot.Commands.ClimbUpCommand;
import frc.robot.Commands.ElevatorUpCommand;
import frc.robot.Commands.ElevatorDownCommand;
import frc.robot.Commands.ElevatorStillCommand;
import frc.robot.Commands.GrabAlgaeCommand;
import frc.robot.Commands.GrabCoralCommand;
import frc.robot.Commands.PivotResetCommand;
import frc.robot.Commands.PivotStillCommand;
import frc.robot.Commands.PivotScoreCommand;
import frc.robot.Commands.PositionDownCommand;
import frc.robot.Commands.PositionStillCommand;
import frc.robot.Commands.PositionUpCommand;
import frc.robot.Commands.ReleaseAlgaeCommand;
import frc.robot.Commands.ReleaseCoralCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.AlgaePivotSubsystem;
import frc.robot.subsystems.CoralPivotSubsystem;

public class RobotContainer {

  private CoralSubsystem m_coral = new CoralSubsystem(21);
  private CoralPivotSubsystem m_pivot = new CoralPivotSubsystem(22);
  private AlgaeSubsystem m_algae = new AlgaeSubsystem(61);
  private AlgaePivotSubsystem m_position = new AlgaePivotSubsystem(62);
  private ClimbSubsystem m_climb = new ClimbSubsystem(51);
  private ElevatorSubsystem m_elevator = new ElevatorSubsystem();

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
  private final XboxController operator = new XboxController(1);
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  JoystickButton XButton = new JoystickButton(operator, XboxController.Button.kX.value);
  JoystickButton YButton = new JoystickButton(operator, XboxController.Button.kY.value);
  JoystickButton BButton = new JoystickButton(operator, XboxController.Button.kB.value);
  JoystickButton AButton = new JoystickButton(operator, XboxController.Button.kA.value);
  JoystickButton LeftBumper = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
  JoystickButton RightBumper = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
  JoystickButton LeftTrigger = new JoystickButton(operator, XboxController.Axis.kLeftTrigger.value);
  JoystickButton RightTrigger = new JoystickButton(operator, XboxController.Axis.kRightTrigger.value);

  ElevatorUpCommand elevatorUp = new ElevatorUpCommand(m_elevator);
  ElevatorDownCommand elevatorDown = new ElevatorDownCommand(m_elevator);
  ElevatorStillCommand elevatorStill = new ElevatorStillCommand(m_elevator);

  ClimbDownCommand climbDown = new ClimbDownCommand(m_climb);
  ClimbUpCommand climbUp = new ClimbUpCommand(m_climb);
  ClimbStillCommand climbStill = new ClimbStillCommand(m_climb);

  PivotResetCommand pivotDown = new PivotResetCommand(m_pivot);
  PivotScoreCommand pivotScore = new PivotScoreCommand(m_pivot);
  PivotStillCommand pivotStill = new PivotStillCommand(m_pivot);

  PositionDownCommand positionDown = new PositionDownCommand(m_position);
  PositionUpCommand positionUp = new PositionUpCommand(m_position);
  PositionStillCommand positionStill = new PositionStillCommand(m_position);

  GrabAlgaeCommand grabAlgae = new GrabAlgaeCommand(m_algae, m_position);
  ReleaseAlgaeCommand releaseAlgae = new ReleaseAlgaeCommand(m_algae);

  GrabCoralCommand grabCoral = new GrabCoralCommand(m_coral);
  ReleaseCoralCommand releaseCoral = new ReleaseCoralCommand(m_coral);

  private boolean NoButtonsArePressed() {
    return (!(XButton.getAsBoolean() || YButton.getAsBoolean() || BButton.getAsBoolean()
        || AButton.getAsBoolean() || LeftBumper.getAsBoolean() || RightBumper.getAsBoolean()));
  }

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final Telemetry logger = new Telemetry(MaxSpeed);

  private void configureBindings() {

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MaxSpeed / 7) // Drive forward with
            // negative Y (forward)
            .withVelocityY(-joystick.getLeftX() * MaxSpeed / 7) // Drive left with negative X (left)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate / 7) // Drive counterclockwise with negative X
                                                                            // (left)
        ));

    joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
    joystick.b().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));

    // reset the field-centric heading on left bumper press
    joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

    // LeftBumper.whileTrue(grabCoral);
    // LeftTrigger.whileTrue(releaseCoral);
    // RightBumper.whileTrue(grabAlgae);
    // RightTrigger.whileTrue(releaseAlgae);

    // low elevator = A
    // med elevator = B
    // high elevator = Y

    // algae pivot - when pushing button go down to a set point and when released go
    // up to starting position = X

    // coral pivot down = down arrow
    // coral pivot score = left arrow
    // coral pivot intake = up arrow

    // climb - a more difficult way to push like 2 buttons at the same time

  }

  public void periodic() {

    m_elevator.periodic();

    if (YButton.getAsBoolean()) {
      //m_elevator.SetElevatorHigh();
      m_elevator.ElevatorUp(.2);
    } else if (AButton.getAsBoolean()) {
      m_elevator.ElevatorDown(-.2);
      //m_elevator.SetElevatorLowered();
    } else if (BButton.getAsBoolean()) {
      //m_elevator.SetElevatorMiddle();
    }  
    else {
      m_elevator.ElevatorStill();
    }

    // if (XButton.getAsBoolean()) {
    //   //m_algae.IntakeAlgae();
    //   //m_coral.IntakeCoral();
    //   //m_elevator.SetElevatorMiddle();
    // } else {
    //   //m_algae.Still();
    //   //m_coral.Still();
    // }
  }

  public RobotContainer() {
    configureBindings();
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
