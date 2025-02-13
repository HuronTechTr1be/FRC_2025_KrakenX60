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

        algae.set(AlgaeSubsystemConstants.k_intakeFactor);
        
    }

    public void IntakeAlgae(double speed) {
        speed = -(Math.abs(speed));
        algae.set(speed);
    }

    public void ReleaseAlgae() {

        algae.set(AlgaeSubsystemConstants.k_releaseFactor);

    }

    public void ReleaseAlgae(double speed) {

        speed = Math.abs(speed);
        algae.set(speed);

    }

    public void Still() {

        algae.set(0);

    }
}
