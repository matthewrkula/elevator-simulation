package com.mattkula.se350.elevatorsimulator;

import java.io.FileNotFoundException;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * ElevatorSimulation contains a main method that is the driver
 * for the current simulation. It starts the simulation then does
 * nothing else.
 * 
 * @author Matt
 *
 */
public class ElevatorSimulation {

	public static void main(String[] args) throws InvalidArgumentException, FileNotFoundException{
		
		/* 
		   ****************************************************
		   * Change second parameter to:					  *
		   *	ElevatorController.IMPROVED_DECISION_DELEGATE * 
		   * for my custom implementation. 					  *
		   ****************************************************
		*/
		
		Building building = new Building("simulation_data.txt", ElevatorController.DEFAULT_DECISION_DELEGATE);			//Default algorithm
//		Building building = new Building("simulation_data.txt", ElevatorController.IMPROVED_DECISION_DELEGATE);			//My algorithm
			
	}
}
