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
 * This class is an implementation of the Elevator interface. It responds to
 * requests to move up and down sent from outside sources. At each destination,
 * it takes time to open the doors, let people out and in, then close the doors.
 * If it spends too much time without a destination, it times out and returns to
 * whatever floor it considers the "default" floor.
 * 
 * @author Matt
 * @since Version 1.0
 */
public class SimpleElevatorImpl implements Elevator{
	
	/**
	 * The number of milliseconds it takes for the elevator to do its exchange at a floor.
	 * This exchange includes opening doors, adding people, removing people, and closing
	 * doors. 
	 * @see #arriveAtDestination()
	 */
	private int msDoorOperations;
	
	/**
	 * The number of milliseconds it takes for the elevator to move up/down one floor.
	 * Used to simulate the time it takes for elevator to move floors. 
	 * @see #moveToNextDest()
	 */
	private int msPerFloor;
	
	/**
	 * The number of milliseconds before an elevator with no more destinations returns.
	 * to its default floor.
	 * Value: {@value}
	 */
	public static final int msBeforeTimeout = 10000;
	
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
	
	/**
	 * Current Status of the elevator, held in an Enum
	 * @see #getStatus()
	 * @see #setStatus(com.mattkula.se350.elevatorsimulator.elevator.Elevator.Status)
	 */
	private Elevator.Status status;
	
	/**
	 * Holds instance of FloorManager for easy access.
	 */
	private FloorManager floorManager;
	
	/**
	 * Constructor that creates a new SimpleElevatorImplementation
	 * @param elevatorNum - The id/number of the elevator
	 * @param defaultFloor - The default floor of the elevator
	 * @param msPerFloor - The time it takes to travel one floor
	 * @param msDoorOperation - The time it takes for the elevator to exchange people at a floor
	 * @throws InvalidArgumentException if elevatorNum < 1 or defaultFloor is not a floor number
	 */
	public SimpleElevatorImpl(int elevatorNum, int defaultFloor, int msPerFloor, int msDoorOperation) throws InvalidArgumentException{
		floorManager = FloorManager.getInstance();
		
		if(elevatorNum < 1)
			throw new InvalidArgumentException("Elevator number cannot be less than one.");
		
		if(defaultFloor < 1 || defaultFloor > floorManager.getNumberOfFloors())
			throw new InvalidArgumentException("Default floor out of range.");
		
		setElevatorNum(elevatorNum);
		destinationList = new ArrayList<Integer>();
		peopleInElevator = new ArrayList<Person>();
		
		setMaxCapacity(10);
		setCurrentFloor(defaultFloor);
		setDefaultFloor(defaultFloor);
		setStatus(Elevator.Status.WAITING_DEFAULT);
		setMsDoorOperations(msDoorOperation);
		setMsPerFloor(msPerFloor);
	}

