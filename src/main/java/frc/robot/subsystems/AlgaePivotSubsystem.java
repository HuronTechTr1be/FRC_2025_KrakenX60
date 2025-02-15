package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.AlgaePivotSubsystemConstants;

// motor 62
public class AlgaePivotSubsystem extends SubsystemBase {

    private CANSparkMax algaePivot;
    private RelativeEncoder m_relativeEncoder;
    private SparkLimitSwitch m_limitSwitch;
    private boolean m_findHome = false;
    private boolean goingUp;
    private boolean goingDown;

    public AlgaePivotSubsystem(int deviceId) {

        algaePivot = new CANSparkMax(deviceId, MotorType.kBrushless);
        m_relativeEncoder = algaePivot.getEncoder();
        m_limitSwitch = algaePivot.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

        SetZeroInit();
    }

    public void SetZeroInit() {

        m_findHome = true;

        if (isRaised() == true) {
            algaePivotDown();

        } else {
            m_findHome = false;
        }
    }

    private void SetZeroFinish() {

        algaePivotDown();

        if (isRaised() == false) {
            algaePivotStill();
            algaePivotEncoderZero();

            m_findHome = false;

        }
    }

    public double getPositionEncoder() {

        return m_relativeEncoder.getPosition();

    }

    public void algaePivotEncoderZero() {

        m_relativeEncoder.setPosition(0);

    }

    public void algaePivotUp() {

        goingUp = true;

        if (isRaised()) {
            algaePivotStill();
        } else {
            algaePivot.set(AlgaePivotSubsystemConstants.k_speedUpFactor);
            goingDown = false;
            goingUp = true;
        }

    }

    public void algaePivotUp(double speed) {
        // speed = (Math.abs(speed));
        // if (onSwitch()) {
        // if (goingDown == false){
        // algaePivot.set(0);
        // m_relativeEncoder.setPosition(0);
        // goingUp = true;
        // }
        // else{
        // algaePivot.set(speed);
        // }
        // } else {
        // algaePivot.set((speed));
        // }

    }

    public void algaePivotDown() {

        goingDown = true;

        if (isLowered()) {
            algaePivotStill();
        } else {
            algaePivot.set(AlgaePivotSubsystemConstants.k_speedDownFactor);
            goingDown = true;
            goingUp = false;
        }

    }

    public void algaePivotDown(double speed) {
        // speed = -(Math.abs(speed));
        // if (onSwitch()) {
        // if (goingUp == false){
        // algaePivot.set(0);
        // goingDown = true;
        // }
        // else{
        // algaePivot.set(speed);
        // }
        // } else {
        // algaePivot.set(speed);
        // }
    }

    // public void algaePivotUpInit() {

    // algaePivot.set(AlgaePivotSubsystemConstants.k_speedUpFactor);

    // }

    public void algaePivotStill() {

        algaePivot.set(0);
        goingUp = false;
        goingDown = false;

    }

    public boolean onSwitch() {

        return m_limitSwitch.isPressed();

    }

    public boolean isRaised() {

        return m_limitSwitch.isPressed();

    }

    public boolean isLowered() {

        return ((AlgaePivotSubsystemConstants.k_pointLowered - getPositionEncoder()) <= 2);

    }

    private void UpdateDashboard() {

        SmartDashboard.putNumber("Algae Position", getPositionEncoder());
        SmartDashboard.putBoolean("Algae Raised", isRaised());
        SmartDashboard.putBoolean("Algae Lowered", isLowered());
        SmartDashboard.putBoolean("Algae GoingUp", goingUp);
        SmartDashboard.putBoolean("Algae GoingDown", goingDown);
    }

    public void periodic() {

        if (m_findHome) {
            SetZeroFinish();
        }

        UpdateDashboard();

        if (isRaised()) {
            if (goingUp) {
                algaePivotStill();
            }
        }
        if (isLowered()) {
            if (goingDown) {
                algaePivotStill();
            }
        }

    }

}