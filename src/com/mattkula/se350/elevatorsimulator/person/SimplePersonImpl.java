package com.mattkula.se350.elevatorsimulator.person;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;
import com.mattkula.se350.elevatorsimulator.statistics.ReportGenerator;

/**
 * An implementation of the Person interface that creates a simple person
 * who starts on one floor and desires to go to another floor through an
 * elevator instance.
 * @author Matt
 *
 */
public class SimplePersonImpl implements Person{
	
	/**
	 * The unique id for each Person to differentiate them.
	 * @see #getId()
	 */
	private int id;
	
	/**
	 * The floor that this person was generated at.
	 */
	private int source;
	
	/**
	 * The desired floor that this instance is trying to get to.
	 * @see #getDestination()
	 */
	private int destination;
	
	/**
	 * The current state of the Person as defined by the enum in the Person interface. 
	 * @see #getStatus()
	 */
	private Person.Status status;
	
	/**
	 * The second of the simulation that the person was created, useful for creating statistics. 
	 */
	private int createdTime;
	
	/**
	 * The second of the simulation that the person moved from the floor to an elevator.
	 */
	private int addedToElevatorTime;
	
	/**
	 * The second of the simulation that the person arrived at their destination.
	 */
	private int finishedTime;
	
	/**
	 * Constructor to create a simple Person object
	 * @param id - The unique id for a Simple Person
	 * @param dest - The floor that the Person wants to get to
	 * @throws InvalidArgumentException if the destination is outside the number of floors.
	 */
	public SimplePersonImpl(int id, int source, int dest) throws InvalidArgumentException{
		setId(id);
		setSource(source);
		setDestination(dest);
		setStatus(Person.Status.WAITING_FOR_ELEVATOR);
		createdTime = Building.getCurrentTime();
	}
		
	/**
	 * Sets the Person's destination, only used in initialization.
	 * @param dest - The floor the Person should get to.
	 * @throws InvalidArgumentException - if the destination is outside the number of floors. 
	 */
	private void setDestination(int dest) throws InvalidArgumentException{
		if(dest < 0 || dest > FloorManager.getInstance().getNumberOfFloors())
			throw new InvalidArgumentException("Person's destination " + dest + " outside of building constraints.");
		
		this.destination = dest;
	}

	/**
	 * Get the destination of the Person object, defined by the Person interface.
	 * @return the floor the Person is trying to get to.
	 */
	@Override
	public int getDestination() {
		return destination;
	}
	
	private void setSource(int source) throws InvalidArgumentException{
		if(source < 0 || source > FloorManager.getInstance().getNumberOfFloors())
			throw new InvalidArgumentException("Person's source " + source + " outside of building constraints.");
		
		this.source = source;
	}
	
	/**
	 * Get the original floor of the person.
	 * @return The story this person started out on.
	 */
	public int getSource(){
		return source;
	}
	
	/**
	 * Get the unique id of the Person object
	 * @return The unique id of the Person object
	 */
	public int getId(){
		return id;
	}

	/**
	 * Sets the id of the Person, used in initialization.
	 * @param idIn - the id you want to give to the person
	 */
	private void setId(int idIn){
		id = idIn;
	}

	/**
	 * Get the status of the Person, as defined by the enum in the Person interface.
	 * @return the Status of the Person.
	 */
	@Override
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Set the status of the Person, as defined by the enum in the Person interface.
	 * @param newStatus - the Status of the Person.
	 */
	private void setStatus(Person.Status newStatus){
		status = newStatus;
	}
	
	/**
	 * Called when the Person is added to the Elevator to measure their wait time.
	 */
	public void setAddedToElevatorTime() {
		this.addedToElevatorTime = Building.getCurrentTime();
	}

	/**
	 * Called when the Person arrives at their destination to measure their ride time.
	 * Also reports the data to the Report Generator.
	 */
	public void setFinishedTime() {
		this.finishedTime = Building.getCurrentTime();
		
		PersonResultDTO result = new PersonResultDTO();
		result.id = getId();
		result.startingFloor = getSource();
		result.endingFloor = getDestination();
		result.waitTime = getWaitTime();
		result.rideTime = getRideTime();
		ReportGenerator.getInstance().addReport(result);
	}
	
	/**
	 * Gets the number of seconds that a person waited for an elevator
	 * @return The number of seconds that a person waited for an elevator
	 */
	public int getWaitTime() {
		return this.addedToElevatorTime - this.createdTime;
	}

	/**
	 * Gets the number of seconds that a person rode on an elevator
	 * @return The number of seconds that a person rode on an elevator
	 */
	public int getRideTime() {
		return this.finishedTime - this.addedToElevatorTime;
	}

	/**
	 * Defines when two SimplePerson objects are equals, useful in removing from Collections.
	 * @return true if the two objects' ids are equal, false if they are not.
	 */
	@Override
	public boolean equals(Object obj) {
		Person other = (Person)obj;
		return getId() == other.getId();
	}
	
	
}
