package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaePositionSubsystem;

public class AlgaePositionStillCommand extends Command {

    private final AlgaePositionSubsystem m_algaePosition;

    public AlgaePositionStillCommand(AlgaePositionSubsystem algaePosition) {

        m_algaePosition = algaePosition;
        addRequirements(m_algaePosition);

    }

    @Override
    public void initialize() {

        m_algaePosition.algaePositionStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }
}
