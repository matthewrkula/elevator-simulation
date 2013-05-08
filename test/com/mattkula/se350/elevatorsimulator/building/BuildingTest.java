package com.mattkula.se350.elevatorsimulator.building;

import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class BuildingTest {

	
	@Test
	public void testBuildingFloorArgument(){
		try{
			Building b = new Building(-1, 3);
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBuildingElevatorArgument(){
		try{
			Building b = new Building(2, 0);
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBuildingCompletionPass(){
		try{
			Building goodBuilding = new Building(15, 6);
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendingRequestElevatorArgumentFail(){
		try{
			Building goodBuilding = new Building(15, 6);
			goodBuilding.sendRequestToElevator(-1, 5);
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendingRequestFloorArgumentFail(){
		try{
			Building goodBuilding = new Building(15, 6);
			goodBuilding.sendRequestToElevator(3, 0);
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendingRequestPass(){
		try{
			Building goodBuilding = new Building(15, 6);
			goodBuilding.sendRequestToElevator(3, 10);
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
	}

}
