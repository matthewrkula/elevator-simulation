package com.mattkula.se350.elevatorsimulator.elevatorcontroller;

import com.mattkula.se350.elevatorsimulator.elevator.Elevator;
import com.mattkula.se350.elevatorsimulator.elevator.ElevatorDTO;

/**
 * Implementation of the ElevatorDecisionDelegate interface that improves upon
 * the SimpleElevatorDecisionDelegate's algorithm. The ElevatorController delegates
 * the selection 
 * @author Matt
 *
 */
public class ImprovedElevatorDecisionDelegate implements ElevatorDecisionDelegate{

	/**
	 * Chooses the best elevator to send a destination to based on the direction of the request,
	 * the story that needs to be stopped at, and the current position/direction of all of the 
	 * elevators. 
	 */
	public int getBestElevator(int direction, int story, ElevatorDTO[] data) {
		
		// checks if a waiting elevator is on that floor then checks if the new request is in the moving path of an elevator.
		for(int i = 1; i <= data.length; i++){
			ElevatorDTO elevator = data[i-1];
			
			if((elevator.status == Elevator.Status.WAITING || elevator.status == Elevator.Status.WAITING_DEFAULT)
					&& elevator.currentFloor == story){
					System.out.println(String.format("Best fit for %d is %d", story, i));
					return i;
			}
			
			if((elevator.status == Elevator.Status.MOVING_UP && elevator.currentFloor < story && direction == ElevatorController.UP) ||
					(elevator.status == Elevator.Status.MOVING_DOWN && elevator.currentFloor > story && direction == ElevatorController.DOWN)){
				System.out.println(String.format("Best fit for %d is %d", story, i));
				return i;
			}
		}
		//end pass
		
		//Finds the closest waiting elevator
		int closestElevator = 0;
		int closestDistance = 16;
		
		for(int i = data.length; i > 0; i--){
			ElevatorDTO elevator = data[i-1];
			
			if(elevator.status == Elevator.Status.WAITING_DEFAULT || elevator.status == Elevator.Status.WAITING){
				int distance = getDistance(elevator.currentFloor, story);
				
				if(distance <= closestDistance){
					closestDistance = distance;
					closestElevator = i;
				}
			
			}
		}
		
		if(closestElevator != 0){
			System.out.println(String.format("Best fit for %d is %d", story, closestElevator));
			return closestElevator;
		}
		//end pass
		
		
		//No good fit
		System.out.println(String.format("No good fit for %d", story));
		return 0;
	}
	
	/**
	 * Get the distance between two floors.
	 * @param currentFloor - The current floor of the elevator
	 * @param destFloor - The floor to get the distance from
	 * @return The distance between the two floors. 
	 */
	private int getDistance(int currentFloor, int destFloor){
		return Math.abs(currentFloor - destFloor);
	}

}
