package com.mattkula.se350.elevatorsimulator;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * ElevatorSimulation contains a main method that is the driver
 * for the current simulation. It is planned that this will be
 * deprecated when an ElevatorController is implemented and 
 * random People are being generated.
 * 
 * @author Matt
 *
 */
public class ElevatorSimulation {
	//TODO REMOVE THROWS EXCEPTION
	public static void main(String[] args){
		try{
			Building building = new Building(15, 6);
			
			building.sendRequestToElevator(1, 7);
			building.sendRequestToElevator(1, 5);
			building.sendRequestToElevator(1, 10);
		}catch(InvalidArgumentException e){
			System.out.println(e.getMessage());
		}
	}

}
