package com.mattkula.se350.elevatorsimulator.person;

import java.util.Random;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.building.BuildingStatsDTO;
import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * Singleton that is responsible for generating random people and giving
 * them destinations based on the input data, but also has a static method
 * to work as a Factory. Creating another factory class would be too bulky,
 * so it was implemented into this class.
 * 
 * @author Matt
 *
 */
public class PersonGenerator {
	
	/**
	 * Variable to make this a singleton
	 */
	private static PersonGenerator generator;
	
	/**
	 * Keeps track of the number of people generated so each personhas a unique id
	 */
	private static int currentId = 0;
	
	/**
	 * Statistic of how many people to generate per simulated minute. Gathered from input text file.
	 */
	private static int personsPerMinute;
	
	/**
	 * Percentages that somebody will be generated at each floor or their destination will be that floor.
	 * Each story refers to index-1
	 */
	private static int[] floorStats;
	
	/**
	 * A random number generator useful for putting Persons on random floors and giving them 
	 * random destinations.
	 */
	private static Random r;

	/**
	 * Private method to use as a singleton.
	 */
	private PersonGenerator(){
	}
	
	/**
	 * Gets the PersonGenerator instance in singleton fashion
	 * @return The single instance of PersonGenerator
	 */
	public static PersonGenerator getInstance(){
		if(generator == null)
			throw new IllegalStateException("Person Generator must be initialized first.");
		
		return generator;
	}
	
	/**
	 * Initializes the PersonGenerator with data that affects how
	 * and when people will be created
	 * @param buildingStats - Holds all of the data neccessary for creating the PersonGenerator
	 */
	public static void initialize(BuildingStatsDTO buildingStats){
		generator = new PersonGenerator();
		personsPerMinute = buildingStats.getPersonsPerMinute();
		floorStats = new int[buildingStats.getNumOfFloors()];
		r = new Random();
		
		int counterTo100 = 0;
		for(int i = 1; i <= floorStats.length; i++){
			floorStats[i-1] = buildingStats.getFloorPercentage(i);
			counterTo100 += buildingStats.getFloorPercentage(i);
		}
		
		if(counterTo100 != 100)
			throw new IllegalStateException("Floor statistics do not add up to 100");
	}
	
	/**
	 * Method to create people and add them to floors. Called every second, 
	 * but only creates people based on the number of  peoplePerMinute specified by
	 * the text file input. 
	 * @return 0 if nothing was generated or the new person's destination if somebody was
	 * @throws InvalidArgumentException if Person creation data is invalid
	 */
	public int generateAndAddPerson() throws InvalidArgumentException{
		
		if(r.nextInt(60) < personsPerMinute){
				
			currentId++;
			
			int source = getSourceFloor();
			int dest = getDestFloor(source);
			
			Person p = PersonGenerator.build(currentId, dest);
			FloorManager.getInstance().addPersonToFloor(source, p);
			
			System.out.println(String.format("%s Person %d added at %d pressed %s to go to %d", Building.getTimeString(), currentId, source, (dest > source ? "UP" : "DOWN"), dest));
			
			FloorManager.getInstance().pressControlBoxAt(source, (dest > source ? ElevatorController.UP : ElevatorController.DOWN));
			
			
			return source;
		}
		
		return 0;
	}
	
	/**
	 * Generates starting floor by statistics provided by the input file.
	 * @return The floor that the new Person instance should start on.
	 */
	private int getSourceFloor(){

		int randInt = r.nextInt(100) + 1;		//Number from 1-100
		
		int i = 0, counter = 0;
		
		while( counter < randInt ){
			counter += floorStats[i];
			i++;
		}
		
		return i;
	}
	

	/**
	 * Generates random destination floor for the new Person instance
	 * @param sourceFloor - The floor the person begins on, cannot return this floor.
	 * @return The destination of the new Person instance.
	 */
	private int getDestFloor(int sourceFloor){
		
		int[] adjustedPcts = new int[floorStats.length];
		
		for(int i = 0; i < floorStats.length; i++){
			adjustedPcts[i] = floorStats[i];
		}
		
		Integer toSpread = adjustedPcts[sourceFloor - 1];
		adjustedPcts[sourceFloor - 1] = 0;
		
		int newTotalSum = 100 - toSpread;
		
		for(int i = 0; toSpread > 0; i = (i+1) % adjustedPcts.length, toSpread--){
			if(i != (sourceFloor - 1)){
				adjustedPcts[i]++;
			}
		}

		int randInt = r.nextInt(newTotalSum) + 1;		//Number from 1-100
		
		int i = 0, counter = 0;
		
		while( counter < randInt ){
			counter += adjustedPcts[i];
			i++;
		}
		
		return i;
	}
	
	
	/**
	 * Creates Person implementations that best fit the parameters in
	 * a Factory Pattern type of way.
	 * @param id - Unique id of the Person instance to be generated
	 * @param dest - The destination of the new Person implementation
	 * @return Best fit implementation of an Person
	 * @throws InvalidArgumentException if the destination is not a valid floor
	 */
	public static Person build(int id, int dest) throws InvalidArgumentException{
		return new SimplePersonImpl(id, dest);
	}

}
