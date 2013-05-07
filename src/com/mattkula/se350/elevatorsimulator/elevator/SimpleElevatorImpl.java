package com.mattkula.se350.elevatorsimulator.elevator;

import java.util.ArrayList;
import java.util.Collections;

import com.mattkula.se350.elevatorsimulator.building.Building;
import com.mattkula.se350.elevatorsimulator.building.FloorManager;
import com.mattkula.se350.elevatorsimulator.exceptions.InvalidArgumentException;
import com.mattkula.se350.elevatorsimulator.person.Person;

/**
 * An implementation of the Elevator interface that creates a simple elevator.
 * 
 * @author Matt
 * @since Version 1.0
 */
public class SimpleElevatorImpl implements Elevator{
	
	/**
	 * The number of millisecond it takes for the elevator doors to open
	 * @see #openDoors()
	 */
	private final int msToOpen = 1000;
	
	/**
	 * The number of millisecond it takes for the elevator doors to close.
	 * @see #closeDoors()
	 */
	private final int msToClose = 1000;
	
	/**
	 * The number of milliseconds it takes for the elevator to move up/down one floor.
	 * @see #moveToNextDest()
	 */
	private final int msPerFloor = 1000;
	
	/**
	 * The number of milliseconds before an elevator with no more destinations returns
	 * to its default floor.
	 */
	private final int msBeforeTimeout = 3000;
	
	/**
	 * The elevator's identification number in the building, used to reference each individual one.
	 * @see #getElevatorNumber()
	 */
	private int elevatorNumber;
	
	/**
	 * The ArrayList<Integer> "destinationList" contains the pending requests that this elevator
	 * still has to perform. Each integer is a floor number.
	 * @see #getRemainingDestinations()
	 */
	private ArrayList<Integer> destinationList;
	
	/**
	 * The ArrayList<Integer> "peopleInElevator" contains zero to "maxCapacity" number of Person 
	 * instances.
	 */
	private ArrayList<Person> peopleInElevator;
	
	
	/**
	 * The maximum carrying capacity of this elevator.
	 * @see #getMaxCapacity()
	 * @see #setMaxCapacity(int)
	 */
	private int maxCapacity;
	
	/**
	 * The floor that this elevator is currently at, whether passing or stopping. 
	 * @see #moveToNextDest()
	 * @see #setCurrentFloor(int)
	 * @see #getCurrentFloor()
	 */
	private int currentFloor;
	
	/**
	 * The floor that this elevator is to return to after timing out with no destinations in its
	 * pending requests. 
	 * @see #setDefaultFloor(int)
	 * @see #getDefaultFloor()
	 */
	private int defaultFloor;
	
	private Elevator.Status status;
	
	/**
	 * Holds instance of FloorManager for easy access.
	 */
	private FloorManager floorManager;
	
	public SimpleElevatorImpl(int elevatorNum, int defaultFloor) throws InvalidArgumentException{
		elevatorNumber = elevatorNum;
		destinationList = new ArrayList<Integer>();
		peopleInElevator = new ArrayList<Person>();
		floorManager = FloorManager.getInstance();
		setMaxCapacity(10);
		setCurrentFloor(defaultFloor);
		setDefaultFloor(defaultFloor);
		setStatus(Elevator.Status.WAITING_DEFAULT);
	}
	
	public SimpleElevatorImpl(int elevatorNum) throws InvalidArgumentException{
		this(elevatorNum, 1);
	}

