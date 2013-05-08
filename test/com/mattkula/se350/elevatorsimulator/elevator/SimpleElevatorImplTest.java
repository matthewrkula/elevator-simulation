package com.mattkula.se350.elevatorsimulator.elevator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class SimpleElevatorImplTest {
	
	Building b;
	
	@BeforeClass
	public void setup() throws Exception{
		b = new Building(15, 6);
	}

	@Test
	public void test() {
		try{
			b.sendRequestToElevator(1, 3);
			b.sendRequestToElevator(1, 5);
			b.sendRequestToElevator(1, 7);
		}catch(InvalidArgumentException e){
			System.out.println(e.getMessage());
		}
	}

}
