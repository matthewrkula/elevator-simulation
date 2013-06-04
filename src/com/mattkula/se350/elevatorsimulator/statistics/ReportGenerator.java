package com.mattkula.se350.elevatorsimulator.statistics;

import java.util.ArrayList;
import java.util.Collections;

import com.mattkula.se350.elevatorsimulator.person.PersonResultDTO;

/**
 * Singleton class that is responsible for storing data about Person instances' waiting times
 * and riding times. It is initialized at the beginning of a simulation and prints out all of the
 * statistics at the end of the simulation. 
 * 
 * @author Matt
 *
 */
public class ReportGenerator {
	
	/**
	 * A static member of ReportGenerator as used by the Singleton pattern
	 */
	private static ReportGenerator rGenerator;
	
	/**
	 * Keeps track of all reported data from Persons who arrived at their
	 * destinations. 
	 * @see #addReport(PersonResultDTO)
	 */
	private ArrayList<PersonResultDTO> reports;
	private ArrayList<ArrayList<Integer>> waitTimes;
	
	/**
	 * A private constructor to ensure the singleton nature of this class.
	 * @param numOfFloors - The number of floors the simulation is running with. 
	 */
	private ReportGenerator(int numOfFloors){
		reports = new ArrayList<PersonResultDTO>();
		waitTimes = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < numOfFloors; i++){
			waitTimes.add(new ArrayList<Integer>());
		}
		
		
	}
	
	/**
	 * Initializes this singleton class, must be called before any calls
	 * to getInstance. 
	 * @param numOfFloors
	 */
	public static void initialize(int numOfFloors){
		rGenerator = new ReportGenerator(numOfFloors);
	}
	
	/**
	 * Enables others to get a reference to this class in singleton fashion. Can 
	 * only be called after calling initialize()
	 * @return The only instance of the ReportGenerator
	 */
	public static ReportGenerator getInstance(){
		if(rGenerator == null)
			throw new IllegalStateException("ReportGenerator must be initialized first");
		
		return rGenerator;
	}
	
	/**
	 * Method for a Person to add their data to the collection of reports in order to 
	 * analyze all of the people's experiences. Called when a person arrives at their
	 * destination. 
	 * @param report - The data about a person that is to be added
	 */
	public void addReport(PersonResultDTO report){
		
		// Add report
		reports.add(report);
		
		// Add wait times
		int floor = report.startingFloor;
		int waitTime = report.waitTime;
		waitTimes.get(floor - 1).add(waitTime);
	}
	
	/**
	 * Prints the final result of the simulation to the console, including all data about avg/min/max wait times,
	 * the individual statistics for each person, and the ride times to and from each floor.
	 * Called at the end of each simulation run. 
	 */
	public void printFinalReport(){
		
		//print the wait times
		System.out.println(String.format("%15s %15s %15s %15s", "Floor", "Avg Wait Time", "Min Wait Time", "Max Wait Time"));
		
		for(int i = 0; i < waitTimes.size(); i++){
			ArrayList<Integer> times = waitTimes.get(i);
			if(times.size() > 0)
				System.out.printf("%15d %15s %15s %15s \n", i+1, ReportGenerator.getAverageTime(times) + " sec", Collections.min(times) + " sec", Collections.max(times) + " sec");
			else{
				System.out.printf("%15d %15s %15s %15s \n", i+1, "n/a", "n/a", "n/a");
			}
		}
		
		System.out.println();
		
		//print out all reports
		System.out.println(String.format("%15s %15s %15s %15s %15s", "Person", "Wait Time", "Start Floor", "Dest Floor", "Ride Time"));
		
		for(int i = 0; i < reports.size(); i++){
			PersonResultDTO report = reports.get(i);
			System.out.println(String.format("%15d %15d %15d %15d %15d", report.id, report.waitTime, report.startingFloor, 
					report.endingFloor, report.rideTime));
		}
	}
	
	/**
	 * Gets the average time from an ArrayList of integers
	 * @param times - An array list containing Integers of seconds waited/ridden
	 * @return The average of all of the numbers in the ArrayList
	 */
	private static int getAverageTime(ArrayList<Integer> times){
		int total = 0;
		
		for(int i = 0; i < times.size(); i++){
			total += times.get(i);
		}
		
		return total / times.size();
	}

}
