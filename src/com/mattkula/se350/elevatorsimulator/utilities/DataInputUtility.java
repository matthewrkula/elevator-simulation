package com.mattkula.se350.elevatorsimulator.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.mattkula.se350.elevatorsimulator.building.BuildingStatsDTO;

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
	 * @return BuildingStatsDTO that contains all neccessary simulation data.
	 */
	public static BuildingStatsDTO getBuildingInfoFromFile(){
		File data = new File("simulation_data.txt");
		String dataString = "";
		BuildingStatsDTO returnObject = null;
		Gson parser = new Gson();
		
		try {
			Scanner s = new Scanner(data);
			
			while(s.hasNextLine()){
				dataString += s.nextLine();
			}
			
			return returnObject = parser.fromJson(dataString, BuildingStatsDTO.class);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnObject;
	}
}
