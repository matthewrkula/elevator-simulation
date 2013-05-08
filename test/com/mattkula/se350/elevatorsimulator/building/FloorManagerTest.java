package com.mattkula.se350.elevatorsimulator.building;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class FloorManagerTest {

	@Test(expected=IllegalStateException.class)
	public void testInitializeFail() {
		FloorManager.getInstance();
	}
	
	@Test
	public void testInitializeSuccess(){
		FloorManager.initialize(15);
	}
	
	@Test
	public void testNumberOfFloorsSuccess(){
		assertEquals(FloorManager.getInstance().getNumberOfFloors(), 15);
	}

}
