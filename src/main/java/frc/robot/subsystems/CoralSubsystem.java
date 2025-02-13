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

        coral.set(CoralSubsystemConstants.k_intakeFactor);
    }

    public void Intakecoral(double speed) {
        speed = -(Math.abs(speed));
        coral.set(speed);
    }

    public void ReleaseCoral() {

            coral.set(CoralSubsystemConstants.k_releaseFactor);
        
    }

    public void ReleaseCoral(double speed) {

        speed = Math.abs(speed);

        coral.set(speed);

    }

    public void Still() {

        coral.set(0);

    }
}