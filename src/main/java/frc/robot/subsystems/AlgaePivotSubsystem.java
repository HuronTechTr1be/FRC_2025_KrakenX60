package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.AlgaePositionSubsystemConstants;

// motor 42
public class AlgaePivotSubsystem extends SubsystemBase {

    private CANSparkMax algaePivot;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private double m_pointLowered = -20; // dont know this value yet

    public AlgaePivotSubsystem(int deviceId) {

        algaePivot = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = algaePivot.getEncoder();
        m_limitSwitch = algaePivot.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

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
            algaePivot.set((AlgaePositionSubsystemConstants.k_speedUpFactor));
        }

    }

    public void algaePivotDown() {

        if (isLowered()) {
            algaePivot.set(0);
        } else {
            algaePivot.set(AlgaePositionSubsystemConstants.k_speedDownFactor);
        }

    }

    public void algaePivotUpInit() {

        algaePivot.set(AlgaePositionSubsystemConstants.k_speedUpFactor);

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

        return ((m_pointLowered - m_relativeEncoder.getPosition()) >= 2);
    
      }

}