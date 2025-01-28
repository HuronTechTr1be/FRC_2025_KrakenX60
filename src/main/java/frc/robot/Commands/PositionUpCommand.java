package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaePositionSubsystem;

public class PositionUpCommand extends Command {

    private final AlgaePositionSubsystem m_position;

    public PositionUpCommand(AlgaePositionSubsystem position) {

        m_position = position;
        addRequirements(m_position);

    }

    @Override
    public void initialize() {

        m_position.positionUp();
        while (m_position.isRaised() == false) {

        }

        m_position.positionEncoderZero();
        m_position.positionStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }

}
