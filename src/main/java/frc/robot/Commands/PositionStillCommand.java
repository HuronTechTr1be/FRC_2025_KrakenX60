package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaePivotSubsystem;

public class PositionStillCommand extends Command {

    private final AlgaePivotSubsystem m_algaePivot;

    public PositionStillCommand(AlgaePivotSubsystem algaePivot) {

        m_algaePivot = algaePivot;
        addRequirements(m_algaePivot);

    }

    @Override
    public void initialize() {

        m_algaePivot.algaePivotStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }
}
