package com.mattkula.se350.elevatorsimulator.building;

import java.util.ArrayList;
import java.util.Calendar;

import com.mattkula.se350.elevatorsimulator.elevator.Elevator;
import com.mattkula.se350.elevatorsimulator.elevator.ElevatorFactory;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class Building {
	
	ArrayList<Elevator> elevators;
	
	public Building(int numOfFloors, int numOfElevators) throws InvalidArgumentException{
		if(numOfFloors < 1)
			throw new InvalidArgumentException("Building must have two or more floors for an elevator.");
		
		if(numOfElevators < 1)
			throw new InvalidArgumentException("Building must have an elevator.");
		
		FloorManager.initialize(numOfFloors);
		
		elevators = new ArrayList<Elevator>();
		
		for(int i=1; i <= numOfElevators; i++){
			elevators.add(ElevatorFactory.build(i));
		}
		
		System.out.println("Starting simulation...");
		startSimulation();
	}
	
	public void startSimulation(){
		for(Elevator e : elevators){
			Thread t = new Thread(e);
			t.start();
		}
		
	}
	
	/**
	 * Utility method that allows the simulation "driver" to send requests to the elevators.
	 * 
	 * @param elevatorId - Which elevator to send
	 * @param story - The floor to send the elevator to
	 * @throws InvalidArgumentException if the elevatorId is greater than the number of elevators or less than 1
	 * @throws InvalidArgumentException if the story is greater or less than the number of floors
	 */
	public void sendRequestToElevator(int elevatorId, int story) throws InvalidArgumentException{
		if(elevatorId < 1 || elevatorId > elevators.size())
			throw new InvalidArgumentException("Must call send request to valid elevator, elevatorId outside of range");
		
		if(story < 1 || story > FloorManager.getInstance().getNumberOfFloors())
			throw new InvalidArgumentException("Elevator being sent out of floor range.");
		
		System.out.printf("%s Sending Elevator %d to Floor %d.\n", Building.getTimeString(), elevatorId, story);
		Elevator e = elevators.get(elevatorId - 1);
		synchronized(e){
			e.notify();
			e.addDestination(story);
		}
	}
	
	/**
	 * Keeps track of the Global time, useful for logging.
	 * @return A String formatting of the current hour, minute, second in hh:mm:ss format
	 */
	public static String getTimeString(){
		Calendar c = Calendar.getInstance();
		return String.format("%02d:%02d:%02d ", c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}
	
}
