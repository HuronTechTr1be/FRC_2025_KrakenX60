package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.AlgaePivotSubsystem;
import frc.robot.generated.TunerConstants.AlgaePivotSubsystemConstants;
import frc.robot.generated.TunerConstants.AlgaeSubsystemConstants;


public class GrabAlgaeCommand extends Command {

    private final AlgaeSubsystem m_algae;
    private final AlgaePivotSubsystem m_algaePivot;

    public GrabAlgaeCommand(AlgaeSubsystem subsystem, AlgaePivotSubsystem subsystem2) {

        m_algae = subsystem;
        m_algaePivot = subsystem2;
        addRequirements(m_algae, m_algaePivot);

    }

    @Override
    public void initialize() {
        
        m_algae.IntakeAlgae();
        m_algaePivot.goingDown();

        if(m_algaePivot.isLowered()){
            m_algaePivot.algaePivotStill();
        }
        else if(m_algaePivot.getPositionEncoder()>AlgaePivotSubsystemConstants.k_pointLowered){
            m_algaePivot.algaePivotDown();
        }
            
    }

    @Override
    public boolean isFinished() {
        m_algaePivot.goingUp();
        return true;
    }

}