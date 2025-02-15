package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.PivotSubsystemConstants;

// motor 22
public class CoralPivotSubsystem extends SubsystemBase {

    private CANSparkMax pivot;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private boolean m_findHome = false;
    private boolean m_findLow = false;
    private boolean goingUp;
    private boolean goingDown;

    // private ShuffleboardTab coralTab = Shuffleboard.getTab("Coral");

    public CoralPivotSubsystem(int deviceId) {

        pivot = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = pivot.getEncoder();
        m_limitSwitch = pivot.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

        // coralTab.addNumber("Coral Position", () -> getPivotEncoder());
        // coralTab.addBoolean("Coral Raised", () -> isRaised());
        // coralTab.addBoolean("Coral Lowered", () -> isLowered());

        SetZeroInit();

    }

    public void SetZeroInit() {

        m_findHome = true;

        if (isRaised() == false) {
            pivotUp();
            
        } else {
            m_findHome = false;
        }
    }

    private void SetZeroFinish() {

        pivotUp();

        if (isRaised()) {
            pivotStill();
            pivotEncoderZero();

            m_findHome = false;
            SetLowInit();

        }
    }

    public void SetLowInit() {

        m_findLow = true;

        if (isLowered() == false) {
            pivotDown();
            
        } else {
            m_findLow = false;
        }
    }

    private void SetLowFinish() {

        pivotDown();

        if (isLowered()) {
            pivotStill();

            m_findLow = false;
        }
    }

    public double getPivotEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void pivotEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void pivotUp() {

        if (isRaised()) {
            pivotStill();
            m_relativeEncoder.setPosition(0);
        } else {
            pivot.set((PivotSubsystemConstants.k_speedUpFactor));
            goingUp = true;
            goingDown = false;
        }

    }

    public void pivotUp(double speed) {
        speed = -(Math.abs(speed));

        if (isRaised()) {
            pivotStill();
            m_relativeEncoder.setPosition(0);
        } else {
            pivot.set((speed));
            goingUp = true;
            goingDown = false;
        }

    }

    public void pivotDown() {

        goingDown = true;

        if (isLowered()) {
            pivotStill();
        } else {
            pivot.set(PivotSubsystemConstants.k_speedDownFactor);
            goingUp = false;
            goingDown = true;
        }

    }

    public void pivotDown(double speed) {
        speed = Math.abs(speed);

        if (isLowered()) {
            pivotStill();
        } else {
            pivot.set(speed);
            goingUp = false;
            goingDown = true;
        }

    }

    public void pivotUpInit() {

        pivot.set(PivotSubsystemConstants.k_speedUpFactor);

    }

    public void pivotStill() {

        pivot.set(0);
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

        return (Math.abs(PivotSubsystemConstants.k_pointLowered - getPivotEncoder()) <= 2);

    }

    private void UpdateDashboard() {

        SmartDashboard.putNumber("Coral Position", getPivotEncoder());
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
                pivotStill();
            }
        }
        if (isLowered()) {
            if (goingDown) {
                pivotStill();
            }
        }
    }

}