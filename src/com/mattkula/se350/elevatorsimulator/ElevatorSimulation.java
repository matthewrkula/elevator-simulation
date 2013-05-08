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

	public static void main(String[] args){
		try{
			Building building = new Building(15, 6);
			
			Thread.sleep(1000);
			building.sendRequestToElevator(1, 11);
			Thread.sleep(1000);
			
			building.sendRequestToElevator(2, 14);
			Thread.sleep(1000);
			
			building.sendRequestToElevator(2, 13);
			Thread.sleep(1000);
			
			building.sendRequestToElevator(2, 15);
			
			Thread.sleep(30000);
			
			building.sendRequestToElevator(1, 10);
			Thread.sleep(1000);
			
			building.sendRequestToElevator(1, 1);
			Thread.sleep(12000);
			building.sendRequestToElevator(1, 2);
			building.sendRequestToElevator(1, 5);
			building.sendRequestToElevator(1, 3);
			
			
		}catch(InvalidArgumentException e){
			System.out.println(e.getMessage());
		}catch(InterruptedException e){
			System.out.println(e.getMessage());
		}
	}

}
