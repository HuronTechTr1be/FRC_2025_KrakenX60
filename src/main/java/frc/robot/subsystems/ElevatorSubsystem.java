package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.ElevatorSubsystemConstants;

//NEED TO TEST VALUES AND CHANGE CONSTANTS


public class ElevatorSubsystem extends SubsystemBase {

  //Need to clarify which side is which and make clear what we're defining as left and right  
  private ElevatorBasic m_elevatorLeft = new ElevatorBasic(31, "left");
  private ElevatorBasic m_elevatorRight = new ElevatorBasic(32, "right");


  public void elevatorSetZero() {

    m_elevatorLeft.Down();
    m_elevatorRight.Down();

    //need to mess with timing here, probably won't stay at 1000
    for( int i=0; i<1000; i++){
        
    SmartDashboard.putNumber("elevatorSetZero loop value", i);
    if(m_elevatorRight.isLowered() || m_elevatorLeft.isLowered()){
        break;
    }

    }

    m_elevatorLeft.Still();
    m_elevatorRight.Still();

    m_elevatorLeft.finishZero();
    m_elevatorRight.finishZero();

  }


  public void raiseArmsPeriodic() {

    if(m_elevatorRight.isLowered() || m_elevatorLeft.isLowered()){

        m_elevatorRight.Still();
        m_elevatorLeft.Still();

    }
    
  }

  public boolean BothArmsRaised() {
    if (m_elevatorLeft.isRaised() && m_elevatorRight.isRaised()) {
      return true;
    } else {
      return false;
    }
  }

  public void periodic() {

    m_elevatorRight.periodic();
    m_elevatorLeft.periodic();

  }

  public void ElevatorDown(double speed) {

    m_elevatorLeft.Down(speed);
    m_elevatorRight.Down(speed);

  }

  public void ElevatorDown() {
    m_elevatorLeft.Down();
    m_elevatorRight.Down();

  }

 public void ElevatorUp(double speed) {

    m_elevatorLeft.Up(speed);
    m_elevatorRight.Up(speed);

  }

  public void ElevatorUp() {
    m_elevatorLeft.Up();
    m_elevatorRight.Up();

  }

  public void ElevatorStill(){
    m_elevatorLeft.Still();
    m_elevatorRight.Still();

  }


}