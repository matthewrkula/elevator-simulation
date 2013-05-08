package com.mattkula.se350.elevatorsimulator.building;

import java.util.ArrayList;

import com.mattkula.se350.elevatorsimulator.person.Person;

/**
 * A simple implementation of the Floor interface. 
 * 
 * @author Matt
 */
public class SimpleFloorImpl implements Floor{
	
	/**
	 * The story of the Floor, used as an id
	 * @see #getStory()
	 * @see #setStory(int)
	 */
	int story;
	
	/**
	 * A collection of all the Person implementations contained on this floor
	 * @see #addPerson(Person)
	 */
	ArrayList<Person> people;
	
	public SimpleFloorImpl(int story){
		setStory(story);
		people = new ArrayList<Person>();
	}
	
	/**
	 * Adds a Person to this Floor
	 * @param person - Person implementation to add
	 */
	public void addPerson(Person person){
		synchronized(this){
			people.add(person);
		}
	}
	
	/**
	 * Sets the story of the floor
	 * @param storyIn - the story to set
	 */
	private void setStory(int storyIn){
		story = storyIn;
	}

	/**
	 * Gets the story of the floor.
	 * @param storyIn - the story to set.
	 */
	public int getStory(){
		return story;
	}
}
