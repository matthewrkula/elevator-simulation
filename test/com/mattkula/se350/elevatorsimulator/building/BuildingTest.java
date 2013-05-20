package com.mattkula.se350.elevatorsimulator.building;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class BuildingTest {

	//Tests making a building with no floors, should throw exception
	@Test(expected=InvalidArgumentException.class)
	public void testBuildingFloorArgument() throws InvalidArgumentException{

		Building b = new Building(-1, 3);

	}
	
	//Tests making a building with no elevators, should throw exception
	@Test(expected=InvalidArgumentException.class)
	public void testBuildingElevatorArgument() throws InvalidArgumentException{
		Building b = new Building(2, 0);
	}
	
	//Tests making a building with valid data, should pass
	@Test
	public void testBuildingCompletionPass(){
		try{
			Building goodBuilding = new Building(15, 6);
		}catch(InvalidArgumentException e){
			fail("Building should have been completed");
		}
	}
	
	//Tests sending request to a bad elevator id, should throw exception
	@Test(expected=InvalidArgumentException.class)
	public void testSendingRequestElevatorArgumentFail() throws InvalidArgumentException{
		Building goodBuilding = new Building(15, 6);
		goodBuilding.sendRequestToElevator(-1, 5);
	}
	
	//Tests sending request to non-existant floor, should throw exception
	@Test(expected=InvalidArgumentException.class)
	public void testSendingRequestFloorArgumentFail() throws InvalidArgumentException{
			Building goodBuilding = new Building(15, 6);
			goodBuilding.sendRequestToElevator(3, 0);
	}
	
	//Tests sending request with valid data, should pass
	@Test
	public void testSendingRequestPass(){
		try{
			Building goodBuilding = new Building(15, 6);
			goodBuilding.sendRequestToElevator(3, 10);
		}catch(InvalidArgumentException e){
			fail("InvalidArgumentException should not have been thrown");
		}
	}

}
