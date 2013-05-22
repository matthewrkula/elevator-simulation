package com.mattkula.se350.elevatorsimulator.elevatorcontroller;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.building.BuildingStatsDTO;
import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class ElevatorControllerTest {
	
	@BeforeClass
	public static void setup(){
		FloorManager.initialize(5);
	}

	@Test(expected=InvalidArgumentException.class)
	public void testFailInitialization() throws InvalidArgumentException {
		ElevatorController.initialize(new BuildingStatsDTO(1, 1, 2, 0, 12, 1000, 2200, 1, 0, new int[]{100, 0}));
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testFailInitializationBadDefaultFloor() throws InvalidArgumentException {
		ElevatorController.initialize(new BuildingStatsDTO(1, 1, 2, 1, 12, 1000, 2200, 0, 0, new int[]{100, 0}));
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testFailInitializationBadMaxCapacity() throws InvalidArgumentException {
		ElevatorController.initialize(new BuildingStatsDTO(1, 1, 2, 1, 0, 1000, 2200, 1, 0, new int[]{100, 0}));
	}

	@Test
	public void testInitializationPass(){
		try{
			ElevatorController.initialize(new BuildingStatsDTO(1, 1, 5, 1, 12, 1000, 2200, 1, 0, new int[]{100, 0, 0, 0, 0}));
		}catch(Exception e){
			fail("Elevator Controller should have initialized");
		}
	}
	
	@Test
	public void testReturnsCorrectNumberOfElevatorDTOs() throws InvalidArgumentException{
		ElevatorController.initialize(new BuildingStatsDTO(1, 1, 5, 1, 12, 1000, 2200, 1, 0, new int[]{100, 0, 0, 0, 0}));
		assertEquals(1, ElevatorController.getInstance().getElevatorData().length);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testSendRequestToBadElevator() throws InvalidArgumentException{
		ElevatorController.getInstance().sendRequestToElevator(0, 3);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testSendRequestToBadFloor() throws InvalidArgumentException{
		ElevatorController.getInstance().sendRequestToElevator(1, 6);
	}
	
	@Test
	public void testSendRequestSuccess() throws InvalidArgumentException{
		ElevatorController.getInstance().sendRequestToElevator(1, 3);
	}
}
