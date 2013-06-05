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
	 * @throws InvlaidArgumentException - if the Data in the file is incorrect/illegal
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
			int counter = 0;
			
			int duration = Integer.parseInt(args[counter++].trim());
			int scale = Integer.parseInt(args[counter++].trim());
			int floors = Integer.parseInt(args[counter++].trim());
			int elevators = Integer.parseInt(args[counter++].trim());
			int maxPersons = Integer.parseInt(args[counter++].trim());
			int msPerFloor = Integer.parseInt(args[counter++].trim());
			int msDoorOperation = Integer.parseInt(args[counter++].trim());
			
			int[] defaultFloors = new int[elevators];
			for(int i = 0; i < elevators; i++){
				defaultFloors[i] = Integer.parseInt(args[counter++].trim());
			}
			
			int personsPerMinute = Integer.parseInt(args[counter++].trim());
			
			int[] startDestPct = new int[floors];
			
			for(int i = 0; i < floors; i++){
				startDestPct[i] = Integer.parseInt(args[counter++].trim());
			}
			
			returnObject = new BuildingStatsDTO(duration, scale, floors, elevators, maxPersons, msPerFloor,
												msDoorOperation, defaultFloors, personsPerMinute, startDestPct);
			
//			System.out.println(returnObject.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnObject;
	}
}
