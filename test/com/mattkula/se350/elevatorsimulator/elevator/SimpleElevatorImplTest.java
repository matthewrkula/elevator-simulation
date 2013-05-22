package com.mattkula.se350.elevatorsimulator.elevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.building.BuildingStatsDTO;
import com.mattkula.se350.elevatorsimulator.elevatorcontroller.ElevatorController;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class SimpleElevatorImplTest {
	
	static ElevatorController ec;
	
	@BeforeClass
	public static void setup() throws Exception{
		BuildingStatsDTO stats = new BuildingStatsDTO(1, 1, 15, 4, 12, 1000, 2200, 1, 0, new int[]{100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
		
		Building b = new Building("simulation_data_testing.txt", true);
		
		ec = ElevatorController.getInstance();
	}
	
	//Tests adding a bad default floor using the constructor
	@Test(expected=InvalidArgumentException.class)
	public void testElevatorDefaultFloorFail() throws InvalidArgumentException{
		Elevator e = new SimpleElevatorImpl(5, 30, 1000, 2200);
	}

	//Tests adding a bad default floor to the pending requests of an elevator
	@Test(expected=InvalidArgumentException.class)
	public void testElevatorRefusesBadDataFailure() throws InvalidArgumentException{
		ec.sendRequestToElevator(1, 20);
	}
	
	//Tests movement of the elevator, taking approximately one second per floor
	@Test
	public void testElevatorApprxOneSecondPerFloor(){
		try{
			
			Thread.sleep(100);
			ec.sendRequestToElevator(1, 12);
			Thread.sleep(13000);
			
			
			assertEquals(12, ec.getElevatorData()[0].currentFloor);
			
		}catch (InvalidArgumentException | InterruptedException ex){
			fail("Elevator should have gone to floor 12");
		}
	}
	
	//Tests ignoring of requests while moving in the opposite direction
	@Test
	public void testOppositeDirectionIgnored(){
		try{
			Thread.sleep(100);
			ec.sendRequestToElevator(2, 12);
			Thread.sleep(4000);
			ec.sendRequestToElevator(2, 2);
			
			assertEquals("[12]", ec.getElevatorData()[1].remainingDestinations);
			
		}catch (InvalidArgumentException | InterruptedException ex){
			fail("Elevator should have gone to floor 12");
		}
	}
	
	//Tests that the elevator times out and returns to default floor after some time
	@Test
	public void testElevatorTimeout(){
		try{
			
			Thread.sleep(100);
			ec.sendRequestToElevator(3, 4);
			Thread.sleep(30000);
			
			assertEquals(1, ec.getElevatorData()[2].currentFloor);
			
		}catch (InvalidArgumentException | InterruptedException ex){
			fail("Elevator should have timed out, not had an invalid argument" +
					"");
		}
	}
	
	//Tests that an elevator organizes requests out of order in the same direction
//	@Test
//	public void testElevatorOrganizesRequestsInOrder(){
//		try{
//			Thread.sleep(100);
//			ec.sendRequestToElevator(4, 7);
//			ec.sendRequestToElevator(4, 6);
//			ec.sendRequestToElevator(4, 9);
//			
//			
//			assertEquals("[6, 7, 9]", e.getRemainingDestinations());
//			
//		}catch (InvalidArgumentException | InterruptedException ex){
//			fail("Elevator should have timed out, not had an invalid argument" +
//					"");
//		}
//	}

}
