package com.mattkula.se350.elevatorsimulator.elevatorcontroller;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.elevator.Elevator;
import com.mattkula.se350.elevatorsimulator.elevator.ElevatorDTO;

/**
 * Delegate class that is given the task of choosing the best option of Elevator
 * for a given request. Used by the ElevatorController, implements ElevatorDecisionDelegate.
 * 
 * @author Matt
 *
 */
public class SimpleElevatorDecisionDelegate implements ElevatorDecisionDelegate{
	
	/**
	 * Chooses the best elevator to send a destination to based on the direction of the request,
	 * the story that needs to be stopped at, and the current position/direction of all of the 
	 * elevators. 
	 */
	public int getBestElevator(int direction, int story, ElevatorDTO[] data) {
		
		
		for(int i = 1; i <= data.length; i++){
			ElevatorDTO elevator = data[i-1];
			if((elevator.status == Elevator.Status.MOVING_UP && elevator.currentFloor < story && direction == ElevatorController.UP) ||
					(elevator.status == Elevator.Status.MOVING_DOWN && elevator.currentFloor > story && direction == ElevatorController.DOWN)){
//				System.out.println(String.format("Best fit for %d is %d", story, i));
				System.out.println(String.format("%s Sending Elevator %d to Floor %d", Building.getTimeString(), i, story));
				return i;
			}
			
			if((elevator.status == Elevator.Status.WAITING || elevator.status == Elevator.Status.WAITING_DEFAULT)
				&& elevator.currentFloor == story){
//				System.out.println(String.format("Best fit for %d is %d", story, i));
				System.out.println(String.format("%s Sending Elevator %d to Floor %d", Building.getTimeString(), i, story));
				return i;
			}
		}
		
		for(int i = 1; i <= data.length; i++){
			ElevatorDTO elevator = data[i-1];
			if(elevator.status == Elevator.Status.WAITING_DEFAULT){
//				System.out.println(String.format("Best fit for %d is %d", story, i));
				System.out.println(String.format("%s Sending Elevator %d to Floor %d", Building.getTimeString(), i, story));
				return i;
			}
		}
		
		for(int i = 1; i <= data.length; i++){
			ElevatorDTO elevator = data[i-1];
			if(elevator.status == Elevator.Status.WAITING){
//				System.out.println(String.format("Best fit for %d is %d", story, i));
				System.out.println(String.format("%s Sending Elevator %d to Floor %d", Building.getTimeString(), i, story));
				return i;
			}
		}
		
		
		System.out.println(String.format("%s No good fit for %d", Building.getTimeString(), story));
		return 0;
	}

}
