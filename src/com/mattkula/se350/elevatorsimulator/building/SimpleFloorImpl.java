package com.mattkula.se350.elevatorsimulator.building;

import java.util.ArrayList;

import com.mattkula.se350.elevatorsimulator.person.Person;

public class SimpleFloorImpl implements Floor{
	
	int story;
	
	ArrayList<Person> people;
	
	public SimpleFloorImpl(int story){
		setStory(story);
	}
	
	public void addPerson(Person person){
		synchronized(this){
			people.add(person);
		}
	}
	
	private void setStory(int storyIn){
		story = storyIn;
	}

	public int getStory(){
		return story;
	}
}
