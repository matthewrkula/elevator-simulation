package com.mattkula.se350.elevatorsimulator.person;

import java.util.Random;

import com.mattkula.se350.elevatorsimulator.building.BuildingStatsDTO;
import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * Singleton that is responsible for generating random people and giving
 * them destinations based on the input data. 
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
		
		for(int i = 1; i <= floorStats.length; i++){
			floorStats[i-1] = buildingStats.getFloorPercentage(i);
		}
	}
	
	/**
	 * Method to create people and add them to floors. Called every second, 
	 * but only creates people based on the number of  peoplePerMinute specified by
	 * the text file input. 
	 * @return 0 if nothing was generated or the new person's destination if somebody was
	 * @throws InvalidArgumentException if Person creation data is invalid
	 */
	public int generateAndAddPerson() throws InvalidArgumentException{
		Random r = new Random();
		
		
		if(r.nextInt(60) < personsPerMinute){
				
			currentId++;
			
			int source = r.nextInt(floorStats.length)+1;
			int dest;
			do{
				dest = r.nextInt(floorStats.length)+1;
			}while(dest == source);
			
			Person p = PersonFactory.build(currentId, dest);
			FloorManager.getInstance().addPersonToFloor(source, p);
			
			System.out.println(String.format("Person %d added at %d pressed %s to go to %d", currentId, source, (dest > source ? "UP" : "DOWN"), dest));
			
			FloorManager.getInstance().pressControlBoxAt(source, (dest > source ? ElevatorController.UP : ElevatorController.DOWN));
			
			
			return source;
		}
		
		return 0;
	}

}
