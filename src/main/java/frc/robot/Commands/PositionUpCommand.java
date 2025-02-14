package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaePivotSubsystem;

public class PositionUpCommand extends Command {

    private final AlgaePivotSubsystem m_algaePivot;

    public PositionUpCommand(AlgaePivotSubsystem algaePivot) {

        m_algaePivot = algaePivot;
        addRequirements(m_algaePivot);

    }

    @Override
    public void initialize() {

        m_algaePivot.algaePivotUp();
        while (m_algaePivot.isRaised() == false) {

        }

        m_algaePivot.algaePivotEncoderZero();
        m_algaePivot.algaePivotStill();

    }

    @Override
    public boolean isFinished() {

        return true;

    }

}
