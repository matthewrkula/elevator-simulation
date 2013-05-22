package com.mattkula.se350.elevatorsimulator.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.mattkula.se350.elevatorsimulator.building.BuildingStatsDTO;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * Utility class that has a function that gets all simulation data
 * neccessary for execution from a file.
 * 
 * @author Matt
 *
 */
public class DataInputUtility {
	
	/**
	 * Gets all simulation that is neccessary for execution from a file.
	 * @param inputFile - the File to get the data from
	 * @return BuildingStatsDTO that contains all neccessary simulation data.
	 */
	public static BuildingStatsDTO getBuildingInfoFromFile(String inputFile) throws InvalidArgumentException{
		File data = new File(inputFile);
		String dataString = "";
		BuildingStatsDTO returnObject = null;
		
		try {
			Scanner s = new Scanner(data);
			
			while(s.hasNextLine()){
				dataString = s.nextLine();
			}
			
			String[] args = dataString.split(",");
			
			int duration = Integer.parseInt(args[0].trim());
			int scale = Integer.parseInt(args[1].trim());
			int floors = Integer.parseInt(args[2].trim());
			int elevators = Integer.parseInt(args[3].trim());
			int maxPersons = Integer.parseInt(args[4].trim());
			int msPerFloor = Integer.parseInt(args[5].trim());
			int msDoorOperation = Integer.parseInt(args[6].trim());
			int defaultElevatorFloor = Integer.parseInt(args[7].trim());
			int personsPerMinute = Integer.parseInt(args[8].trim());
			
			int[] startDestPct = new int[floors];
			
			for(int i = 9; i < args.length; i++){
				startDestPct[i-9] = Integer.parseInt(args[i].trim());
			}
			
			returnObject = new BuildingStatsDTO(duration, scale, floors, elevators, maxPersons, msPerFloor,
												msDoorOperation, defaultElevatorFloor, personsPerMinute, startDestPct);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnObject;
	}
}
