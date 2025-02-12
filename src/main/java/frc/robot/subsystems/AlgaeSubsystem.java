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

        if (Math.abs(AlgaeSubsystemConstants.k_intakeFactor) > 1) {
            algae.set(0);
        }
         else {
            algae.set(AlgaeSubsystemConstants.k_intakeFactor);
        }
    }

    public void ReleaseAlgae() {

        if (Math.abs(AlgaeSubsystemConstants.k_releaseFactor) > 1) {
            algae.set(0);
        }
         else {
            algae.set(AlgaeSubsystemConstants.k_releaseFactor);
        }
    }

    public void ReleaseAlgae(double speed) {

        if (speed > 0) {
            speed *= -1;
        }

        if (Math.abs(speed) > 1) {
            algae.set(0);
        }
         else {
            algae.set(speed);
        }

    }

    public void Still() {

        algae.set(0);

    }
}
