package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.ClimbSubsystemConstants;

// motor 51
public class ClimbSubsystem extends SubsystemBase {

    private CANSparkMax climb;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private double m_pointLowered = -20; // dont know this value yet

    public ClimbSubsystem(int deviceId) {

        climb = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = climb.getEncoder();
        m_limitSwitch = climb.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

    }

    public double getClimbEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void climbEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void climbSetZero() {
        // find way without using while loops!
    }

    public void climbUp() {

        if (isRaised()) {
            climb.set(0);
            m_relativeEncoder.setPosition(0);
        } else {
            climb.set((ClimbSubsystemConstants.k_speedUpFactor));
        }

    }

    public void climbDown() {

        if (isLowered()) {
            climb.set(0);
        } else {
            climb.set(ClimbSubsystemConstants.k_speedDownFactor);
        }

    }

    public void climbUpInit() {

        climb.set(ClimbSubsystemConstants.k_speedUpFactor);

    }

    public void climbStill() {

        climb.set(0);
        if (isRaised()) {
            m_relativeEncoder.setPosition(0);
        }

    }

    public boolean isRaised() {

        return m_limitSwitch.isPressed();

    }

    public boolean isLowered() {

        return (Math.abs(m_pointLowered - m_relativeEncoder.getPosition()) <= 2);

    }

}
