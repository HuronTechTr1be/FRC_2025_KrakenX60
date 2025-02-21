package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.CoralPositiionSubsystemConstants;

// motor 22
public class CoralPositionSubsystem extends SubsystemBase {

    private CANSparkMax position;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private boolean m_findHome = false;
    private boolean m_findLow = false;
    private boolean goingUp;
    private boolean goingDown;

    // private ShuffleboardTab coralTab = Shuffleboard.getTab("Coral");

    public CoralPositionSubsystem(int deviceId) {

        position = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = position.getEncoder();
        m_limitSwitch = position.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

        // coralTab.addNumber("Coral Position", () -> getPivotEncoder());
        // coralTab.addBoolean("Coral Raised", () -> isRaised());
        // coralTab.addBoolean("Coral Lowered", () -> isLowered());

        SetZeroInit();

    }

    public void SetZeroInit() {

        m_findHome = true;

        if (isRaised() == false) {
            positionUp();

        } else {
            m_findHome = false;
        }
    }

    private void SetZeroFinish() {

        positionUp();

        if (isRaised()) {
            positionStill();
            positionEncoderZero();

            m_findHome = false;
            SetLowInit();

        }
    }

    public void SetLowInit() {

        m_findLow = true;

        if (isLowered() == false) {
            positionDown();

        } else {
            m_findLow = false;
        }
    }

    private void SetLowFinish() {

        positionDown();

        if (isLowered()) {
            positionStill();

            m_findLow = false;
        }
    }

    public double getPositionEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void positionEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void positionUp() {

        if (isRaised()) {
            positionStill();
            m_relativeEncoder.setPosition(0);
        } else {
            position.set((CoralPositiionSubsystemConstants.k_speedUpFactor));
            goingUp = true;
            goingDown = false;
        }

    }

    public void positionUp(double speed) {
        speed = -(Math.abs(speed));

        if (isRaised()) {
            positionStill();
            m_relativeEncoder.setPosition(0);
        } else {
            position.set((speed));
            goingUp = true;
            goingDown = false;
        }

    }

    public void positionDown() {

        goingDown = true;

        if (isLowered()) {
            positionStill();
        } else {
            position.set(CoralPositiionSubsystemConstants.k_speedDownFactor);
            goingUp = false;
            goingDown = true;
        }

    }

    public void positionDown(double speed) {
        speed = Math.abs(speed);

        if (isLowered()) {
            positionStill();
        } else {
            position.set(speed);
            goingUp = false;
            goingDown = true;
        }

    }

    public void positionUpInit() {

        position.set(CoralPositiionSubsystemConstants.k_speedUpFactor);

    }

    public void positionStill() {

        position.set(0);
        goingUp = false;
        goingDown = false;

        if (isRaised()) {
            m_relativeEncoder.setPosition(0);
        }

    }

    public boolean isRaised() {

        return m_limitSwitch.isPressed();

    }

    public boolean isLowered() {

        return (Math.abs(CoralPositiionSubsystemConstants.k_pointLowered - getPositionEncoder()) <= 2);

    }

    private void UpdateDashboard() {

        SmartDashboard.putNumber("Coral Position", getPositionEncoder());
        SmartDashboard.putBoolean("Coral Raised", isRaised());
        SmartDashboard.putBoolean("Coral Lowered", isLowered());
        SmartDashboard.putBoolean("Coral Find Home", m_findHome);
        SmartDashboard.putBoolean("Coral Find Low", m_findLow);
    }

    public void periodic() {

        if (m_findHome) {
            SetZeroFinish();
        }

        if (m_findLow) {
            SetLowFinish();
        }

        UpdateDashboard();

        if (isRaised()) {
            if (goingUp) {
                positionStill();
            }
        }
        if (isLowered()) {
            if (goingDown) {
                positionStill();
            }
        }
    }

}