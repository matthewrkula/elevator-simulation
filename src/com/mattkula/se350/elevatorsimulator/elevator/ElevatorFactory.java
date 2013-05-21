package com.mattkula.se350.elevatorsimulator.elevator;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * Factory that creates Elevator implementations that best fit the parameters. 
 * 
 * @author Matt
 *
 */
public class ElevatorFactory {
	
	/**
	 * Creates Elevator implementations that best fit the parameters.
	 * @param elevatorNum - Number/id of the elevator
	 * @return Best fit implementation of an Elevator
	 * @throws InvalidArgumentException if the elevatorNum < 1
	 */
	public static Elevator build(int elevatorNum, int defaultFloor, int msPerFloor, int msDoorOperation) throws InvalidArgumentException{
		return new SimpleElevatorImpl(elevatorNum, defaultFloor, msPerFloor, msDoorOperation);
	}

}
