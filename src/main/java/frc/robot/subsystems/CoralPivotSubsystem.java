package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.PivotSubsystemConstants;

// motor 22
public class CoralPivotSubsystem extends SubsystemBase {

    private CANSparkMax pivot;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private double m_pointLowered = -20; // dont know this value yet

    public CoralPivotSubsystem(int deviceId) {

        pivot = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = pivot.getEncoder();
        m_limitSwitch = pivot.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

    }

    public double getPivotEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void pivotEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void pivotSetZero() {
        // find way without using while loops!
    }

    public void pivotUp() {

        if (isRaised()) {
            pivot.set(0);
            m_relativeEncoder.setPosition(0);
        } else {
            pivot.set((PivotSubsystemConstants.k_speedUpFactor));
        }

    }

    public void pivotDown() {

        if (isLowered()) {
            pivot.set(0);
        } else {
            pivot.set(PivotSubsystemConstants.k_speedDownFactor);
        }

    }

    public void pivotUpInit() {

        pivot.set(PivotSubsystemConstants.k_speedUpFactor);

    }

    public void pivotStill() {

        pivot.set(0);
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