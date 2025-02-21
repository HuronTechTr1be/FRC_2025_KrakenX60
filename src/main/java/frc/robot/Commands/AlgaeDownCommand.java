package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaePositionSubsystem;

public class AlgaeDownCommand extends Command {

    private final AlgaePositionSubsystem m_algaePosition;

    public AlgaeDownCommand(AlgaePositionSubsystem algaePosition) {

        m_algaePosition = algaePosition;
        addRequirements(m_algaePosition);

    }

    // NOT USED RIGHT NOW

    @Override
    public void initialize() {

        // m_algaePivot.algaePivotDown();
        // while (m_algaePivot.getPositionEncoder() > (-15)) {

        // }

        // m_algaePivot.algaePivotStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }

}