package com.mattkula.se350.elevatorsimulator.building;

/**
 * A Factory class that builds appropriate Floors for the desired situations.
 * 
 * @author Matt
 *
 */
public class FloorFactory {
	
	/**
	 * Builds the required Floor implementations
	 * @param story - The story of the building to be built
	 * @return A Floor implementation that best meets the requirements
	 */
	public static Floor build(int story){
		return new SimpleFloorImpl(story);
	}

}
