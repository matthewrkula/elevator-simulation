package com.mattkula.se350.elevatorsimulator.building;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;
import com.mattkula.se350.elevatorsimulator.person.PersonGenerator;

public class FloorManagerTest {

	//Tests using FloorManager before initialization, should throw exception
	@Test(expected=IllegalStateException.class)
	public void testInitializeFail() {
		FloorManager.getInstance();
	}
	
	//Tests initializing the FloorManager
	@Test
	public void testInitializeSuccess(){
		FloorManager.initialize(15);
	}
	
	//Tests the the FloorManager has the number of floors it was initialized with
	@Test
	public void testNumberOfFloorsSuccess(){
		try{
			FloorManager.initialize(15);
		}catch(IllegalStateException e){
			//pass
		}
		assertEquals(FloorManager.getInstance().getNumberOfFloors(), 15);
	}
	
	//Tests the the FloorManager has the number of floors it was initialized with
	@Test(expected=InvalidArgumentException.class)
	public void testAddToIllegalFloor() throws InvalidArgumentException{
		try{
			FloorManager.initialize(15);
		}catch(IllegalStateException e){
			//pass
		}
		FloorManager.getInstance().addPersonToFloor(16, PersonGenerator.build(1, 2, 1));
	}
	

}
