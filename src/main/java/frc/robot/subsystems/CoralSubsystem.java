package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.CoralSubsystemConstants;

// motor 21
public class CoralSubsystem extends SubsystemBase {

    private CANSparkMax coral;

    public CoralSubsystem(int deviceId) {

        coral = new CANSparkMax(deviceId, MotorType.kBrushless);

    }

    public void IntakeCoral() {

        double intakeFactor = CoralSubsystemConstants.k_intakeFactor;
        if (Math.abs(intakeFactor) > 1) {
            intakeFactor = 0;
        }
        if (intakeFactor == CoralSubsystemConstants.k_intakeFactor) {
            coral.set(CoralSubsystemConstants.k_intakeFactor);
        } else {
            coral.set(intakeFactor);

        }
    }

    public void ReleaseCoral() {

        double ReleaseFactor = CoralSubsystemConstants.k_releaseFactor;
        if (Math.abs(ReleaseFactor) > 1) {
            ReleaseFactor = 0;
        }
        if (ReleaseFactor == CoralSubsystemConstants.k_releaseFactor) {
            coral.set(CoralSubsystemConstants.k_releaseFactor);
        } else {
            coral.set(ReleaseFactor);
        }
    }

    public void ReleaseCoral(double speed) {

        if (speed > 0) {
            speed *= -1;
        }
        coral.set(speed);

    }

    public void Still() {

        coral.set(0);

    }
}