package com.mattkula.se350.elevatorsimulator.elevator;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * An Elevator interface that defines behavior for every elevator implementation.
 * 
 * Contains methods for adding destinations, getting remaining destinations, and getting the current
 * floor of the elevator.
 * 
 * @author Matt
 *
 */
public interface Elevator extends Runnable{
	
	/**
	 * Enum that defines the different states that an Elevator instance can be in depending
	 * on it's current action.
	 *@see #getStatus()
	 */
	enum Status{
		WAITING, WAITING_DEFAULT, MOVING_UP, MOVING_DOWN, DOORS_OPENING, DOORS_CLOSING, DOORS_OPENED
	}
	
	/**
	 * Adds a destination to the Elevator implementation.
	 * @param floorNum - the floor the elevator should stop at. 
	 * @throws InvalidArgumentException if the floor is not in the building
	 */
	public void addDestination(int floorNum) throws InvalidArgumentException;
	
    /**
     * Indicates the current floor that the elevator is stopped at or passing.
     * @return The current floor that the elevator is stopped at or passing.
     */
	public int getCurrentFloor();
	
	/**
     * Gets information about the pending requests on the Elevator instance.
     * @return A string containing the remaining pending requests owned by the Elevator.
     */
	public String getRemainingDestinations();
	
	/**
     * Gets the Status of an elevator as defined in Elevator.Status
     * @return The status of the elevator as defined in Elevator.Status
     */
	public Elevator.Status getStatus();
	
}