package com.mattkula.se350.elevatorsimulator.elevator;


public interface Elevator extends Runnable{
	
	enum Status{
		WAITING, WAITING_DEFAULT, MOVING_UP, MOVING_DOWN, DOORS_OPENING, DOORS_CLOSING, DOORS_OPENED
	}
	
	public void addDestination(int floorNum);
	
	public int getCurrentFloor();
	
	public String getRemainingDestinations();
	
}