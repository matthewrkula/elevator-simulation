package com.mattkula.se350.elevatorsimulator.building;

import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * Class that represents the ControlBox on each Floor of the building. A User
 * presses the ControlBox, either up or down, sending the ElevatorController a
 * request that it must delegate and assign to an Elevator. 
 * @author Matt
 *
 */
public class ControlBox {
	
	/**
	 * The story of the Floor the ControlBox belongs to.
	 * @see #setStory(int)
	 * @see #getStory()
	 */
	int story;
	
	/**
	 * Simple Constructor that sets the story of this ControlBox
	 * @param story - The story of the Control Box
	 */
	public ControlBox(int story) throws InvalidArgumentException{
		setStory(story);
	}
	
	/**
	 * Simulates the Person pressing up on the ControlBox. Sends a new request
	 * to the ElevatorController for processing.
	 * 
	 * @throws InvalidArgumentException - if the story is not a valid Floor
	 */
	public void pressUp() throws InvalidArgumentException{
		ElevatorController.getInstance().sendRequest(ElevatorController.UP, story);
	}
	
	/**
	 * Simulates the Person pressing down on the ControlBox. Sends a new request
	 * to the ElevatorController for processing.
	 * 
	 * @throws InvalidArgumentException - if the story is not a valid Floor
	 */
	public void pressDown() throws InvalidArgumentException{
		ElevatorController.getInstance().sendRequest(ElevatorController.DOWN, story);
	}
	
	/**
	 * Sets the story of the ControlBox, used in initialization
	 * @param storyIn - The story to set the ControlBox's story to
	 */
	private void setStory(int storyIn) throws InvalidArgumentException{
		if(storyIn <= 0)
			throw new InvalidArgumentException("Elevator Control Box set on an illegal floor number");
		
		story = storyIn;
	}

	/**
	 * Get the story of Floor that the ControlBox is on
	 * @return The story of the Floor that the ControlBox is on.
	 */
	public int getStory(){
		return story;
	}
}
