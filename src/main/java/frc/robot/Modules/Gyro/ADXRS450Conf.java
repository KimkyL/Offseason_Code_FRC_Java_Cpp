package frc.robot.Modules.Gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


// Using the ADXRS450 gyro for the odometry and Z-Yaw for the Pathplanning
public class ADXRS450Conf extends SubsystemBase {


    private final ADXRS450_Gyro ADXRS450;



    public ADXRS450Conf(){

        ADXRS450 = new ADXRS450_Gyro();

        ADXRS450.calibrate();
        

    }

    public double getangle(){

        return ADXRS450.getAngle();

    }

    public Rotation2d getyaw(){

        return ADXRS450.getRotation2d();

    }


    public void resetgyro(){

        ADXRS450.reset();

    }

    @Override
    public void periodic(){

        SmartDashboard.putBoolean("Is Gyro {$ADXRS450} Connected?", ADXRS450.isConnected());
        SmartDashboard.putNumber("Ratio of the Gyro", ADXRS450.getRate());
        

        
    }
    

  
}




