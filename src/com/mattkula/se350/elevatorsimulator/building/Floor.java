package com.mattkula.se350.elevatorsimulator.building;

import com.mattkula.se350.elevatorsimulator.person.Person;

public interface Floor {
	
	public void addPerson(Person person);
	
	public int getStory();

}
