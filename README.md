# Offseason_Code_FRC_Java_Cpp
The off-season code, created by 0xF1nky_Dev, is intended for people who want to test their robot during the off-season and use it to make their own modifications.

# Robot Documentation — Off-Season!

## General Information
- **Robot Name:** Rebellako
- **Game:** Rebulit
- **Last Update:** May 2026

## Table of Contents
1. [General Information](#general-information)
2. [Code Architecture](#code-architecture)
3. [Subsystems & Commands](#subsystems--commands)
4. [Robot Components](#robot-components)
5. [Controls](#controls)
6. [Config](#config)
7. [Troubleshooting](#troubleshooting)

## General Description
Our robot features: an Intake, an Elevator, and a Pneumatic system. It is designed for defense and collection of Algaes during the game.

## Code Architecture

### Folder Structure
```
src/
└── main/
    └── java/
        └── frc/
            └── robot/
                ├── Auto/
                │   └── NotImportant2
                ├── Config/
                │   └── ControlConstants/
                │       └── JoystickCons.java
                ├── Core/
                │   ├── Controls.java
                │   ├── Main.java
                │   └── Robot.java
                ├── Modules/
                │   ├── Elevator/
                │   │   ├── ElevatorConstants/
                │   │   │   └── ElevatorCons.java
                │   │   ├── ElevatorDownCom.java
                │   │   ├── ElevatorUpCom.java
                │   │   └── ElevatorSub.java
                │   ├── Intake/
                │   │   ├── IntakeConstants/
                │   │   │   └── IntakeConstants.java
                │   │   ├── IntakeGrabCom.java
                │   │   ├── IntakeReleaseCom.java
                │   │   └── IntakeSub.java
                │   ├── Movement/
                │   │   ├── MovementConstants/
                │   │   │   └── MovementCons.java
                │   │   ├── MovementCom.java
                │   │   └── MovementSubsystem.java
                │   └── Pneumatic/
                │       ├── Compressor/
                │       │   ├── TurnOffCompressorCom.java
                │       │   └── TurnOnCompressorCom.java
                │       ├── Solenoid/
                │       │   ├── SolenoidExtendCom.java
                │       │   ├── SolenoidInvertCom.java
                │       │   ├── SolenoidRetractCom.java
                │       └── SolenoidConstants/
                │       │  └── SolenoidCons.java
                │       └── SolenoidSub.java
```

---

## Subsystems & Commands

### Intake (Collection System)
**File:** `Subsystems/IntakeSub.java`

**Description:** System responsible for picking up and manipulating the game's Algaes.

#### Motors and Sensors
- **Main Motor/Sensor:** SparkMax (CAN ID: 6)
- **Encoder:** No encoder.

#### Main Methods

##### `IntakeGrabCom()`
**Function:** Activates the mechanism to pick up objects.

**Behavior:**
- Activates the intake motor in the positive direction
- Configured speed: 0.5 (50% power)
- Stops automatically when the upper limit sensor is reached

**Usage example:**
```java
public void execute(){
    Intake_m.IntakeOn(Constants.IntakeCons.Grab);
}
```

##### `IntakeReleaseCom()`
**Function:** Releases the mechanism to drop the object.

**Behavior:**
- Activates the intake motor in the negative direction
- Configured speed: -0.5 (50% power in reverse)

##### `IntakeOn()`
**Function:** Completely stops the intake movement.

**Typical use:** Emergency command or when finishing operations.

#### Intake States
- **Grab:** Ready to pick up elements
- **Release:** Ready for transport or release
- **Stop:** Motor stopped

#### Configuration
```java
// Constants.java
public static final class IntakeCons {
    public static final int IntakeMotor = 6;   // CAN ID of the Intake Motor (Claw)
    public static final Double Release = -0.5; // Speed at which it releases
    public static final Double Grab = 0.5;     // Speed at which it grabs
}
```

---

### Elevator
**File:** `Subsystems/ElevatorSub.java`

**Description:** Controls the elevator system, together with the Intake system.

#### Motors and Sensors
- **Main Motor/Sensor:** SparkMax, REV NEO Brushless Motor V1.1 (CAN ID: 6)
- **Encoder:** Included with the REV Robotics NEO.

#### Main Methods

##### `ElevatorDownCom()`
**Function:** Lowers the mechanism along with the Intake to adjust height.

**Behavior:**
- Lowers the elevator so it can be handed off to the Human Player.
- Configured speed: -0.5 (50% power in reverse)
- Lowers automatically when the counter reaches zero.

**Usage example:**
```java
public void execute() {
    m_elevator.ElevatorOn(ElevatorConstants.Lower);
}
```

##### `ElevatorUpCom()`
**Function:** Raises the mechanism along with the intake to return it to its original height.

**Behavior:**
- Raises the elevator to throw Algaes into the coral rack.
- Configured speed: 0.5 (50% power)
- Lowers automatically when the counter reaches zero.

**Usage example:**
```java
public void execute() {
    m_elevator.ElevatorOn(ElevatorConstants.Raise);
}
```

#### Configuration
```java
// Constants.java
public static final class ElevatorConstants {
    public static final int ElevatorID = 5; // CAN ID of the elevator motor

    /** Voltage limit and battery compensation to guarantee 100% usage */
    public static final int Elevator_Amp_Limit = 60;        // Elevator amp limit — prevents overloads and avoids irreparable damage
    public static final double Elevator_Voltage_Limit = 10; // If battery drops to 10V, the motor will keep running at 10V until its last breath

    public static final double Raise = -0.5; // Speed at which it raises
    public static final double Lower = 0.5;  // Speed at which it lowers

    public static final double StayDown = 0; // Speed to hold down position (default 0)
    public static final double StayUp = 0;   // Speed to hold up position
}
```

---

### Solenoid (Pneumatic System)
**File:** `Subsystems/SolenoidSub.java`

**Description:** Controls the robot's pneumatic system, including the compressor.

#### Components
- **Compressor:** PCM (Pneumatic Control Module)
- **Solenoid:** Single solenoid for up/down only.
- **Pressure Sensor:** Included in the tubing system.

#### Main Methods

##### `TurnOnCompressor()`
**Function:** Activates the compressor to generate pressure in the pneumatic system.

**Behavior:**
- Turns on the compressor automatically
- Maintains pressure at 120 PSI
- Turns off automatically when it reaches max pressure or is manually disabled

**Usage example:**
```java
public void CompressorOn() {
    Compressor.enableDigital();
}
```

##### `TurnOffCompressor()`
**Function:** Manually deactivates the compressor.

**Behavior:**
- Turns off the compressor by calling its function.

**Typical use:**
- Maintenance / Energy saving
- Emergency situations

**Usage example:**
```java
public void CompressorOff() {
    Compressor.disable();
}
```

##### `Extend()`
**Function:** Extends the pneumatic piston.

**States:** Extend / Retract / Invert

##### `Retract()`
**Function:** Retracts the pneumatic piston.

##### `Invert()`
**Function:** Inverts the piston between Retract/Invert states.

#### Configuration
```java
// Constants.java
public static final class SolenoidConf {
    public static final int CompressorID = 0; // CAN ID of the Compressor (Cannot be changed — may cause HAL Error)
    public static final int SolenoidID = 4;   // Solenoid 'CAN ID' (can be placed on any module)
}
```

---

## Robot Components

| Part          | Model                    | CAN ID | Additional Details            |
|---------------|--------------------------|--------|-------------------------------|
| Intake Motor  | SparkMax + 775pro        | 6      | No encoder                    |
| Elevator      | SparkMax + NEO Brushless | 5      | Integrated encoder            |
| Solenoid      | Single Solenoid          | 7      | Controlled via PCM ID 0       |
| Compressor    | PCM                      | 0      | Digital automatic activation  |

---

## Controls

### IntakeReleaseCom
**Function:** Command to release the intake.
**Subsystem:** IntakeSub
**Trigger:** Button A on the controller
**Conditions:** Must not have duplicate or conflicting intake configurations.

### IntakeGrabCom
**Function:** Command to grab with the intake.
**Subsystem:** IntakeSub
**Trigger:** Button B on the controller

### ElevatorUpCom
**Function:** Command to raise the elevator.
**Subsystem:** ElevatorSub
**Trigger:** Right Bumper on the controller

### ElevatorDownCom
**Function:** Command to lower the elevator.
**Subsystem:** ElevatorSub
**Trigger:** Left Bumper on the controller

### TurnOnCompressorCom
**Function:** Command to activate the pneumatic system.
**Subsystem:** Solenoid
**Trigger:** Start button on the controller
**Method used:** `SolenoidSUB.CompressorOn();`
**Conditions:** Keep PCM at 0 to avoid fatal errors.

### TurnOffCompressorCom
**Function:** Command to manually deactivate the compressor.
**Subsystem:** Solenoid
**Trigger:** Back button on the controller
**Method used:** `SolenoidSUB.CompressorOff();`

### Calibration
1. **Safety Limits:** Configured by physical sensors
2. **Speeds:** Adjusted for smooth and precise movement

---

## Troubleshooting

### Common Issues

#### Intake doesn't move
**Possible causes:**
- Disconnected motor (check CAN Bus)
- Burned / accidentally shorted cables
- Code not loaded correctly

**Solution:**
1. Check physical connections
2. Review Driver Station for CAN errors (usually "CAN No TOKEN")
3. Re-deploy the code

#### Intake moves too slowly
**Possible causes:**
- Low battery
- Incorrect speed values
- Mechanical resistance

**Solution:**
1. Check battery voltage (visible in Driver Station)
2. Adjust speed constants
3. Check mechanism for obstructions

### Solenoid & Compressor Issues

#### Compressor not working
**Possible causes:**
- Cables not correctly connected to the PCM
- Pressure switch not correctly connected to the PCM (Pneumatics Control Module)
- Code error (HAL Error)

**Solution:**
1. Check connections from RoboRIO to PDP
2. Temporarily bypass the pressure system to verify the compressor works
3. Carefully check the code for duplicate CAN IDs

#### Solenoid not working or not opening valves
**Possible causes:**
- Cables not correctly connected to the corresponding identifier (1, 7) on the PCM
- Incorrect code configuration

**Solution:**
1. Verify that both the code and the physical connection match the corresponding identifier
2. Check the code for duplicate identifiers

---

### Autonomous
Currently disabled — FRC PathPlanner is planned for future use.

**Note:** A very basic autonomous routine may be used in the meantime.

---

## Changelog
**None at this time.**

---

## Team Notes
- Test the code individually and understand its structure thoroughly
- Document any changes in this file
- Back up the code before making major modifications

**Coming soon:**
- Older code versions will be uploaded separately by version to Google Drive
- An English-only version will be created (starting with improving spelling in the Spanish version)

---

## Contact
**Lead Programmers:** Cesar Augusto Ramirez, Angel David Morales Marrufo
**Programming Mentor:** Cesar Augusto Ramirez
**Last Reviewed:** 08/18/2025
