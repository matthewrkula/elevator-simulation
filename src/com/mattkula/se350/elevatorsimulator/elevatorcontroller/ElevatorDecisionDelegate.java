package com.mattkula.se350.elevatorsimulator.elevatorcontroller;

/**
 * Interface that defines an delegate for the ElevatorController class. Each
 * implemenation can have it's own algorithm for deciding which elevator to 
 * respond to each call.
 * 
 * @author Matt
 *
 */
public interface ElevatorDecisionDelegate {
	
	/**
	 * Gets the best elevator to send for a request.
	 * @return The number of the elevator that should be sent. 
	 */
	public int getBestElevator();

}
