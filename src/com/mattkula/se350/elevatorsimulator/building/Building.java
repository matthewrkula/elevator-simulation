package com.mattkula.se350.elevatorsimulator.building;

import java.util.ArrayList;
import java.util.Calendar;

import com.mattkula.se350.elevatorsimulator.elevator.Elevator;
import com.mattkula.se350.elevatorsimulator.elevator.ElevatorFactory;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class Building {
	
	ArrayList<Elevator> elevators;
	
	public Building(int numOfFloors, int numOfElevators) throws InvalidArgumentException{
		FloorManager.initialize(numOfFloors);
		
		elevators = new ArrayList<Elevator>();
		
		for(int i=1; i <= numOfElevators; i++){
			elevators.add(ElevatorFactory.build(i));
		}
	}
	
	public void startSimulation() throws Exception{
		for(Elevator e : elevators){
			Thread t = new Thread(e);
			t.start();
		}
		
	}
	
	public void sendRequestToElevator(int elevatorId, int i){
		System.out.printf("%s Sending Elevator %d to Floor %d.\n", Building.getTimeString(), elevatorId, i);
		Elevator e = elevators.get(elevatorId - 1);
		synchronized(e){
			e.notify();
			e.addDestination(i);
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
