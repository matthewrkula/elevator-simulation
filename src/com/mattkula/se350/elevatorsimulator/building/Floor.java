package com.mattkula.se350.elevatorsimulator.building;

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
	 * Get the height/id of the floor. The story of the building which it represents.
	 * @return The story fo the building which the Floor represents.
	 */
	public int getStory();

}
