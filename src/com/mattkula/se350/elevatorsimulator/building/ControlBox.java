package com.mattkula.se350.elevatorsimulator.building;

import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class ControlBox {
	
	int story;
	
	public ControlBox(int storyIn){
		story = storyIn;
	}
	
	public void pressUp() throws InvalidArgumentException{
		ElevatorController.getInstance().sendRequest(ElevatorController.UP, story);
	}
	
	public void pressDown() throws InvalidArgumentException{
		ElevatorController.getInstance().sendRequest(ElevatorController.DOWN, story);
	}

}
