package com.mattkula.se350.elevatorsimulator.person;

import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;

/**
 * Factory that creates Person implementations that best fit the parameters. 
 * 
 * @author Matt
 *
 */
public class PersonFactory {
	
	/**
	 * Creates Person implementations that best fit the parameters.
	 * @param id - Unique id of the Person instance to be generated
	 * @param dest - The destination of the new Person implementation
	 * @return Best fit implementation of an Person
	 * @throws InvalidArgumentException if the destination is not a valid floor
	 */
	public static Person build(int id, int dest) throws InvalidArgumentException{
		return new SimplePersonImpl(id, dest);
	}

}