package com.mattkula.se350.elevatorsimulator.building;

public class FloorFactory {
	
	public static Floor build(int story){
		return new SimpleFloorImpl(story);
	}

}
