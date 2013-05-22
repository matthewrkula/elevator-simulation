package com.mattkula.se350.elevatorsimulator.person;

import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

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
	 * Constructor to create a simple Person object
	 * @param id - The unique id for a Simple Person
	 * @param dest - The floor that the Person wants to get to
	 * @throws InvalidArgumentException if the destination is outside the number of floors.
	 */
	public SimplePersonImpl(int id, int dest) throws InvalidArgumentException{
		setId(id);
		setDestination(dest);
		setStatus(Person.Status.WAITING_FOR_ELEVATOR);
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
	 * Defines when two SimplePerson objects are equals, useful in removing from Collections.
	 * @return true if the two objects' ids are equal, false if they are not.
	 */
	@Override
	public boolean equals(Object obj) {
		Person other = (Person)obj;
		return getId() == other.getId();
	}
	
	
}
