package com.mattkula.se350.elevatorsimulator.person;

import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

public class SimplePersonImpl implements Person{
	
	private int destination;
	
	private int id;
	
	public SimplePersonImpl(int id, int dest) throws InvalidArgumentException{
		setId(id);
		setDestination(dest);
	}
		
	private void setDestination(int dest) throws InvalidArgumentException{
		if(dest < 0 || dest > FloorManager.getInstance().getNumberOfFloors())
			throw new InvalidArgumentException("Person's destination outside of building constraints.");
		
		this.destination = dest;
	}

	@Override
	public int getDestination() {
		return destination;
	}
	
	public int getId(){
		return id;
	}

	private void setId(int idIn){
		id = idIn;
	}
}
