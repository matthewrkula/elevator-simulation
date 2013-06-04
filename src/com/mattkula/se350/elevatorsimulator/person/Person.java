package com.mattkula.se350.elevatorsimulator.person;

/**
 * Interface that describes a person who wants to use an elevator. 
 * @author Matt
 *
 */
public interface Person {
	
	/**
	 * Enum of different Statuses a person can be in.
	 * @author Matt
	 *
	 */
	enum Status{
		WAITING_FOR_ELEVATOR, IN_ELEVATOR, REACHED_DESTINATION
	}
	
	/**
	 * Get the Original floor of this person
	 * @return The floor this person was generated on.
	 */
	public int getSource();
	
	/**
	 * Gets the desired destination story for this person.
	 * @return The desired destination story for this person.
	 */
	public int getDestination();
	
	/**
	 * Get the status of the person, useful for finding out where
	 * a person is.
	 * @return The Status of the Person instance
	 */
	public Person.Status getStatus();
	
	/**
	 * Get the unique id for the person, useful for differentiating the diffent instances
	 * of the implementations.
	 * @return The unique id for the Person instance
	 */
	public int getId();
	
	/**
	 * Called when the Person is added to the Elevator to measure their wait time.
	 */
	public void setAddedToElevatorTime();
	
	/**
	 * Called when the Person arrives at the destination to measure how long their ride was. 
	 */
	public void setFinishedTime();

	/**
	 * Gets the number of seconds that a person waited for an elevator
	 * @return The number of seconds that a person waited for an elevator
	 */
	public int getWaitTime();
	
	/**
	 * Gets the number of seconds that a person rode on an elevator
	 * @return The number of seconds that a person rode on an elevator
	 */
	public int getRideTime();
}
