package com.mattkula.se350.elevatorsimulator.elevatorcontroller;

import com.mattkula.se350.elevatorsimulator.elevator.Elevator;
import com.mattkula.se350.elevatorsimulator.elevator.ElevatorDTO;

public class SimpleElevatorDecisionDelegate implements ElevatorDecisionDelegate{
	
	
	public int getBestElevator(int direction, int story, ElevatorDTO[] data) {
		
		
		for(int i = 1; i <= data.length; i++){
			ElevatorDTO elevator = data[i-1];
			if((elevator.status == Elevator.Status.MOVING_UP && elevator.currentFloor < story && direction == ElevatorController.UP) ||
					(elevator.status == Elevator.Status.MOVING_DOWN && elevator.currentFloor > story && direction == ElevatorController.DOWN)){
				System.out.println(String.format("Best fit for %d is %d", story, i));
				return i;
			}
			
			if((elevator.status == Elevator.Status.WAITING || elevator.status == Elevator.Status.WAITING_DEFAULT)
				&& elevator.currentFloor == story){
				System.out.println(String.format("Best fit for %d is %d", story, i));
				return i;
			}
		}
		
		for(int i = 1; i <= data.length; i++){
			ElevatorDTO elevator = data[i-1];
			if(elevator.status == Elevator.Status.WAITING_DEFAULT){
				System.out.println(String.format("Best fit for %d is %d", story, i));
				return i;
			}
		}
		
		for(int i = 1; i <= data.length; i++){
			ElevatorDTO elevator = data[i-1];
			if(elevator.status == Elevator.Status.WAITING){
				System.out.println(String.format("Best fit for %d is %d", story, i));
				return i;
			}
		}
		
		
		System.out.println(String.format("No good fit for %d", story));
		return 0;
	}

}
