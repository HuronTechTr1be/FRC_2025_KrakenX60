package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPivotSubsystem;

public class PivotStillCommand extends Command {

    private final CoralPivotSubsystem m_pivot;

    public PivotStillCommand(CoralPivotSubsystem pivot) {

        m_pivot = pivot;
        addRequirements(m_pivot);

    }

    @Override
    public void initialize() {

        m_pivot.pivotStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }
}
