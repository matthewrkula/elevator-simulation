package com.mattkula.se350.elevatorsimulator.exceptions;

/**
 * This is a custom exception class that is to be thrown when a argument to a method
 * does not match the method requirements. 
 * @author Matt
 *
 */

@SuppressWarnings("serial")
public class InvalidArgumentException extends Exception {
	
	public InvalidArgumentException(String s){
		super(s);
	}

}
