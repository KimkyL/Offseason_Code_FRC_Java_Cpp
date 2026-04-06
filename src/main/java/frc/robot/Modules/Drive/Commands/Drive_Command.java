package frc.robot.Modules.Drive.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Modules.Drive.Constants.Drive_Constants;
import frc.robot.Modules.Drive.Subsystems.Drive_Subystem;
import frc.robot.Modules.Gyro.ADXRS450Conf;

public class Drive_Command extends Command {

    private final DoubleSupplier D_xSpeed;
    private final DoubleSupplier D_zRotation;
    private final Drive_Subystem D_Drive;
    private final ADXRS450Conf G_Gyro;

   public Drive_Command(DoubleSupplier xSpeed, DoubleSupplier zRotation, Drive_Subystem Drive, ADXRS450Conf Gyro){

        this.D_xSpeed = xSpeed;
        this.D_zRotation = zRotation;
        this.D_Drive = Drive;
        this.G_Gyro = Gyro;

        addRequirements(this.D_Drive);


   }

   @Override
   public void initialize(){

   }

    @Override
    public void execute() {
      D_Drive.Drive(D_xSpeed.getAsDouble(), D_zRotation.getAsDouble(), Drive_Constants.KpError, G_Gyro);
    }
    
    
    @Override
    public void end(boolean isInterrupted){
        
    }

    //Finish if the timer is 0 or teleop is changed to Test or Autoinit.
    @Override
    public boolean isFinished(){

        return false;
    }

}
