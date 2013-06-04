package com.mattkula.se350.elevatorsimulator.building;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;



/**
 * A Data holder class that contains all the specific statistics about
 * each  simulation. Useful in transferring information from the input
 * text file to the actual creation of the simulation.
 * 
 * @author Matt
 *
 */
public class BuildingStatsDTO {
	
	/**
	 * The duration that the simulation should last.
	 */
	private int simulationTime;
	
	/**
	 * The factor by which the simulated time will be sped up in relation
	 * to real time.
	 */
	private int timeScaleFactor;
	
	/**
	 * Number of floors the simulation should have.
	 */
	private int numOfFloors;
	
	/**
	 * Number of elevators the simulation should have.
	 */
	private int numOfElevators;
	
	/**
	 * Max capacity of the elevators in the simulation.
	 */
	private int maxPersonsPerElevator;
	
	/**
	 * Milliseconds it takes for an elevator to move up/down a floor.
	 */
	private int msPerFloor;
	
	/**
	 * Milliseconds it takes for an elevator to do a floor exchange.
	 */
	private int msDoorOperation;
	
	/**
	 * The default floor elevators go to after timing out.
	 */
	private int[] defaultFloor;
	
	/**
	 * The number of generated people per simulation minute.
	 */
	private int personsPerMinute;
	
	/**
	 * Percentages of person being generated at/with destination of each floor.
	 */
	private int[] floorStatsPercentages;
	
	
	/**
	 * Constructor that just creates the data holder object.
	 * @param simulationTimeIn - Simulation time
	 * @param timeScaleFactorIn - Number of simulated seconds per real second
	 * @param numOfFloorsIn - Number of floors in the building
	 * @param numOfElevatorsIn - number of elevators in the building
	 * @param maxPersonsPerElevatorIn - Max capacity of the elevators
	 * @param msPerFloorIn - Time for elevators to travel a floor
	 * @param msDoorOperationIn - Time for elevators to exchange at a floor
	 * @param defaultFloorIn - Default floor for the elevators
	 * @param personsPerMinuteIn - Number of people generated per minute
	 * @param floorStatsPercentagesIn - Statics of the floors of the building
	 */
	public BuildingStatsDTO(int simulationTimeIn, int timeScaleFactorIn, int numOfFloorsIn, int numOfElevatorsIn, int maxPersonsPerElevatorIn, int msPerFloorIn,
							int msDoorOperationIn, int defaultFloorIn[], int personsPerMinuteIn, int[] floorStatsPercentagesIn) throws InvalidArgumentException{
		
		setSimulationTime(simulationTimeIn);
		setTimeScaleFactor(timeScaleFactorIn);
		setNumOfFloors(numOfFloorsIn);
		setNumOfElevators(numOfElevatorsIn);
		setMaxPersonsPerElevator(maxPersonsPerElevatorIn);
		setMsPerFloor(msPerFloorIn);
		setMsDoorOperation(msDoorOperationIn);
		setDefaultFloor(defaultFloorIn);
		setPersonsPerMinute(personsPerMinuteIn);
		setFloorStatsPercentages(floorStatsPercentagesIn);
	}

	/**
	 * @return The simulation time
	 */
	public int getSimulationTime() {
		return simulationTime;
	}

	/**
	 * Sets the simulation time, used in initialization.
	 * @param simulationTime -  The simulation time in minutes.
	 */
	private void setSimulationTime(int simulationTime) throws InvalidArgumentException{
		if(simulationTime <= 0)
			throw new InvalidArgumentException("Simulation time must be greater than 0");
		
		this.simulationTime = simulationTime;
	}

	/**
	 * @return The number of simulation seconds for every regular second
	 */
	public int getTimeScaleFactor() {
		return timeScaleFactor;
	}

	/**
	 * Sets the time scale factor, used in initialization.
	 * @param timeScaleFactor -  The number of simulation seconds for every regular second.
	 */
	private void setTimeScaleFactor(int timeScaleFactor) throws InvalidArgumentException{
		if(timeScaleFactor <= 0)
			throw new InvalidArgumentException("Simulation time scale factor must be greater than 0");
		
		this.timeScaleFactor = timeScaleFactor;
	}

	/**
	 * @return The number of floors in the building.
	 */
	public int getNumOfFloors() {
		return numOfFloors;
	}

	/**
	 * Sets the number of floors in the building, used in initialization.
	 * @param numOfFloors - the number of floors in the building
	 */
	private void setNumOfFloors(int numOfFloors) throws InvalidArgumentException{
		if(numOfFloors <= 1)
			throw new InvalidArgumentException("Building must have at least two floors");
		
		this.numOfFloors = numOfFloors;
	}

	/**
	 * @return The nubmer of elevators in the building.
	 */
	public int getNumOfElevators() {
		return numOfElevators;
	}

	/**
	 * Sets the number of elevators in the building, used in initialization.
	 * @param numOfElevators - the number of elevators in the building
	 */
	private void setNumOfElevators(int numOfElevators) throws InvalidArgumentException{
		if(numOfElevators <= 0)
			throw new InvalidArgumentException("Building must have at least one elevator");
		
		this.numOfElevators = numOfElevators;
	}

