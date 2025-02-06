package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.AlgaePivotSubsystemConstants;

// motor 42
public class AlgaePivotSubsystem extends SubsystemBase {

    private CANSparkMax algaePivot;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private String direction;

    public AlgaePivotSubsystem(int deviceId) {

        algaePivot = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = algaePivot.getEncoder();
        m_limitSwitch = algaePivot.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

    }

    public void goingDown(){
        direction = "Down";
    }

    public void goingUp(){
        direction = "Up";
    }

    public String getDirection(){
        return direction;
    }

    public double getPositionEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void algaePivotEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void algaePivotSetZero() {
        // find way without using while loops!
    }

    public void algaePivotUp() {

        if (isRaised()) {
            algaePivot.set(0);
            m_relativeEncoder.setPosition(0);
        } else {
            algaePivot.set((AlgaePivotSubsystemConstants.k_speedUpFactor));
        }

    }

    public void algaePivotDown() {

        if (isLowered()) {
            algaePivot.set(0);
        } else {
            algaePivot.set(AlgaePivotSubsystemConstants.k_speedDownFactor);
        }

    }

    public void algaePivotUpInit() {

        algaePivot.set(AlgaePivotSubsystemConstants.k_speedUpFactor);

    }

    public void algaePivotStill() {

        algaePivot.set(0);
        if (isRaised()) {
            m_relativeEncoder.setPosition(0);
        }

    }

    public boolean isRaised() {

        return m_limitSwitch.isPressed();
    
      }

      public boolean isLowered() {

        return ((AlgaePivotSubsystemConstants.k_pointLowered - m_relativeEncoder.getPosition()) >= 2);
    
      }

      public void periodic(){
        if (isRaised()){
            algaePivotStill();
            algaePivotEncoderZero();
        }
        else if (isLowered()){
            algaePivotStill();
        }

        if(getDirection()=="Down"){

        }
        else if (getDirection()=="Up"){
            if (!isRaised()){
            algaePivotUp();
            }
        }
      }

}