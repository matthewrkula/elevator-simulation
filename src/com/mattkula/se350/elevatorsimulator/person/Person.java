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

}
