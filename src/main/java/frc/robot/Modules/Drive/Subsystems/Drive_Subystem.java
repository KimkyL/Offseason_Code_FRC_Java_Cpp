package frc.robot.Modules.Drive.Subsystems;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.ResetMode;   // ← nuevo paquete común
import com.revrobotics.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Modules.Drive.Constants.*;
import frc.robot.Modules.Gyro.ADXRS450Conf;



public class Drive_Subystem extends SubsystemBase {

    //Initialize The motors on the constructor
    private final SparkMax Leader_1;
    private final SparkMax Leader_2;
    private final SparkMax Follower_1;
    private final SparkMax Follower_2;
    //Set the leaders for DiferentialDrive - for the leaders
    private final DifferentialDrive Drive_Leader;
  
    


    public Drive_Subystem(){


          
           Leader_1  = new SparkMax(Drive_Constants.Leader_1, MotorType.kBrushed);
           Leader_2  = new SparkMax(Drive_Constants.Leader_2, MotorType.kBrushed);
           Follower_1 = new SparkMax(Drive_Constants.Follower_1, MotorType.kBrushed);
           Follower_2 = new SparkMax(Drive_Constants.Follower_2, MotorType.kBrushed);


           Drive_Leader = new DifferentialDrive(Leader_1, Leader_2);

           SparkMaxConfig Spark_Config = new SparkMaxConfig();

            //Set the CAN bus timeout before giving up and sending an alert that the CAN bus was not found
            Leader_1.setCANTimeout(250);
            Leader_2.setCANTimeout(250);
            Follower_1.setCANTimeout(250);
            Follower_2.setCANTimeout(250);


            //Set the voltage and the regulator for burnouts.
            Spark_Config.voltageCompensation(12);

            Spark_Config.smartCurrentLimit(60);

            // Setting the leaders and configure followers.
            Spark_Config.follow(Leader_1);
           
            Follower_1.configure(Spark_Config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters); 

            Spark_Config.follow(Leader_2);

            Follower_2.configure(Spark_Config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
            //Elminate older parameters and no save the config on the followers
            Spark_Config.disableFollowerMode();
            //Disable follwer mode for the leader 1 

            Leader_1.configure(Spark_Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

            Spark_Config.inverted(true);
         
            Leader_2.configure(Spark_Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

            //Elminate older parameters but save de config con the leaders
        
}
     /**
     * 
     * 
     * 
     * @param xSpeed The lineal Velocity of the Joystick
     * @param zRotation The degrees of the joytick
     * @param KpError Errors of the drift caused by the movement
     * 
     * Formulas for the calculations of the distance and rotations
     * 
     *  a+b(PV)+c*t+d(PV*t)+e(PV^2)*t for distance, off by 4 cm
     * θ = (217.15 * t + -16.78) * (PV / 8) for rotation, off by 5.4%
     * 
     * @see -GP i love you!
     */
    public void Drive(double xSpeed, double zRotation, double KpError, ADXRS450Conf Gyro){

       
        //if rotation is minor of 5 degrees
        if(Math.abs(zRotation) < 0.05){
           double correction = KpError * Gyro.getangle();
           Drive_Leader.arcadeDrive(xSpeed, -correction);
        } else {
             Gyro.resetgyro();
             Drive_Leader.arcadeDrive(xSpeed, zRotation);

        }

    

    


        





    }   

    


    //Telemetry of the CAN and the velocity set this on 20ms

@Override
public void periodic(){

    SmartDashboard.putNumber("Speed Leader 1", Leader_1.get());
    SmartDashboard.putNumber("Speed Leader 2", Leader_2.get());

    SmartDashboard.putNumber("Temp Leader 1", Leader_1.getMotorTemperature());
    SmartDashboard.putNumber("Temp Leader 2", Leader_2.getMotorTemperature());

    SmartDashboard.putNumber("Current Leader 1", Leader_1.getOutputCurrent());
    SmartDashboard.putNumber("Current Leader 2", Leader_2.getOutputCurrent());

    SmartDashboard.putNumber("Voltage Leader 1", Leader_1.getBusVoltage());
    SmartDashboard.putNumber("Voltage Leader 2", Leader_2.getBusVoltage());

    SmartDashboard.putNumber("Faults Leader 1", Leader_1.getFaults().rawBits);
    SmartDashboard.putNumber("Faults Leader 2", Leader_2.getFaults().rawBits);

}}
