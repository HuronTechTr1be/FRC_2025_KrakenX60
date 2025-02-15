package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.ElevatorSubsystemConstants;


//NEED TO TEST VALUES AND CHANGE CONSTANTS


public class ElevatorSubsystem extends SubsystemBase {

  //Need to clarify which side is which and make clear what we're defining as left and right  
  private ElevatorBasic m_elevatorLeft; // = new ElevatorBasic(31, "left");
  private ElevatorBasic m_elevatorRight; // = new ElevatorBasic(32, "right");
  private boolean m_findHome;
  private boolean m_movingDown = false;
  private boolean m_movingUp = false;
  private String target = new String();
  

  public ElevatorSubsystem() {
      
    m_elevatorLeft = new ElevatorBasic(31, "left");
    m_elevatorRight = new ElevatorBasic( 32, "right");
    SetZeroInit();
  }



  public void SetZeroInit() {

    m_findHome = true;
    m_elevatorLeft.setZero();
    m_elevatorRight.setZero();

    if (atLowerLimit() == false) {
      //SetElevatorLowered();
      ElevatorDown(-.2);
    } else {
      m_findHome = false;
    }
  }

  private void SetZeroFinish() {

    ElevatorDown();

    if (atLowerLimit()) {
      ElevatorStill();
      m_elevatorLeft.setZero();
      m_elevatorRight.setZero();

      m_findHome = false;
    }
  }


  private void UpdateDashboard() {

    SmartDashboard.putBoolean("Elevator Find Home", m_findHome);
    SmartDashboard.putBoolean("Elevator Lowered", atLowerLimit());
    SmartDashboard.putBoolean("Elevator Raised", atUpperLimit());
    SmartDashboard.putNumber("Elevator Left Position", m_elevatorLeft.getPosition());
    SmartDashboard.putNumber("Elevator Right Position", m_elevatorRight.getPosition());
  }

  public void periodic() {

    if (m_findHome) {
      SetZeroFinish();
    }

    if (ElevatorLowered()) {
    m_elevatorLeft.setZero();
    m_elevatorRight.setZero();
      if(target == "Lowered"){
        ElevatorStill();
      }
    }

    if (ElevatorLow()) {
    if(target == "Low"){
    ElevatorStill();
    }
    }
    if (ElevatorMiddle()) {
      if(target == "Middle") {
      ElevatorStill();
      }
    }
    if (ElevatorRaised()) {
      if(target=="High"){
      ElevatorStill();
      }
    }

    //redundant now I think - was this causing the jumping on and off issue?
    // if ((atLowerLimit() && m_movingUp == false) || (atUpperLimit() && m_movingDown == false)) {
    //   ElevatorStill();
    // }

    m_elevatorRight.periodic();
    m_elevatorLeft.periodic();
    
    UpdateDashboard();

  }

  public double position(){
    return m_elevatorRight.getPosition();
  }

  public boolean atLowerLimit() {
    return m_elevatorLeft.isOnSwitch();
  }

  public boolean atUpperLimit() {
    return m_elevatorRight.isOnSwitch();
  }
  
  public boolean ElevatorLowered() {
    return atLowerLimit();
  }
  
  public boolean ElevatorRaised() {
    return atUpperLimit();
  }

  public boolean ElevatorMiddle() {
    return (Math.abs(position()-ElevatorSubsystemConstants.m_PointMiddle)<5);
  }

  public boolean ElevatorLow() {
    return (Math.abs(position()-ElevatorSubsystemConstants.m_PointLow)<5);
  }

  public void ElevatorDown(double speed) {

    if(ElevatorLowered()){
      m_elevatorLeft.Still();
      m_elevatorRight.Still();
    }
    else{
    m_elevatorLeft.Down(speed);
    m_elevatorRight.Down(speed);
    m_movingDown = true;

    }

  }

  public void ElevatorDown() {
    
    m_movingDown = true;

    if(ElevatorLowered()){
      m_elevatorLeft.Still();
      m_elevatorRight.Still();
    }
    else{
      m_elevatorLeft.Down();
      m_elevatorRight.Down();
      m_movingDown = true;

    }


  }

 public void ElevatorUp(double speed) {

    if(ElevatorRaised()){
      m_elevatorLeft.Still();
      m_elevatorRight.Still();
    }
    else{
    m_elevatorLeft.Up(speed);
    m_elevatorRight.Up(speed);
    m_movingUp = true;

    }

  }

  public void ElevatorUp() {
    if(ElevatorRaised()){
      m_elevatorLeft.Still();
      m_elevatorRight.Still();
    }
    else{
    m_elevatorLeft.Up();
    m_elevatorRight.Up();
    m_movingUp = true;

    }

  }

  public void ElevatorStill(){
    m_elevatorLeft.Still();
    m_elevatorRight.Still();

    m_movingDown = false;
    m_movingUp = false;

  }

  public void SetElevatorHigh() {
    target = "High";
    if (ElevatorRaised()) {
      ElevatorStill();
    } else{ //if (getPosition() < ElevatorSubsystemConstants.m_PointRaised) {
      ElevatorUp();
    }
  }

  public void SetElevatorMiddle() {
    target = "Middle";
    if (ElevatorMiddle()) {
      ElevatorStill();
    } else if (position() < ElevatorSubsystemConstants.m_PointMiddle) {
      ElevatorUp();
    } else if (position() > ElevatorSubsystemConstants.m_PointMiddle) {
      ElevatorDown();
    }
  }

  public void SetElevatorLow() {
    target = "Low";
    if (ElevatorLow()) {
      ElevatorStill();
    } else if (position() < ElevatorSubsystemConstants.m_PointLow) {
      ElevatorUp();
    } else if (position() > ElevatorSubsystemConstants.m_PointLow) {
      ElevatorDown();
    }
  }

  public void SetElevatorLowered() {
    target = "Lowered";
    if (ElevatorLowered()) {
      ElevatorStill();
    } else{ // if (getPosition() > 0) {
      ElevatorDown();
    }
  }

  

  

}