package com.mattkula.se350.elevatorsimulator.building;

import java.util.ArrayList;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;
import com.mattkula.se350.elevatorsimulator.person.Person;

/**
 * A Singleton facade that handles everything to do with Floors, including adding people and
 * removing people from them and getting their size. 
 * 
 * This class is a singleton, but must be initialized using the initialize(int numOfFloors)
 * method. This can only be called once. 
 * 
 * @author Matt
 *
 */

public class FloorManager {
	
	/**
	 * Keeps the instance cached to use as a Singleton.
	 */
	private static FloorManager floorManager;
	
	/**
	 * Contains all of the Floor instances in the building.
	 */
	private static ArrayList<Floor> floors;
	
	/**
	 * Private constructor that creates the one and only instance of FloorManager. This creates
	 * as many Floor instances as building stories and holds them in an ArrayList.
	 * @param numOfFloors - The number of floors in the building
	 */
	private FloorManager(int numOfFloors){
		floors = new ArrayList<Floor>();
		
		for(int i=1; i <= numOfFloors; i++){
			floors.add(FloorFactory.build(i));
		}
	}
	
	
	/**
	 * Creates the floor manager, can only be called once. 
	 * 
	 * @throws IllegalStateException if this method has already been called once elsewhere
	 * @param numOfFloors is the number of floors the building has.
	 */
	public synchronized static void initialize(int numOfFloors){
//		if(floorManager != null)
//			throw new IllegalStateException("Floor Manager already initialized");
		
		floorManager = new FloorManager(numOfFloors);
	}
	
	/**
	 * Static method that returns a reference to the only FloorManager instance.
	 * @return The Singleton instance of FloorManager
	 * @throws IllegalStateException if initialize is not called once before invoking getInstance()
	 */
	public static FloorManager getInstance(){
		if(floorManager == null)
			throw new IllegalStateException("Must initialize FloorManager before using it");
		
		return floorManager;
	}
	
	/**
	 * Adds a Person instance to a specific floor. 
	 * @param story - The number of the floor you are adding the person to
	 * @param person - The person you are adding to the floor
	 */
	public void addPersonToFloor(int story, Person person) throws InvalidArgumentException{
		if(story < 1 || story > getInstance().getNumberOfFloors())
			throw new InvalidArgumentException("Person being added out of floor range.");
		
		floors.get(story - 1).addPerson(person);
	}
	
	/**
	 * Method to find out how many floors are in a building
	 * @return The number of floors in a building
	 */
	public int getNumberOfFloors(){
		return floors.size();
	}

}
