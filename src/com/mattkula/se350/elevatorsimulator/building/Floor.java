package com.mattkula.se350.elevatorsimulator.building;

import java.util.ArrayList;

import com.mattkula.se350.elevatorsimulator.person.Person;

public class Floor {
	
	int story;
	
	ArrayList<Person> people;
	
	public Floor(int story){
		setStory(story);
	}
	
	public synchronized void addPerson(Person person){
		people.add(person);
	}
	
	private void setStory(int storyIn){
		story = storyIn;
	}

	public int getStory(){
		return story;
	}
}