	/**
	 * Add a destination to the Elevator's pending requests. If the elevator is going up, only requests
	 * from levels above it's current position will be accepted. If the elevator is going down, only requests
	 * from levels below it will be accepted. Keeps all pending destinations sorted according to direction.
	 * @param floorNum - The story that is to be added to the elevator's pending destination list
	 * @throws InvalidArgumentException 
	 */
	@Override
	public void addDestination(int floorNum) throws InvalidArgumentException{
		
		if(floorNum < 1 || floorNum > FloorManager.getInstance().getNumberOfFloors())
			throw new InvalidArgumentException("Elevator being sent out of floor range.");
		
		if(getStatus() == Elevator.Status.MOVING_UP && floorNum < getCurrentFloor()){
			System.out.printf("%s Elevator %d request for %d not in direction of travel - ignoring\n", 
					Building.getTimeString(), getElevatorNumber(), floorNum);
			return;
		}
		
		if(getStatus() == Elevator.Status.MOVING_DOWN && floorNum > getCurrentFloor()){
			System.out.printf("%s Elevator %d request for %d not in direction of travel - ignoring\n", 
					Building.getTimeString(), getElevatorNumber(), floorNum);
			return;
		}
		
		if(getStatus() == Elevator.Status.WAITING || getStatus() == Elevator.Status.WAITING_DEFAULT){
			if(floorNum > currentFloor)
				setStatus(Elevator.Status.MOVING_UP);
			else if(floorNum < currentFloor)
				setStatus(Elevator.Status.MOVING_DOWN);
		}
		
		if(!destinationList.contains(floorNum))
			destinationList.add(floorNum);
		if(status == Elevator.Status.MOVING_UP)
			Collections.sort(destinationList);
		else{
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
	 * @throws InvalidArgumentException 
	 */
	private void moveToNextDest() throws InterruptedException, InvalidArgumentException{
		
		while(getCurrentFloor() != destinationList.get(0)){ // While we are not at our next destination
			if(destinationList.get(0) > getCurrentFloor()){
				setStatus(Elevator.Status.MOVING_UP);
				
				System.out.printf("%s Elevator %d passing %d on way up to Floor %d. Remaining Destinations: %s\n", 
						Building.getTimeString(), elevatorNumber, currentFloor, destinationList.get(0), getRemainingDestinations());
				currentFloor++;
				
				Thread.sleep(getMsPerFloor() / Building.getTimeScale());		// Simulate delay in moving up a floor
				
			}else if(destinationList.get(0) < getCurrentFloor()){
				setStatus(Elevator.Status.MOVING_DOWN);
				
				System.out.printf("%s Elevator %d passing %d on way down to Floor %d. Remaining Destinations: %s\n", 
						Building.getTimeString(), elevatorNumber, currentFloor, destinationList.get(0), getRemainingDestinations());
				currentFloor--;
				
				Thread.sleep(getMsPerFloor() / Building.getTimeScale());		// Simulate delay in moving up a floor
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
	 * @throws InvalidArgumentException 
	 */
	private void arriveAtDestination() throws InvalidArgumentException{
		System.out.printf("%s Elevator %d arrived at Floor %d.\n", Building.getTimeString(), elevatorNumber, currentFloor);
		destinationList.remove(0);
		if(destinationList.size() == 0){
			setStatus(Elevator.Status.WAITING);
		}
		doFloorExchange();
	}
	
	/**
	 * Called whenever an elevator arrives at a destination on its destination list. 
	 * Simulates the opening of doors, allows people to come in and out, then closes the doors.
	 * It sleeps for a specified number of seconds to simulate the time it takes for all of that
	 * to take place. 
	 * @throws InvalidArgumentException - for invalid data specified by the error message
	 */
	private void doFloorExchange() throws InvalidArgumentException{
		try{
			//Open the Doors
			System.out.printf("%s Elevator %d opening doors at Floor %d...\n", Building.getTimeString(), elevatorNumber, currentFloor);
			
			//Exchange of people
			letPeopleOut();
			letPeopleIn();
			
			System.out.printf("%s Elevator %d letting people exchange at Floor %d. People Left: %s\n", Building.getTimeString(), elevatorNumber, 
					currentFloor, getPeopleInElevator());
			//Simulates exchange time
			Thread.sleep(getMsDoorOperations() / Building.getTimeScale());
			
			//Close the doors
			System.out.printf("%s Elevator %d closing doors at Floor %d. Remaining destinations are %s\n", 
					Building.getTimeString(), elevatorNumber, currentFloor, getRemainingDestinations());
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves people who are in the elevator that have the destination of the current floor
	 * to the floor.
	 */
	private void letPeopleOut(){
		
		ArrayList<Person> peopleToRemove = new ArrayList<Person>();
		
		try{
			for(int i = 0; i < peopleInElevator.size(); i++){
				Person person = peopleInElevator.get(i);
				if(person.getDestination() == getCurrentFloor()){
					floorManager.addPersonToFloor(getCurrentFloor(), person);
					peopleToRemove.add(peopleInElevator.get(i));
				}
				
			}
			
			peopleInElevator.removeAll(peopleToRemove);
			
		}catch(InvalidArgumentException e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Moves people who are on the current floor trying to get elsewhere onto the elevator.
	 * @throws InvalidArgumentException 
	 */
	private void letPeopleIn() throws InvalidArgumentException{
		FloorManager.getInstance().addPeopleToElevator(getCurrentFloor(), this);
	}
	
	/**
	 * Adds a person to the instance of the elevator.
	 * @throws InvalidArgumentException if the destination is not a legal floor
	 */
	public boolean addPerson(Person p) throws InvalidArgumentException {
		
		if(peopleInElevator.size() < getMaxCapacity()){
			peopleInElevator.add(p);
			addDestination(p.getDestination());
			return true;
		}
		
		return false;
	}

	/**
	 * Contains the main logic loop of the elevator. Constantly runs
	 * checking if it has any destinations in the queue. If not, it waits
	 * until it is notified. After a certain amount of idle time, it will
	 * go back down to its default floor. 
	 */
	@Override
	public void run() {
		try{
			
		while(true){
				if(destinationList.size() == 0){	// If there are no more pending requests
					
					if(getCurrentFloor() != getDefaultFloor()){ // If the elevator is not on it's default floor
						synchronized(this){
							
							System.out.printf("%s Elevator %d waiting for timeout.\n", Building.getTimeString(), getElevatorNumber());
							setStatus(Elevator.Status.WAITING);
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
		
		}catch(InterruptedException | InvalidArgumentException e){
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
	
	/**
	 * Get a string containing ids of the Person objects in the elevator, useful for logging.
	 * @return A string containing the ids of the Person objects in the elevator.
	 */
	public String getPeopleInElevator(){
		String s = "[";
		int peopleLeft = peopleInElevator.size();
		for(int i = 0; i < peopleLeft - 1; i++){
			s += peopleInElevator.get(i).getId() + ", ";
		}
		
		if(peopleLeft > 0){
			s += peopleInElevator.get(peopleLeft - 1).getId();
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
	
	/**
	 * Get the current status of the elevator.
	 * @return The elevator's current status
	 */
	public Elevator.Status getStatus(){
		return status;
	}
	
	/**
	 * Gets the number of milliseconds it takes to open the doors, let people
	 * in and out, then close the doors. 
	 * @return The time the elevator takes at each destination floor
	 */
	public int getMsDoorOperations(){
		return msDoorOperations;
	}
	
	/**
	 * Gets the number of milliseconds it takes to move floors.
	 * @return The number of milliseconds it takes to move floors.
	 */
	public int getMsPerFloor(){
		return msPerFloor;
	}
	
	/**
	 * Set the number of the elevator instance.
	 * @param newStatus - The status of the elevator
	 */
	private void setElevatorNum(int num){
		elevatorNumber = num;
	}
	
	/**
	 * Set the status of the elevator instance.
	 * @param newStatus - The status of the elevator
	 */
	private void setStatus(Elevator.Status newStatus){
		this.status = newStatus;
	}
	
	/**
	 * Sets the maximum capacity of the elevator.
	 * @param capacity - The maximum capacity of the elevator
	 * @throws InvalidArgumentException if the capacity is less than 1.
	 */
	private void setMaxCapacity(int capacity) throws InvalidArgumentException{
		if(capacity < 1)
			throw new InvalidArgumentException("Elevator cannot hold any people");
		
		this.maxCapacity = capacity;
	}
	
	/**
	 * Sets the default floor of the elevator
	 * @param floorNum - The floor the elevator should return to after timing out
	 * @throws InvalidArgumentException if the floor < 0 or the floor > than the number of floors in the building.
	 */
	private void setDefaultFloor(int floorNum) throws InvalidArgumentException{
		if(floorNum < 0 || floorNum > floorManager.getNumberOfFloors())
			throw new InvalidArgumentException("Elevator's default floor outside of building constraints.");
		
		this.defaultFloor = floorNum;
	}
	
	/**
	 * Sets the current floor of the elevator, used only at initialization
	 * @param floorNum - Story to set the elevator to
	 * @throws InvalidArgumentException if the floor < 0 or the floor > than the number of floors in the building.
	 */
	private void setCurrentFloor(int floorNum) throws InvalidArgumentException{
		if(floorNum < 0 || floorNum > floorManager.getNumberOfFloors())
			throw new InvalidArgumentException("Elevator's current floor outside of building constraints.");
		
		this.currentFloor = floorNum;
	}
	
	/**
	 * Sets the time the elevator takes at each destination floor, used only at initialization. 
	 * @param newMsDoorOperations - The time to set the door operations speed to
	 * @throws InvalidArgumentException if the input is < 0
	 */
	private void setMsDoorOperations(int newMsDoorOperations) throws InvalidArgumentException{
		if(newMsDoorOperations < 0)
			throw new InvalidArgumentException("Elevator needs a positive door operations time.");
		
		msDoorOperations = newMsDoorOperations;
	}

	/**
	 * Sets the time the elevator takes to travel up/down one floor in milliseconds.
	 * @param newMsPerFloor - The time to set the elevator speed to.
	 * @throws InvalidArgumentException if the input is < 0
	 */
	private void setMsPerFloor(int newMsPerFloor) throws InvalidArgumentException{
		if(newMsPerFloor < 0)
			throw new InvalidArgumentException("Elevator needs a positive time per floor while moving.");
		
		msPerFloor = newMsPerFloor;
	}
}
