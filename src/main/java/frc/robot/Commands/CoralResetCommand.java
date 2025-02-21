package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPositionSubsystem;

public class CoralResetCommand extends Command {

    private final CoralPositionSubsystem m_coralPosition;

    public CoralResetCommand(CoralPositionSubsystem coralPosition) {

        m_coralPosition = coralPosition;
        addRequirements(m_coralPosition);

    }

    @Override
    public void initialize() {

        m_coralPosition.positionDown();
        while (m_coralPosition.getPositionEncoder() > (-15)) {

        }

        m_coralPosition.positionStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }

}