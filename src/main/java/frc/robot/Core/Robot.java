/* 

  "It's something you give your heart to and love unconditionally, but it keeps letting you down"

  Off-Season Code by 0xF1nky_Dev::Stable();

  Letsgo to code!

 */

package frc.robot.Core;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Container.RobotContainer;


public class Robot extends TimedRobot {

  private RobotContainer F_Container;

  private Command Auto;


  @Override
  public void robotInit(){

    F_Container = new RobotContainer();

  
    
    DriverStation.silenceJoystickConnectionWarning(true);


  }

   
  @Override
  public void robotPeriodic() {
    
    CommandScheduler.getInstance().run();
    
  }

  @Override
  public void disabledInit() {
   
   
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() { 
    Auto = F_Container.getAutonomousCommand();

     // schedule the autonomous command (example)
     if (Auto != null) {
       Auto.schedule();}}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    
    CommandScheduler.getInstance().cancelAll();

    
  
  }
  

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {}


  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}

  
 

}

  
