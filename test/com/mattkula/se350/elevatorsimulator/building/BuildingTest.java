package com.mattkula.se350.elevatorsimulator.building;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class BuildingTest {
	
	@After
	public void destroyFloorManager(){
		FloorManager.destroy();
	}
	
	//Tests making a building with valid data, should pass
	@Test
	public void testBuildingCompletionPass(){
		try{
			Building b = new Building("simulation_data_testing.txt", ElevatorController.DEFAULT_DECISION_DELEGATE, true);
		}catch(InvalidArgumentException e){
			fail("Building should have been completed");
		}catch(FileNotFoundException e){
			fail("Building should have been completed");
		}
	}
	
	//Tests sending request to a bad elevator id, should throw exception
	@Test(expected=InvalidArgumentException.class)
	public void testSendingRequestElevatorArgumentFail() throws InvalidArgumentException, FileNotFoundException{
		Building b = new Building("simulation_data_testing.txt", ElevatorController.DEFAULT_DECISION_DELEGATE, true);
		ElevatorController.getInstance().sendRequestToElevator(-1, 5);
	}
	
	//Tests sending request to non-existant floor, should throw exception
	@Test(expected=InvalidArgumentException.class)
	public void testSendingRequestFloorArgumentFail() throws InvalidArgumentException, FileNotFoundException{
			Building b = new Building("simulation_data_testing.txt", ElevatorController.DEFAULT_DECISION_DELEGATE, true);
			ElevatorController.getInstance().sendRequestToElevator(3, 0);
	}
	
	//Tests sending request with valid data, should pass
	@Test
	public void testSendingRequestPass(){
		try{
			Building b = new Building("simulation_data_testing.txt", ElevatorController.DEFAULT_DECISION_DELEGATE, true);
			ElevatorController.getInstance().sendRequestToElevator(3, 10);
		}catch(InvalidArgumentException e){
			fail("InvalidArgumentException should not have been thrown");
		}catch(FileNotFoundException e){
			fail("File should have been found");
		}
	}

}
