// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.ClimbDownCommand;
import frc.robot.Commands.ClimbStillCommand;
import frc.robot.Commands.ClimbUpCommand;
import frc.robot.Commands.ElevatorDownCommand;
import frc.robot.Commands.ElevatorStillCommand;
import frc.robot.Commands.ElevatorUpCommand;
import frc.robot.Commands.AlgaeGrabCommand;
import frc.robot.Commands.CoralGrabCommand;
import frc.robot.Commands.CoralResetCommand;
import frc.robot.Commands.CoralScoreCommand;
import frc.robot.Commands.CoralPositionStillCommand;
import frc.robot.Commands.AlgaeDownCommand;
import frc.robot.Commands.AlgaePositionStillCommand;
import frc.robot.Commands.AlgaeUpCommand;
import frc.robot.Commands.AlgaeReleaseCommand;
import frc.robot.Commands.AlgaeStillCommand;
import frc.robot.Commands.CoralReleaseCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AlgaePivotSubsystem;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralPivotSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

//current bindings for testing:
//Driver:
//X button - Algae Pivot Down
//Y button - Algae Pivot Up
//Start Button + Menu/Back Button - claw down
//B Button - claw up
//
//Operator:
//Y Button - elevator Up
//A Button - elevator Down
//Right Trigger - coral pivot up
//Right Bumper - coral pivot down
//B button - shoot coral
//A button - intake coral

public class RobotContainer {

  private CoralSubsystem m_coral = new CoralSubsystem(21);

  private CoralPivotSubsystem m_coralPivot = new CoralPivotSubsystem(22);
  private AlgaeSubsystem m_algae = new AlgaeSubsystem(61);
  private AlgaePivotSubsystem m_algaePivot = new AlgaePivotSubsystem(62);

  private ClimbSubsystem m_climb = new ClimbSubsystem(51);
  private ElevatorSubsystem m_elevator = new ElevatorSubsystem();

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
  private final CommandXboxController operator = new CommandXboxController(1);
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  Trigger XButtonOp = operator.x();
  Trigger YButtonOp = operator.y();
  Trigger BButtonOp = operator.b();
  Trigger AButtonOp = operator.a();
  Trigger LeftBumperOp = operator.leftBumper();
  Trigger RightBumperOp = operator.rightBumper();
  Trigger LeftTriggerOp = operator.leftTrigger();
  Trigger RightTriggerOp = operator.rightTrigger();

  Trigger XButtonDriver = joystick.x();
  Trigger YButtonDriver = joystick.y();
  Trigger BButtonDriver = joystick.b();
  Trigger AButtonDriver = joystick.a();
  Trigger LeftBumperDriver = joystick.leftBumper();
  Trigger RightBumperDriver = joystick.rightBumper();
  Trigger LeftTriggerDriver = joystick.leftTrigger();
  Trigger RightTriggerDriver = joystick.rightTrigger();
  Trigger StartButtonDriver = joystick.start();
  Trigger BackButtonDriver = joystick.back();

  ElevatorUpCommand elevatorUp = new ElevatorUpCommand(m_elevator);
  ElevatorDownCommand elevatorDown = new ElevatorDownCommand(m_elevator);
  ElevatorStillCommand elevatorStill = new ElevatorStillCommand(m_elevator);

  ClimbDownCommand climbDown = new ClimbDownCommand(m_climb);
  ClimbUpCommand climbUp = new ClimbUpCommand(m_climb);
  ClimbStillCommand climbStill = new ClimbStillCommand(m_climb);

  CoralResetCommand CoralPositionDown = new CoralResetCommand(m_coralPivot);
  CoralScoreCommand CoralPositionScore = new CoralScoreCommand(m_coralPivot);
  CoralPositionStillCommand CoralPositionStill = new CoralPositionStillCommand(m_coralPivot);

  CoralGrabCommand CoralGrab = new CoralGrabCommand(m_coral);
  CoralReleaseCommand CoralRelease = new CoralReleaseCommand(m_coral);

  AlgaeDownCommand AlgaePositionDown = new AlgaeDownCommand(m_algaePivot);
  AlgaeUpCommand AlgaePositionUp = new AlgaeUpCommand(m_algaePivot);
  AlgaePositionStillCommand AlgaePositionStill = new AlgaePositionStillCommand(m_algaePivot);

  AlgaeGrabCommand AlgaeGrab = new AlgaeGrabCommand(m_algae, m_algaePivot);
  AlgaeReleaseCommand AlgaeRelease = new AlgaeReleaseCommand(m_algae);
  AlgaeStillCommand AlgaeStill = new AlgaeStillCommand(m_algae);
  

  PathPlannerAuto testautoooo;

  // Command marker1Cmd = Commands.print("Passed marker 1");
  // Command markerPHCmd = Commands.print("Print Middle point");

  // private SendableChooser<Command> autoChooser = new
  // SendableChooser<Command>();
  // private static final String kAuto1 = "Auto1";
  // private static final String kAuto2 = "Auto2";

