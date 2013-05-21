package com.mattkula.se350.elevatorsimulator.building;

import java.util.Calendar;

import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;
import com.mattkula.se350.elevatorsimulator.person.PersonGenerator;
import com.mattkula.se350.elevatorsimulator.utilities.DataInputUtility;

/**
 * Building the container for the entire Elevator simulation, creating and holding all of the elevators
 * and initializing the FloorManager.
 * 
 * @author Matt
 *
 */
public class Building {
	
	/**
	 * Building data compiled from data text file. Contains all the data
	 * neccessary to create a customizable simulation. 
	 */
	private BuildingStatsDTO buildingStats;
	
	/**
	 * Integer used for keeping track of the simulation time. 
	 */
	private static int currentTime;
	
	/**
	 * Duration of the simulation in minutes, must be multiplied by 60 
	 * in order to get the length in seconds. Specified through input file.
	 */
	private int durationInMinutes;
	
	/**
	 * The ratio of time to make faster simulations possible. 
	 * Ex. If timeScale==5, every five seconds of simulation time
	 * is one second of real time. In other words, there is a 5:1 ratio. 
	 */
	private static int timeScale;
	
	/**
	 * Boolean that keeps track of if the simulation is still running. 
	 * Starts true then becomes false after currentTime >= durationInMintues * 60
	 */
	private static boolean isRunning;
	
	/**
	 * Creates a Building instance which creates/holds all of the elevators, initializes 
	 * the FloorManager, and then begins the simulation by starting a thread for each elevator.
	 * @throws InvalidArgumentException when floors < 2 or number of elevators < 1
	 */
	public Building() throws IllegalStateException, InvalidArgumentException{
		isRunning = true;
		currentTime = 0;
		buildingStats = DataInputUtility.getBuildingInfoFromFile();
		
		if(buildingStats == null){
			throw new IllegalStateException("simulation_data.txt not found");
		}
		
		durationInMinutes = buildingStats.getSimulationTime();
		timeScale = buildingStats.getTimeScaleFactor();
		
		FloorManager.initialize(buildingStats.getNumOfFloors());
		PersonGenerator.initialize(buildingStats);
		ElevatorController.initialize(buildingStats);	//Starts the elevator threads
		simulate();
	}
	
	/**
	 * The main loop for the main thread that keeps the simulation running for (durationInMinutes*60) seconds.
	 * Also makes a call to possibly generate a person every second.
	 * @throws InvalidArgumentException if data in the input file is invalid, as specified by the message
	 */
	private void simulate() throws InvalidArgumentException{
		try {
			
			while(currentTime < durationInMinutes*60){
				currentTime++;
				Thread.sleep(1000 / Building.getTimeScale());
				PersonGenerator.getInstance().generateAndAddPerson();
			}
			
			isRunning = false;
			System.out.println("ALL DONE.");
			System.exit(0);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Keeps track of the Global time, useful for logging.
	 * @return A String formatting of the current hour, minute, second in hh:mm:ss format
	 */
	public static String getTimeString(){
		Calendar c = Calendar.getInstance();
		return String.format("%02d:%02d:%02d ", c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}
	
	/**
	 * Get the time scale of the simulation, the larger the number, the faster the simulation is
	 * compared to real time. 
	 * @return The number of simulated seconds that correspond to actual seconds. 
	 */
	public static int getTimeScale(){
		return timeScale;
	}
	
	/**
	 * Get whether the simulation is still running or over.
	 * @return True if the simulation is running, false if it is over
	 */
	public static boolean isRunning(){
		return isRunning;
	}
	
	/**
	 * Get current time in the simulation, specified by simulated seconds
	 * @return The number of simulated seconds gone by since it began
	 */
	public static int getCurrentTime(){
		return currentTime;
	}
	
}
