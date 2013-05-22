package com.mattkula.se350.elevatorsimulator.utilities;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;
import com.mattkula.se350.elevatorsimulator.utilities.DataInputUtility;

public class DataInputUtilityTest {

	//Should throw exception because elevator number is zero
	@Test(expected=InvalidArgumentException.class)
	public void ZeroElevatorCountTest() throws InvalidArgumentException{
		DataInputUtility.getBuildingInfoFromFile("simulation_data_error1.txt");
		
	}
	
	//Should throw exception because deafult floor is not a real floor
	@Test(expected=InvalidArgumentException.class)
	public void InvlalidDefaultFloorTest() throws InvalidArgumentException{
		DataInputUtility.getBuildingInfoFromFile("simulation_data_error2.txt");
		
	}
	
	//Should throw exception because number of persons per minute must be greater than zero
	@Test(expected=InvalidArgumentException.class)
	public void InvalidPersosnPerMinuteTest() throws InvalidArgumentException{
		DataInputUtility.getBuildingInfoFromFile("simulation_data_error3.txt");
		
	}
	
	//Should throw exception because number of persons per minute must be greater than zero
	@Test
	public void ValidDataTest(){
		try{
			DataInputUtility.getBuildingInfoFromFile("simulation_data.txt");
		}catch(InvalidArgumentException e){
			fail("Should have loaded correctly");
		}
	}

	

}
