package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaePositionSubsystem;

public class PositionDownCommand extends Command {

    private final AlgaePositionSubsystem m_position;

    public PositionDownCommand(AlgaePositionSubsystem position) {

        m_position = position;
        addRequirements(m_position);

    }

    @Override
    public void initialize() {

        m_position.positionDown();
        while (m_position.getPositionEncoder() > (-15)) {

        }

        m_position.positionStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }

}