package frc.robot.Container;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Core.Constants;
import frc.robot.Modules.Drive.Commands.Drive_Command;
import frc.robot.Modules.Drive.Subsystems.Drive_Subystem;
import frc.robot.Modules.Gyro.ADXRS450Conf;

public class RobotContainer {

    //Create a Constructor of the controllers

    private final XboxController Driver_Controller = 
    
    new XboxController(Constants.OperatorConstants.kDriverControllerPort);


    public final Drive_Subystem F_Drive = new Drive_Subystem();
    public final ADXRS450Conf F_Gyro = new ADXRS450Conf();

    public RobotContainer(){
        XboxController_Config();
    
    }

    public void XboxController_Config(){
        //If you can change that of the controller 
          F_Drive.setDefaultCommand(new Drive_Command(
            () -> Driver_Controller.getLeftY(),
            () -> Driver_Controller.getLeftX(),
            F_Drive, F_Gyro));


    }

    public Command getAutonomousCommand(){
        return null;
    }

    
}
