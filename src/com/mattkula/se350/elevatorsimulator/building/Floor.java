package com.mattkula.se350.elevatorsimulator.building;

import com.mattkula.se350.elevatorsimulator.elevator.Elevator;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;
import com.mattkula.se350.elevatorsimulator.person.Person;

/**
 * Interface representing the behavior of a Floor. So far
 * it just defines adding a person to the floor and getting
 * the story of it. 
 * 
 * @author Matt
 *
 */
public interface Floor {
	
	/**
	 * Adds a person implementation to the floor.
	 * @param person - Person to add to the floor
	 */
	public void addPerson(Person person);
	
	/**
	 * Adds all people on this floor that want to go in the direction of
	 * the elevator to said elevator.
	 * @param e - The elevator to add people to if they want to go in the same direction
	 * @throws InvalidArgumentException if input data is invalid - specified by error message
	 */
	public void addPeopleToElevator(Elevator e) throws InvalidArgumentException;
	
	/**
	 * Get the height/id of the floor. The story of the building which it represents.
	 * @return The story fo the building which the Floor represents.
	 */
	public int getStory();
	
	/**
	 * Method that simulates a Person pressing a ControlBox on whatever Floor they are on in 
	 * their desired direction.
	 * @param direction - The direction the Person wants to go 
	 * @throws InvalidArgumentException if input data is invalid - specified by error message
	 */
	public void pressControlBox(int direction) throws InvalidArgumentException;

}