	/**
	 * Add a destination to the Elevator's pending requests. If the elevator is going up, only requests
	 * from levels above it's current position will be accepted. If the elevator is going down, only requests
	 * from levels below it will be accepted. Keeps all pending destinations sorted according to direction.
	 * @param floorNum - The story that is to be added to the elevator's pending destination list
	 */
	@Override
	public void addDestination(int floorNum) {
		
		if(getStatus() == Elevator.Status.MOVING_UP && floorNum < getCurrentFloor())
			return;
		
		if(getStatus() == Elevator.Status.MOVING_DOWN && floorNum > getCurrentFloor())
			return;
		
		if(getStatus() == Elevator.Status.WAITING || getStatus() == Elevator.Status.WAITING_DEFAULT){
			if(floorNum > currentFloor)
				setStatus(Elevator.Status.MOVING_UP);
			else if(floorNum < currentFloor)
				setStatus(Elevator.Status.MOVING_DOWN);
		}
		
		//TODO fix all below this
		destinationList.add(floorNum);
		if(status == Elevator.Status.MOVING_UP)
			Collections.sort(destinationList);
		else if(status == Elevator.Status.MOVING_DOWN){
			Collections.sort(destinationList);
			Collections.reverse(destinationList);
		}
	}
	
	/**
	 * Simulates Elevator moving to a floor. For each story that the elevator
	 * is away from its next distance, the elevator sleeps for a second to simulate
	 * the time it takes to move. It sets the elevator status depending on the 
	 * direction it is moving. After it arrives at the next destination, it then 
	 * calls arriveAtDestination() to make the person exchange at the current story.
	 * 
	 * @see #arriveAtDestination()
	 * @throws InterruptedException if sleeping in Threads is interrupted
	 */
	private void moveToNextDest() throws InterruptedException{
		
		while(getCurrentFloor() != destinationList.get(0)){ // While we are not at our next destination
			if(destinationList.get(0) > getCurrentFloor()){
				setStatus(Elevator.Status.MOVING_UP);
				Thread.sleep(msPerFloor);		// Simulate delay in moving up a floor
				
				System.out.printf("%s Elevator %d passing %d on way up to Floor %d. Remaining Destinations: %s\n", 
						Building.getTimeString(), elevatorNumber, currentFloor, destinationList.get(0), getRemainingDestinations());
				currentFloor++;
				
			}else if(destinationList.get(0) < getCurrentFloor()){
				setStatus(Elevator.Status.MOVING_DOWN);
				Thread.sleep(msPerFloor);		// Simulate delay in moving up a floor
				
				System.out.printf("%s Elevator %d passing %d on way down to Floor %d. Remaining Destinations: %s\n", 
						Building.getTimeString(), elevatorNumber, currentFloor, destinationList.get(0), getRemainingDestinations());
				currentFloor--;
			}
		}
		
		arriveAtDestination();		// Start exchange at destination
	}
	
	/**
	 * Method that takes care of all that should happen when an elevator arrives at its next destination.
	 * First it removes the floor from the destinationList, then opens the doors, let's people leave,
	 * let's those who were waiting inside, then closes the doors.
	 * 
	 * @see #openDoors() 
	 * @see #letPeopleOut(int)
	 * @see #letPeopleIn(int)
	 * @see #closeDoors()
	 * @param floorNum
	 */
	private void arriveAtDestination(){
		System.out.printf("%s Elevator %d arrived at Floor %d.\n", Building.getTimeString(), elevatorNumber, currentFloor);
		destinationList.remove(0);
		openDoors();
		letPeopleOut();
		letPeopleIn();
		closeDoors();
	}
	
