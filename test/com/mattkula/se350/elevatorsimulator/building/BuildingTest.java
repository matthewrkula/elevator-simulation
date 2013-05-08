package com.mattkula.se350.elevatorsimulator.building;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class BuildingTest {

	
	@Test(expected=InvalidArgumentException.class)
	public void testBuildingFloorArgument() throws InvalidArgumentException{

		Building b = new Building(-1, 3);

	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testBuildingElevatorArgument() throws InvalidArgumentException{
		Building b = new Building(2, 0);
	}
	
	@Test
	public void testBuildingCompletionPass(){
		try{
			Building goodBuilding = new Building(15, 6);
		}catch(InvalidArgumentException e){
			fail("Building should have been completed");
		}
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testSendingRequestElevatorArgumentFail() throws InvalidArgumentException{
		Building goodBuilding = new Building(15, 6);
		goodBuilding.sendRequestToElevator(-1, 5);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testSendingRequestFloorArgumentFail() throws InvalidArgumentException{
			Building goodBuilding = new Building(15, 6);
			goodBuilding.sendRequestToElevator(3, 0);
	}
	
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
