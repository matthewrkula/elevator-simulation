package com.mattkula.se350.elevatorsimulator;

import com.mattkula.se350.elevatorsimulator.building.Building;

public class ElevatorSimulation {
	//TODO REMOVE THROWS EXCEPTION
	public static void main(String[] args) throws Exception{

			Building building = new Building(15, 6);
			System.out.println("Starting simulation...");
			building.startSimulation();
			building.sendRequestToElevator(1, 7);
			building.sendRequestToElevator(1, 5);
			building.sendRequestToElevator(1, 10);
	}

}
