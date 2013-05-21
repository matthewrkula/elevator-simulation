package com.mattkula.se350.elevatorsimulator.building;

import com.google.gson.annotations.SerializedName;


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
	@SerializedName("duration")
	private int simulationTime;
	
	/**
	 * The factor by which the simulated time will be sped up in relation
	 * to real time.
	 */
	@SerializedName("scale")
	private int timeScaleFactor;
	
	/**
	 * Number of floors the simulation should have.
	 */
	@SerializedName("floors")
	private int numOfFloors;
	
	/**
	 * Number of elevators the simulation should have.
	 */
	@SerializedName("elevators")
	private int numOfElevators;
	
	/**
	 * Max capacity of the elevators in the simulation.
	 */
	@SerializedName("max_persons")
	private int maxPersonsPerElevator;
	
	/**
	 * Milliseconds it takes for an elevator to move up/down a floor.
	 */
	@SerializedName("ms_per_floor")
	private int msPerFloor;
	
	/**
	 * Milliseconds it takes for an elevator to do a floor exchange.
	 */
	@SerializedName("ms_door_operation")
	private int msDoorOperation;
	
	/**
	 * The default floor elevators go to after timing out.
	 */
	@SerializedName("default_elevator_floor")
	private int defaultFloor;
	
	/**
	 * The number of generated people per simulation minute.
	 */
	@SerializedName("persons_per_minute")
	private int personsPerMinute;
	
	/**
	 * Percentages of person being generated at/with destination of each floor.
	 */
	@SerializedName("start_dest_pct")
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
							int msDoorOperationIn, int defaultFloorIn, int personsPerMinuteIn, int[] floorStatsPercentagesIn){
		
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

	public int getSimulationTime() {
		return simulationTime;
	}

	private void setSimulationTime(int simulationTime) {
		this.simulationTime = simulationTime;
	}

	public int getTimeScaleFactor() {
		return timeScaleFactor;
	}

	private void setTimeScaleFactor(int timeScaleFactor) {
		this.timeScaleFactor = timeScaleFactor;
	}

	public int getNumOfFloors() {
		return numOfFloors;
	}

	private void setNumOfFloors(int numOfFloors) {
		this.numOfFloors = numOfFloors;
	}

	public int getNumOfElevators() {
		return numOfElevators;
	}

	private void setNumOfElevators(int numOfElevators) {
		this.numOfElevators = numOfElevators;
	}

	public int getMaxPersonsPerElevator() {
		return maxPersonsPerElevator;
	}

	private void setMaxPersonsPerElevator(int maxPersonsPerElevator) {
		this.maxPersonsPerElevator = maxPersonsPerElevator;
	}

	public int getMsPerFloor() {
		return msPerFloor;
	}

	private void setMsPerFloor(int msPerFloor) {
		this.msPerFloor = msPerFloor;
	}

	public int getMsDoorOperation() {
		return msDoorOperation;
	}

	private void setMsDoorOperation(int msDoorOperation) {
		this.msDoorOperation = msDoorOperation;
	}

	public int getDefaultFloor() {
		return defaultFloor;
	}

	private void setDefaultFloor(int defaultFloor) {
		this.defaultFloor = defaultFloor;
	}

	public int getPersonsPerMinute() {
		return personsPerMinute;
	}

	private void setPersonsPerMinute(int personsPerMinute) {
		this.personsPerMinute = personsPerMinute;
	}

	public int getFloorPercentage(int floor) {
		return floorStatsPercentages[floor-1];
	}

	private void setFloorStatsPercentages(int[] floorStatsPercentages) {
		this.floorStatsPercentages = floorStatsPercentages;
	}
	
	public String toString(){
		String s = "";
		s += "Simulation Time: " + getSimulationTime() + "\n";
		s += "Time Scale Factor: " + getTimeScaleFactor() + "\n";
		s += "Number of Floors: " + getNumOfFloors() + "\n";
		s += "Number of Elevators: " + getNumOfElevators() + "\n";
		s += "Max Persons Per Elevator: " + getMaxPersonsPerElevator() + "\n";
		s += "ms Per Floor: " + getMsPerFloor() + "\n";
		s += "ms Door Operation: " + getMsDoorOperation() + "\n";
		s += "Default Floor: " + getDefaultFloor() + "\n";
		s += "Persons Per Minute: " + getPersonsPerMinute() + "\n";
		
		for(int i = 1; i <= getNumOfFloors(); i++){
			s += "Floor " + i + " Percentage: " + getFloorPercentage(i) + "\n";
		}
		
		return s;
	}

}
