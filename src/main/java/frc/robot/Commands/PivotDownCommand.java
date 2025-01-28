package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PivotSubsystem;

public class PivotDownCommand extends Command {

    private final PivotSubsystem m_pivot;

    public PivotDownCommand(PivotSubsystem pivot) {

        m_pivot = pivot;
        addRequirements(m_pivot);

    }

    @Override
    public void initialize() {

        m_pivot.pivotDown();
        while (m_pivot.getPivotEncoder() > (-15)) {

        }

        m_pivot.pivotStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }

}