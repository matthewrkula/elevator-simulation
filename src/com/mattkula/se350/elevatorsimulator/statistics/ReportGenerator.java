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
	
	/**
	 * Keeps track of all of the wait times at each of the floor. Each floor
	 * has it's own ArrayList containing Integers of the wait times at that floor.
	 */
	private ArrayList<ArrayList<Integer>> waitTimes;
	
	/**
	 * Holds data of the ride times for each floor to every other floor.
	 */
	private ArrayList<ArrayList<ArrayList<Integer>>> rideTimes;
	
	/**
	 * A private constructor to ensure the singleton nature of this class.
	 * @param numOfFloors - The number of floors the simulation is running with. 
	 */
	private ReportGenerator(int numOfFloors){
		reports = new ArrayList<PersonResultDTO>();
		waitTimes = new ArrayList<ArrayList<Integer>>();
		rideTimes = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		//Initialize the data holders
		for(int i = 0; i < numOfFloors; i++){
			waitTimes.add(new ArrayList<Integer>());
			
			rideTimes.add(new ArrayList<ArrayList<Integer>>());
			
			for(int j = 0; j < numOfFloors; j++){
				rideTimes.get(i).add(new ArrayList<Integer>());
			}
		}
		
		
	}
	
	/**
	 * Initializes this singleton class, must be called before any calls
	 * to getInstance. 
	 * @param numOfFloors - The number of floors in the simulation.
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
		
		if(report.id <= 0 || report.rideTime <= 0 || report.waitTime < 0 || report.startingFloor <= 0 || report.startingFloor > waitTimes.size() 
				|| report.endingFloor <= 0 || report.endingFloor > waitTimes.size())
			throw new IllegalStateException("Person's report is invalid");
		
		// Add report
		reports.add(report);
		
		// Add wait times
		int floor = report.startingFloor;
		int waitTime = report.waitTime;
		waitTimes.get(floor - 1).add(waitTime);
		
		//Add ride times
		int floorFrom = report.startingFloor;
		int floorTo = report.endingFloor;
		int rideTime = report.rideTime;
		rideTimes.get(floorFrom - 1).get(floorTo - 1).add(rideTime);
		
	}
	
	/**
	 * Prints the final result of the simulation to the console, including all data about avg/min/max wait times,
	 * the individual statistics for each person, and the ride times to and from each floor.
	 * Called at the end of each simulation run. 
	 */
	public void printFinalReport(){
		
		//print the wait times
		System.out.printf("%40s \n", "Average Wait Times by Floor");
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
		
		//Print ride times
		System.out.printf("%40s \n", "Average Ride Times from Floor to Floor");
		for(int i = 0; i < waitTimes.size(); i++){
			for(int j = 0; j < waitTimes.size(); j++){
				int averageTime = getAverageTime(rideTimes.get(i).get(j));
				if(averageTime != -1)
					System.out.printf("%8d ", averageTime);
				else
					System.out.printf("%8s ", "n/a");
			}
			System.out.println();
		}
		
		System.out.println();
		
		//print out all reports
		System.out.printf("%40s \n", "Wait and Ride Times by Person");
		Collections.sort(reports);
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
		if(times.size() == 0)
			return -1;
		
		int total = 0;
		
		for(int i = 0; i < times.size(); i++){
			total += times.get(i);
		}
		
		return total / times.size();
	}
	
	/**
	 * Get the average wait time that people have waited at the floor.
	 * @param floor - the Floor to get the information about
	 * @return The average number of seconds people have waited at that floor, -1 if nobody has waited at that floor
	 */
	public int getAverageFloorWaitTime(int floor){
		if(waitTimes.get(floor-1).size() == 0)
			return -1;
		
		return getAverageTime(waitTimes.get(floor - 1));
	}
	
	/**
	 * Get the average wait time riding from one floor to another
	 * @param from - The floor that the people are riding from
	 * @param to - The floor that the people are riding to
	 * @return The average number of seconds the ride has taken, -1 if nobody has ridden from 'from' to 'to'
	 */
	public int getRideTime(int from , int to){
		return getAverageTime(rideTimes.get(from - 1).get(to - 1));
	}

}
