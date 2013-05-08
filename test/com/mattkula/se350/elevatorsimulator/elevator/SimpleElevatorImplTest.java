package com.mattkula.se350.elevatorsimulator.elevator;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class SimpleElevatorImplTest {
	
	static FloorManager manager;
	static ArrayList<Elevator> elevators;
	
	public void sendRequestToElevator(int elevatorId, int story) throws InvalidArgumentException{
		Elevator e = elevators.get(elevatorId - 1);
		synchronized(e){
			e.notify();
			e.addDestination(story);
		}
	}
	
	@BeforeClass
	public static void setup() throws Exception{
		FloorManager.initialize(15);
		manager = FloorManager.getInstance();
		elevators = new ArrayList<Elevator>();
		
		for(int i = 1; i <= 4; i ++){
			elevators.add(ElevatorFactory.build(i));
			Thread t = new Thread(elevators.get(i-1));
			t.start();
		}
		
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void testElevatorDefaultFloorFail() throws InvalidArgumentException{
		Elevator e = new SimpleElevatorImpl(5, 30);
	}

	@Test(expected=InvalidArgumentException.class)
	public void testElevatorRefusesBadDataFailure() throws InvalidArgumentException{
		sendRequestToElevator(1, 20);
	}
	
	@Test
	public void testElevatorApprxOneSecondPerFloor(){
		try{
			
			Thread.sleep(100);
			sendRequestToElevator(1, 12);
			Thread.sleep(12000);
			assertEquals(12, elevators.get(0).getCurrentFloor());
			
		}catch (InvalidArgumentException | InterruptedException ex){
			fail("Elevator should have gone to floor 12");
		}
	}
	
	@Test
	public void testOppositeDirectionIgnored(){
		try{
			Elevator e = elevators.get(1);
			
			Thread.sleep(100);
			sendRequestToElevator(2, 12);
			Thread.sleep(3000);
			sendRequestToElevator(2, 2);
			
			assertEquals("[12]", e.getRemainingDestinations());
			
		}catch (InvalidArgumentException | InterruptedException ex){
			fail("Elevator should have gone to floor 12");
		}
	}
	
	@Test
	public void testElevatorTimeout(){
		try{
			Elevator e = elevators.get(3);
			
			Thread.sleep(100);
			sendRequestToElevator(3, 4);
			Thread.sleep(20000);
			
			assertEquals(1, e.getCurrentFloor());
			
		}catch (InvalidArgumentException | InterruptedException ex){
			fail("Elevator should have timed out, not had an invalid argument" +
					"");
		}
	}
	
	@Test
	public void testElevatorOrganizesRequestsInOrder(){
		try{
			Elevator e = elevators.get(3);
			
			Thread.sleep(100);
			sendRequestToElevator(4, 7);
			sendRequestToElevator(4, 6);
			sendRequestToElevator(4, 9);
			
			
			assertEquals("[6, 7, 9]", e.getRemainingDestinations());
			
		}catch (InvalidArgumentException | InterruptedException ex){
			fail("Elevator should have timed out, not had an invalid argument" +
					"");
		}
	}

}