	/**
	 * Delays the Thread for a certain number of milliseconds simulating the time
	 * it takes for elevator doors to open. Called upon arrival at each destination.
	 * 
	 * @see #msToOpen
	 */
	private void openDoors(){
		try{
			System.out.printf("%s Elevator %d opening doors at Floor %d...\n", Building.getTimeString(), elevatorNumber, currentFloor);
			setStatus(Elevator.Status.DOORS_OPENING);
			Thread.sleep(msToOpen);
			setStatus(Elevator.Status.DOORS_OPENED);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Delays the Thread for a certain number of milliseconds simulating the time
	 * it takes for elevator doors to close. Called upon arrival at each destination.
	 * 
	 * @see #msToClose
	 */
	private void closeDoors(){
		try{
			System.out.printf("%s Elevator %d closing doors at Floor %d. Remaining destinations are %s\n", 
					Building.getTimeString(), elevatorNumber, currentFloor, getRemainingDestinations());
			setStatus(Elevator.Status.DOORS_CLOSING);
			Thread.sleep(msToClose);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves people who are in the elevator that have the destination of the current floor
	 * to the floor.
	 */
	private void letPeopleOut(){
		//TODO Add people to floor, remove from elevator
//		for(Person person : peopleInElevator){
//			if(person.getDestination() == getCurrentFloor())
//				floorManager.addPersonToFloor(getCurrentFloor(), person);
//			
//		}
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves people who are on the current floor trying to get elsewhere onto the elevator.
	 */
	private void letPeopleIn(){
		//TODO Remove people from floor, add to elevator
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try{
			
		while(true){
				if(destinationList.size() == 0){	// If there are no more pending requests
					
					if(getCurrentFloor() != getDefaultFloor()){ // If the elevator is not on it's default floor
						synchronized(this){
							
							System.out.printf("%s Elevator %d waiting for timeout.\n", Building.getTimeString(), getElevatorNumber());
							setStatus(Elevator.Status.WAITING_DEFAULT);
							this.wait(msBeforeTimeout);	// Wait for timeout or new request
							
							if(destinationList.size() != 0) // If no new requests were added
								continue;						// restart the loop
							
							System.out.printf("%s Elevator %d timed out, returning to default floor.\n", Building.getTimeString(), getElevatorNumber());
							this.addDestination(getDefaultFloor());	// Return to the default floor
						}
					}
					
					if(getCurrentFloor() == getDefaultFloor()){ // If we are at the default floor
						System.out.printf("%s Elevator %d waiting for instructions.\n", Building.getTimeString(), getElevatorNumber());
						setStatus(Elevator.Status.WAITING_DEFAULT);
						synchronized(this){
							this.wait();	// Wait for a new request
						}
					}
					
				}				// If there are more pending requests
				else{	
					moveToNextDest();
				}
			
		}
		
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the story that the elevator is currently on whether stopped or passing.
	 * @return The elevator's current story
	 */
	public int getCurrentFloor(){
		return currentFloor;
	}
	
	/**
	 * A method that displays all of the pending destinations on the elevator.
	 * 
	 * @return A string containing all of the destinations still pending, useful for console output. 
	 * @see #destinationList
	 */
	public String getRemainingDestinations(){
		String s = "[";
		int destsLeft = destinationList.size();
		for(int i = 0; i < destsLeft - 1; i++){
			s += destinationList.get(i) + ", ";
		}
		
		if(destsLeft > 0){
			s += destinationList.get(destsLeft - 1);
		}
		return s + "]";
	}
	
	public int getElevatorNumber(){
		return elevatorNumber;
	}
	
	/**
	 * Gets the total number of people the elevator can hold.
	 * @return The elevator's maximum capacity.
	 */
	public int getMaxCapacity(){
		return maxCapacity;
	}
	
	/**
	 * Gets the floor which an idle elevator will return to after timing out.
	 * @return The elevator's default floor.
	 */
	public int getDefaultFloor(){
		return defaultFloor;
	}
	
	public Elevator.Status getStatus(){
		return status;
	}
	
	private void setStatus(Elevator.Status newStatus){
		this.status = newStatus;
	}
	
	private void setMaxCapacity(int capacity) throws InvalidArgumentException{
		if(capacity < 1)
			throw new InvalidArgumentException("Elevator cannot hold any people");
		
		this.maxCapacity = capacity;
	}
	
	private void setDefaultFloor(int floorNum) throws InvalidArgumentException{
		if(floorNum < 0 || floorNum > floorManager.getNumberOfFloors())
			throw new InvalidArgumentException("Elevator's default floor outside of building constraints.");
		
		this.defaultFloor = floorNum;
	}
	
	private void setCurrentFloor(int floorNum) throws InvalidArgumentException{
		if(floorNum < 0 || floorNum > floorManager.getNumberOfFloors())
			throw new InvalidArgumentException("Elevator's current floor outside of building constraints.");
		
		this.currentFloor = floorNum;
	}

}