  public RobotContainer() {

    NamedCommands.registerCommand("elevatorUp", elevatorUp);
    NamedCommands.registerCommand("elevatorDown", elevatorDown);
    NamedCommands.registerCommand("elevatorStill", elevatorStill);

    NamedCommands.registerCommand("climbUp", climbUp);
    NamedCommands.registerCommand("climbDown", climbDown);
    NamedCommands.registerCommand("climbStill", climbStill);

    // Coral
    NamedCommands.registerCommand("CoralPositionScore", CoralPositionScore);
    NamedCommands.registerCommand("CoralPositionDown", CoralPositionDown);
    NamedCommands.registerCommand("CoralPositionStill", CoralPositionStill);

    NamedCommands.registerCommand("CoralGrab", CoralGrab);
    NamedCommands.registerCommand("CoralRelease", CoralRelease);

    // Algae
    NamedCommands.registerCommand("AlgaePositionDown", AlgaePositionDown);
    NamedCommands.registerCommand("AlgaePositionUp", AlgaePositionUp);
    NamedCommands.registerCommand("AlgaePositionStill", AlgaePositionStill);

    NamedCommands.registerCommand("AlgaeGrab", AlgaeGrab);
    NamedCommands.registerCommand("AlgaeRelease", AlgaeRelease);
    NamedCommands.registerCommand("AlgaeStill", AlgaeStill);


    testautoooo = new PathPlannerAuto("TestAuto");
    // autoChooser.setDefaultOption("E Auto", new PathPlannerAuto("Example Auto"));
    // autoChooser.addOption("Auto Option 2", new PathPlannerAuto(kAuto2));
    // SmartDashboard.putData("Auto Choices", autoChooser);

    // autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be
    // `Commands.none()`
    // SmartDashboard.putData("Auto Mode", autoChooser);

    configureBindings();

  }

  // private boolean NoButtonsArePressed() {
  // return (!(XButtonOp.getAsBoolean() || YButtonOp.getAsBoolean() ||
  // BButtonOp.getAsBoolean()
  // || AButtonOp.getAsBoolean() || LeftBumperOp.getAsBoolean() ||
  // RightBumperOp.getAsBoolean()));
  // }

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final String m_autoselected = (SmartDashboard.getString("Auto Choices", "Example Auto"));

  // Path Follower
  private Command runAuto = drivetrain.getAutoPath(m_autoselected);

  private final Telemetry logger = new Telemetry(MaxSpeed);

  private void configureBindings() {

    // SmartDashboard.putData("Example Auto", new PathPlannerAuto("Example Auto"));

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

    // algae functions - 
    // NEED: RUN INTAKE LONGER AT PICKUP
    if (RightTriggerDriver.getAsBoolean()) {
      m_algaePivot.algaePivotDown();
      m_algae.IntakeAlgae();
    } else {
      m_algaePivot.SetAlgaePivotMiddle();
      
      if (RightBumperDriver.getAsBoolean()) {
        m_algae.ReleaseAlgae();
      } else {
        m_algae.Still();
      }

      // algae functions for getting encoder values
      // if (XButtonDriver.getAsBoolean()) {
      // m_algaePivot.algaePivotDown();
      // } else if (YButtonDriver.getAsBoolean()) {
      // m_algaePivot.algaePivotUp();
      // } else {
      // m_algaePivot.algaePivotStill();
      // }
    }

    if (LeftTriggerDriver.getAsBoolean()) {
      m_algaePivot.algaePivotUp();
    }

    // coral pivot testing functions
    if (RightTriggerOp.getAsBoolean()) {
      //SmartDashboard.putBoolean("Right Trigger", true);
      m_coralPivot.pivotDown();
    } else if (RightBumperOp.getAsBoolean()) {
      m_coralPivot.pivotUp();
    } else {
      //SmartDashboard.putBoolean("Right Trigger", false);
      m_coralPivot.pivotStill();
    }

    // coral functions
    if (LeftTriggerOp.getAsBoolean()) {
      SmartDashboard.putBoolean("Coral Intake", false);
      SmartDashboard.putBoolean("Coral Release", true);
      m_coral.ReleaseCoral();
    } else if (LeftBumperOp.getAsBoolean()) {
      SmartDashboard.putBoolean("Coral Intake", true);
      SmartDashboard.putBoolean("Coral Release", false);
      m_coral.IntakeCoral();
    } else {
      SmartDashboard.putBoolean("Coral Intake", false);
      SmartDashboard.putBoolean("Coral Release", false);
      m_coral.Still();
    }

    // climb functions for testing
    if (StartButtonDriver.getAsBoolean() && BackButtonDriver.getAsBoolean()) {
      m_climb.climbDown();
    } else if (YButtonDriver.getAsBoolean()) {
      m_climb.climbUp();
    } else {
      m_climb.climbStill();
    }

    // elevator functions
    // m_elevator.periodic();
    if (YButtonOp.getAsBoolean()) {
      m_elevator.SetElevatorHigh();
      m_coralPivot.pivotDown();
    } else if (AButtonOp.getAsBoolean()) {
      m_elevator.SetElevatorLowered();
      m_coralPivot.pivotDown();
    } else if (BButtonOp.getAsBoolean()) {
      m_elevator.SetElevatorMiddle();
      m_coralPivot.pivotDown();
    } else if (XButtonOp.getAsBoolean()) {
      m_elevator.SetElevatorLowered();
      m_coralPivot.pivotUp();
    }

    // Elevator Functions for Testing
    // if (YButtonOp.getAsBoolean()) {
    // m_elevator.ElevatorUp(.2);
    // } else if (AButtonOp.getAsBoolean()) {
    // m_elevator.ElevatorDown(-.2);
    // } else {
    // m_elevator.ElevatorStill();
    // }

  }

  public Command getAutonomousCommand() {
    // SmartDashboard.putString("what is the auto", runAuto.getName());
    // return runAuto;
    return testautoooo;
  }
}
