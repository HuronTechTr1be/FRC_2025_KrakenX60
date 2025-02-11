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
  

  public ElevatorSubsystem() {
      
    m_elevatorLeft = new ElevatorBasic(31, "left");
    m_elevatorRight = new ElevatorBasic(32, "right");
    SetZeroInit();
  }

  //public double m_combinedEncoder(){
  //  return (m_elevatorLeft.getPosition()+m_elevatorRight.getPosition())/2;
  //}


  public void SetZeroInit() {

    m_findHome = true;
    m_elevatorLeft.setZero();
    m_elevatorRight.setZero();

    if (atLowerLimit() == false) {
      //SetElevatorLowered();
      ElevatorDown();
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

    //need to mess with timing here, probably won't stay at 1000
    // for( int i=0; i<1000; i++){
        
    // SmartDashboard.putNumber("elevatorSetZero loop value", i);
    // if(m_elevatorRight.isLowered() || m_elevatorLeft.isLowered()){
    //     break;
    // }

    // }

    //m_elevatorLeft.Still();
    //m_elevatorRight.Still();

    //m_elevatorLeft.finishZero();
    //m_elevatorRight.finishZero();



  // public void raiseElevatorPeriodic() {

  //   if(m_elevatorRight.isLowered() || m_elevatorLeft.isLowered()){

  //       m_elevatorRight.Still();
  //       m_elevatorLeft.Still();

  //   }
    
  // }

  private void UpdateDashboard() {

    SmartDashboard.putBoolean("Find Home", m_findHome);
    SmartDashboard.putBoolean("Elevator Lowered", atLowerLimit());
    SmartDashboard.putBoolean("Elevator Raised", atUpperLimit());
    SmartDashboard.putNumber("Elevator Left Position", m_elevatorLeft.getPosition());
    SmartDashboard.putNumber("Elevator Right Position", m_elevatorRight.getPosition());
  }

  public void periodic() {

    if (m_findHome) {
      SetZeroFinish();
    }

    if ((atLowerLimit() && m_movingUp == false) || (atUpperLimit() && m_movingDown == false)) {
      ElevatorStill();
    }

    m_elevatorRight.periodic();
    m_elevatorLeft.periodic();
    
    UpdateDashboard();

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

  public void ElevatorDown(double speed) {

    m_elevatorLeft.Down(speed);
    m_elevatorRight.Down(speed);

  }

  public void ElevatorDown() {
    m_movingDown = true;
    m_elevatorLeft.Down();
    m_elevatorRight.Down();

  }

 public void ElevatorUp(double speed) {

    m_elevatorLeft.Up(speed);
    m_elevatorRight.Up(speed);

  }

  public void ElevatorUp() {
    m_movingUp = true;
    m_elevatorLeft.Up();
    m_elevatorRight.Up();

  }

  public void ElevatorStill(){
    m_elevatorLeft.Still();
    m_elevatorRight.Still();

    m_movingDown = false;
    m_movingUp = false;

  }

  public void SetElevatorHigh(){
    if (atUpperLimit() == false) {
      m_elevatorLeft.toScoreHigh();
      m_elevatorRight.toScoreHigh();
    }
  }

  public void SetElevatorMiddle(){
    //Test PIDController Code
    //m_elevatorLeft.setTargetPosition(60);
    //m_elevatorRight.setTargetPosition(60);
    m_elevatorLeft.toScoreMiddle();
    m_elevatorRight.toScoreMiddle();
  }

  public void SetElevatorLow(){
    m_elevatorLeft.toScoreLow();
    m_elevatorRight.toScoreLow();
  }

  public void SetElevatorLowered(){
    if (atLowerLimit() == false) {
      m_elevatorLeft.toLowered();
      m_elevatorRight.toLowered();
    }
  }

}