package com.mattkula.se350.elevatorsimulator.building;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
		assertEquals(FloorManager.getInstance().getNumberOfFloors(), 15);
	}

}
