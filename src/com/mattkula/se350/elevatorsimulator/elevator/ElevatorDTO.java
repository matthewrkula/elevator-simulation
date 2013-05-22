package com.mattkula.se350.elevatorsimulator.elevator;

/**
 * Class that holds data so the ElevatorController can give the status
 * of each Elevator instance to its delegates to choose which one to give
 * a request to.
 * @author Matt
 *
 */
public class ElevatorDTO {
	
	/**
	 * The current floor of the Elevator
	 */
	public int currentFloor;
	
	/**
	 * The current status of the Elevator, as defined by the Elevator.Status enum
	 */
	public Elevator.Status status;
	
	/**
	 * The remaining destinations of the elevator
	 */
	public String remainingDestinations;

}
