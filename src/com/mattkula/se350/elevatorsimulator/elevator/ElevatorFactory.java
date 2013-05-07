package com.mattkula.se350.elevatorsimulator.elevator;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class ElevatorFactory {
	
	public static Elevator build(int elevatorNum) throws InvalidArgumentException{
		return new SimpleElevatorImpl(elevatorNum);
	}

}
