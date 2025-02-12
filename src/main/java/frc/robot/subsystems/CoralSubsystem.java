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

        if (Math.abs(CoralSubsystemConstants.k_intakeFactor) > 1) {
            coral.set(0);
        }
         else {
            coral.set(CoralSubsystemConstants.k_intakeFactor);

        }
    }

    public void ReleaseCoral() {

        if (Math.abs(CoralSubsystemConstants.k_releaseFactor) > 1) {
            coral.set(0);
        }
         else {
            coral.set(CoralSubsystemConstants.k_releaseFactor);
        }
    }

    public void ReleaseCoral(double speed) {

        if (speed > 0) {
            speed *= -1;
        }

        if (Math.abs(speed) > 1) {
            coral.set(0);
        }
         else {
            coral.set(speed);
        }

    }

    public void Still() {

        coral.set(0);

    }
}