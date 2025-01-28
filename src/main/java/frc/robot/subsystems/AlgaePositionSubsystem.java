package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.AlgaePositionSubsystemConstants;

// motor 42
public class AlgaePositionSubsystem extends SubsystemBase {

    private CANSparkMax position;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private double m_pointLowered = -20; // dont know this value yet

    public AlgaePositionSubsystem(int deviceId) {

        position = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = position.getEncoder();
        m_limitSwitch = position.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

    }

    public double getPositionEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void positionEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void positionSetZero() {
        // find way without using while loops!
    }

    public void positionUp() {

        if (isRaised()) {
            position.set(0);
            m_relativeEncoder.setPosition(0);
        } else {
            position.set((AlgaePositionSubsystemConstants.k_speedUpFactor));
        }

    }

    public void positionDown() {

        if (isLowered()) {
            position.set(0);
        } else {
            position.set(AlgaePositionSubsystemConstants.k_speedDownFactor);
        }

    }

    public void positionUpInit() {

        position.set(AlgaePositionSubsystemConstants.k_speedUpFactor);

    }

    public void positionStill() {

        position.set(0);
        if (isRaised()) {
            m_relativeEncoder.setPosition(0);
        }

    }

    public boolean isRaised() {

        return m_limitSwitch.isPressed();
    
      }

      public boolean isLowered() {

        return ((m_pointLowered - m_relativeEncoder.getPosition()) >= 2);
    
      }

}