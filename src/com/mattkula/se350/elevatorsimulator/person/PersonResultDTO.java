package com.mattkula.se350.elevatorsimulator.person;

/**
 * Simple Data Tranfer Object that helps a user send their completed report of waiting and ride times
 * to the Report Generator. The data is already checked for errors before this, so no error checking
 * is necessary, therefore all data members are pulic.
 * 
 * @author Matt
 *
 */
public class PersonResultDTO {
	
	/**
	 * The id of the person whose stats are being reported. 
	 */
	public int id;
	
	/**
	 * The amount of time the person waited for an elevator. 
	 */
	public int waitTime;
	
	/**
	 * The amount of time the person rode the elevator to the destination.
	 */
	public int rideTime;
	
	/**
	 * The floor the person started out on.
	 */
	public int startingFloor;
	
	/**
	 * The floor the person ended on.
	 */
	public int endingFloor;

}