	/**
	 * @return The max capacity of the elevators.
	 */
	public int getMaxPersonsPerElevator() {
		return maxPersonsPerElevator;
	}

	/**
	 * Sets the max capacity of the elevators, used in initialization.
	 * @param maxPersonsPerElevator - Elevator max capacity
	 */
	private void setMaxPersonsPerElevator(int maxPersonsPerElevator) throws InvalidArgumentException{
		if(maxPersonsPerElevator <= 0)
			throw new InvalidArgumentException("Elevator max capacity must be greater than 0");
		
		this.maxPersonsPerElevator = maxPersonsPerElevator;
	}

	/**
	 * @return Milliseconds it takes for an Elevator to move past a floor.
	 */
	public int getMsPerFloor() {
		return msPerFloor;
	}

	/**
	 * Sets the travel time per floor for an Elevator, used in initialization.
	 * @param msPerFloor - Travel time per floor in milliseconds
	 */
	private void setMsPerFloor(int msPerFloor) throws InvalidArgumentException{
		if(msPerFloor <= 0)
			throw new InvalidArgumentException("Milliseconds Per Floor must be greater than 0");
		
		this.msPerFloor = msPerFloor;
	}

	/**
	 * @return Milliseconds it takes for an elevator to do a floor exchange.
	 */
	public int getMsDoorOperation() {
		return msDoorOperation;
	}

	/**
	 * Sets the door operation time for an Elevator, used in initialization
	 * @param msDoorOperation - The time it takes to open, add/remove people and close
	 */
	private void setMsDoorOperation(int msDoorOperation) throws InvalidArgumentException{
		if(msDoorOperation <= 0)
			throw new InvalidArgumentException("Door operation time must be greater than 0");
		
		this.msDoorOperation = msDoorOperation;
	}

	/**
	 * @return The default floor of the elevators.
	 * @param elevator - The number of the elevator to get the default floor for
	 */
	public int getDefaultFloor(int elevator) {
		return defaultFloor[elevator - 1];
	}

	/**
	 * Sets the default floor of the elevators, used in initialization.
	 * @param defaultFloor - The floor Elevators should default to
	 */
	private void setDefaultFloor(int defaultFloor[]) throws InvalidArgumentException{
		if(defaultFloor.length != getNumOfElevators())
			throw new InvalidArgumentException("Not the right number of default floors in input file.");
		
		for(int i = 0; i < defaultFloor.length; i++){
			if(defaultFloor[i] <= 0 || defaultFloor[i] > getNumOfFloors())
				throw new InvalidArgumentException("Default floor for Elevator" + i+1 + " in input file is not a valid floor");
		}
		
		this.defaultFloor = defaultFloor;
	}

	/**
	 * @return The number of people generated per minute by the simulation.
	 */
	public int getPersonsPerMinute() {
		return personsPerMinute;
	}

	/**
	 * Sets the Persons generated per minute in the simulation, used in initialization.
	 * @param personsPerMinute - Persons generated per minute
	 */
	private void setPersonsPerMinute(int personsPerMinute) throws InvalidArgumentException{
		if(personsPerMinute < 0)
			throw new InvalidArgumentException("Persons per minute must be >= 0");
		
		this.personsPerMinute = personsPerMinute;
	}

	/**
	 * @return Array of the floor statistics used by PersonGenerator
	 */
	public int getFloorPercentage(int floor) {
		return floorStatsPercentages[floor-1];
	}

	/**
	 * The statistics of the floor generation.
	 * @param floorStatsPercentages - Array of percentages per floor to generate Person instances.
	 */
	private void setFloorStatsPercentages(int[] floorStatsPercentages) throws InvalidArgumentException{
		if(floorStatsPercentages.length != getNumOfFloors())
			throw new InvalidArgumentException("Your floor stats do not equal the number of floors");
		this.floorStatsPercentages = floorStatsPercentages;
	}
	
	/**
	 * @return A string representation of the BuildingStatsDTO
	 */
	public String toString(){
		String s = "";
		s += "Simulation Time: " + getSimulationTime() + "\n";
		s += "Time Scale Factor: " + getTimeScaleFactor() + "\n";
		s += "Number of Floors: " + getNumOfFloors() + "\n";
		s += "Number of Elevators: " + getNumOfElevators() + "\n";
		s += "Max Persons Per Elevator: " + getMaxPersonsPerElevator() + "\n";
		s += "ms Per Floor: " + getMsPerFloor() + "\n";
		s += "ms Door Operation: " + getMsDoorOperation() + "\n";
		
		for(int i = 1; i <= getNumOfElevators(); i++){
			s += "Default Floor: " + getDefaultFloor(i) + "\n";
		}
		s += "Persons Per Minute: " + getPersonsPerMinute() + "\n";
		
		for(int i = 1; i <= getNumOfFloors(); i++){
			s += "Floor " + i + " Percentage: " + getFloorPercentage(i) + "\n";
		}
		
		return s;
	}

}
