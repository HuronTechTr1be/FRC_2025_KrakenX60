package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.generated.TunerConstants.ElevatorSubsystemConstants;

//NEED TO TEST VALUES AND CHANGE CONSTANTS

//start: 31 = -349 & 32 = -438
// isRaised: 31 = -433 & 32 = -521 (went about 90)

public class ElevatorBasic {
  private CANSparkMax elevator;
  private RelativeEncoder m_RelativeEncoder;

  private String m_elevatorSide;
  private SparkLimitSwitch m_LimitSwitch;
  public String target;

  public ElevatorBasic(int deviceId, String elevatorSide) {

    elevator = new CANSparkMax(deviceId, MotorType.kBrushless);
    m_RelativeEncoder = elevator.getEncoder();
    m_LimitSwitch = elevator.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

    m_elevatorSide = elevatorSide;

  }

  public void setRampRate(int rampRate) {

    elevator.setOpenLoopRampRate(rampRate);

  }

  public void Up() {
    // SmartDashboard.putNumber(m_elevatorSide, getPosition());
    if (isRaised())
      elevator.set(0);
    else
      elevator.set(ElevatorSubsystemConstants.k_ElevatorSpeedUp);
  }

  public void Up(double speed) {
    if (isRaised())
      elevator.set(0);
    else
      elevator.set((speed));
  }

  public void Down() {
    // SmartDashboard.putNumber(m_elevatorSide, getPosition());
    if (isLowered())
      elevator.set(0);
    else
      elevator.set(ElevatorSubsystemConstants.k_ElevatorSpeedDown);

  }

  public void Down(double speed) {

    if (isLowered())
      elevator.set(0);
    else
      elevator.set(speed);

  }

  // don't think we'll need these
  // public void adjustedArmsDownInit(double speed) {

  // elevator.set(speed);

  // }

  // public void ArmsDownInit() {

  // arm.set(ArmConstants.k_initArmSpeedRoboInit);

  // }

  public void Still() {
    // SmartDashboard.putNumber(m_elevatorSide, getPosition());
    elevator.set(0);

  }

  public boolean isRaised() {

    // Don't know if 5 is a reasonable range here, need to test encoders and get a
    // sense of scale
    return Math.abs(ElevatorSubsystemConstants.m_PointRaised - m_RelativeEncoder.getPosition()) <= 5;

  }

  public boolean isMiddle() {
    return Math.abs(ElevatorSubsystemConstants.m_PointMiddle - m_RelativeEncoder.getPosition()) <= 5;
  }

  public boolean isLow() {
    return Math.abs(ElevatorSubsystemConstants.m_PointLow - m_RelativeEncoder.getPosition()) <= 5;
  }

  public boolean isLowered() {

    if (m_LimitSwitch.isPressed()) {
      m_RelativeEncoder.setPosition(0);
      return true;
    } else {
      return false;
    }

  }

  public double getCurrent() {
    double ElevatorCurrent = elevator.getOutputCurrent();
    SmartDashboard.putNumber(m_elevatorSide + "ArmCurrent", ElevatorCurrent);
    return ElevatorCurrent;

  }

  public void finishZero() {

    m_RelativeEncoder.setPosition(0);
    elevator.setOpenLoopRampRate(0);
    elevator.burnFlash();

  }

  public double getPosition() {
    return m_RelativeEncoder.getPosition();
  }

  public void toScoreHigh() {
    target = "High";
    if (isRaised()) {
      Still();
    } else if (getPosition() < ElevatorSubsystemConstants.m_PointRaised) {
      Up();
    }
  }

  public void toScoreMiddle() {
    target = "Middle";
    if (isMiddle()) {
      Still();
    } else if (getPosition() < ElevatorSubsystemConstants.m_PointMiddle) {
      Up();
    } else if (getPosition() > ElevatorSubsystemConstants.m_PointMiddle) {
      Down();
    }
  }

  public void toScoreLow() {
    target = "Low";
    if (isLow()) {
      Still();
    } else if (getPosition() < ElevatorSubsystemConstants.m_PointLow) {
      Up();
    } else if (getPosition() > ElevatorSubsystemConstants.m_PointLow) {
      Down();
    }
  }

  public void toLowered() {
    target = "Lowered";
    if (isLowered()) {
      Still();
    } else if (getPosition() > 0) {
      Down();
    }
  }

  public void periodic() {

    SmartDashboard.putNumber(m_elevatorSide, getPosition());

    // if (isLowered()) {
    // m_RelativeEncoder.setPosition(0);
    // Still();
    // }
    // if (isLow()) {
    // if(target == "Low"){
    // Still();
    // }
    // }
    // if (isMiddle()) {
    // if(target == "Middle"){
    // Still();
    // }
    // }
    // if (isRaised()) {
    // Still();
    // }

    // if (elevator.getDeviceId() == 31) {

    // if (isLowered()) {
    // m_RelativeEncoder.setPosition(0);
    // }

    // } else if (elevator.getDeviceId() == 32) {

    // if (isLowered()) {
    // m_RelativeEncoder.setPosition(0);
    // }

  }

}
