package com.mattkula.se350.elevatorsimulator.elevatorcontroller;



/**
 * A Factory class that builds DecisionDelegates for the Elevator Controller
 * 
 * @author Matt
 *
 */
public class ElevatorDecisionDelegateFactory {
	
	/**
	 * Builds the required ElevatorDecisionDelegate
	 * @param which - Which delegate which be created (1 for simple, 2 for improved)
	 * @return An ElevatorDecisionDelegate that matches the parameters
	 */
	public static ElevatorDecisionDelegate build(int which){
		
		switch(which){
		case ElevatorController.DEFAULT_DECISION_DELEGATE:
			return new SimpleElevatorDecisionDelegate();
		case ElevatorController.IMPROVED_DECISION_DELEGATE:
			return new ImprovedElevatorDecisionDelegate();
		}
		
		return new SimpleElevatorDecisionDelegate();
	}

}
