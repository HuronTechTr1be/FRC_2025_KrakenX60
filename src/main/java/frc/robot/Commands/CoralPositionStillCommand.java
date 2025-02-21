package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPositionSubsystem;

public class CoralPositionStillCommand extends Command {

    private final CoralPositionSubsystem m_coralPosition;

    public CoralPositionStillCommand(CoralPositionSubsystem coralPosition) {

        m_coralPosition = coralPosition;
        addRequirements(m_coralPosition);

    }

    @Override
    public void initialize() {

        m_coralPosition.positionStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }
}
