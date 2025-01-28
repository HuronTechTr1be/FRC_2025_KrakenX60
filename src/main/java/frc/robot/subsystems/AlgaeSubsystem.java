package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.generated.TunerConstants.AlgaeSubsystemConstants;

// motor 41
public class AlgaeSubsystem extends SubsystemBase {

    private CANSparkMax algae;

    public AlgaeSubsystem(int deviceId) {

        algae = new CANSparkMax(deviceId, MotorType.kBrushless);

    }

    public void IntakeAlgae() {

        double intakeFactor = AlgaeSubsystemConstants.k_intakeFactor;
        if (Math.abs(intakeFactor) > 1) {
            intakeFactor = 0;
        }
        if (intakeFactor == AlgaeSubsystemConstants.k_intakeFactor) {
            algae.set(AlgaeSubsystemConstants.k_intakeFactor);
        } else {
            algae.set(intakeFactor);

        }
    }

    public void ReleaseAlgae() {

        double ReleaseFactor = AlgaeSubsystemConstants.k_releaseFactor;
        if (Math.abs(ReleaseFactor) > 1) {
            ReleaseFactor = 0;
        }
        if (ReleaseFactor == AlgaeSubsystemConstants.k_releaseFactor) {
            algae.set(AlgaeSubsystemConstants.k_releaseFactor);
        } else {
            algae.set(ReleaseFactor);
        }
    }

    public void ReleaseAlgae(double speed) {

        if (speed > 0) {
            speed *= -1;
        }
        algae.set(speed);

    }

    public void Still() {

        algae.set(0);

    }
}
