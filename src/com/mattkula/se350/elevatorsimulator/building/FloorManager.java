package com.mattkula.se350.elevatorsimulator.building;

import java.util.ArrayList;

import com.mattkula.se350.elevatorsimulator.elevator.Elevator;
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
		
		try{
		
			for(int i=1; i <= numOfFloors; i++){
				floors.add(FloorFactory.build(i));
			}
		
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates the floor manager, can only be called once. 
	 * 
	 * @throws IllegalStateException if this method has already been called once elsewhere
	 * @param numOfFloors is the number of floors the building has.
	 */
	public synchronized static void initialize(int numOfFloors){
		if(floorManager != null)
			throw new IllegalStateException("Floor Manager already initialized");
		
		floorManager = new FloorManager(numOfFloors);
	}
	
	/**
	 * Used for testing building creation.
	 */
	public static void destroy(){
		floorManager = null;
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
		
		synchronized(floors.get(story - 1)){
			floors.get(story - 1).addPerson(person);
		}
	}
	
	/**
	 * Adds people from the specified floor to the specified elevator.
	 * @param story - The story that the elevator arrived at
	 * @param e - The elevator to add people to
	 * @throws InvalidArgumentException - if the story is not a valid story
	 */
	public void addPeopleToElevator(int story, Elevator e) throws InvalidArgumentException{
		floors.get(story - 1).addPeopleToElevator(e);
	}
	
	/**
	 * Called when a Control Box is pressed
	 * @param story - The story the Control Box is pressed
	 * @param direction - The direction the person wants to go 
	 * @throws InvalidArgumentException - if the story is not a valid story
	 */
	public void pressControlBoxAt(int story, int direction) throws InvalidArgumentException{
		floors.get(story - 1).pressControlBox(direction);
	}
	
	/**
	 * Method to find out how many floors are in a building
	 * @return The number of floors in a building
	 */
	public int getNumberOfFloors(){
		return floors.size();
	}

}
