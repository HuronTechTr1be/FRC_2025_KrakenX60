package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeSubsystem;

public class ReleaseAlgaeCommand extends Command {

    private final AlgaeSubsystem m_algae;

    public ReleaseAlgaeCommand(AlgaeSubsystem subsystem) {

        m_algae = subsystem;
        addRequirements(m_algae);

    }

    @Override
    public void initialize() {
        m_algae.ReleaseAlgae();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}