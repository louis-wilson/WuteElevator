/** 
* We build an elevator. Intialized it with the number of floors,
* then run it. 
*
* We assume that every non-edge floor has an up button and a down button, 
* and that the elevator has a button for each floor. 
*
* We count floors from 0 to a max, so set the lowest floor as index 0
* and increment until the top floor. 
*
* To activate a button, use the "toggle" method for the particular type 
* of button at the particular floor. 
**/
class Elevator {
	// We cache number of floors for cleaner code, but technically unnecessary
	static int numFloors;
	/*
	We set the buttons as integers instead of booleans with the intention
	of them being treated as 0 or 1, but to allow cleaner summing of demand
	in the methods. Therefore, 0 = button off, 1 = button on
	*/
	ArrayList<Integer> elevatorButton = new ArrayList<Integer>();
	/* 
	Note that the top floor has no up button, and the down floor
	has no down button, but we leave them in the data structure to 
	make the code cleaner (index == floor). We handle these edge cases
	using the toggle methods.
	*/
	ArrayList<Integer> upButton = new ArrayList<Ineteger>();
	ArrayList<Integer> downButton = new ArrayList<Integer>();
	int direction; // +1 or -1 - is the elevator going up or down?
	int currentFloor; // Where is the elevator currently?

	/*
	Initialize the elevator with the number of floors
	*/
	public Elevator(int NumFloors){
		 this.numFloors=numFloors;
		 direction = 1;
		 currentFloor = 0;
		 for(int i = 0; i<numFloors; i++){
		 	elevatorButton.add(0);
		 	upButton.add(0);
		 	downButton.add(0);
		}
	}

	/*
	Change the direction of the elevator. 
	*/
	public void flip(){
		direction = -direction;
	}	

	/*
	Have the elevator move in the current direction
	*/
	public void move(){
		currentFloor += direction;
	}	

	/* 
	Should the elevator continue moving in the current direction?
	Returns whether anyone needs to be picked up or dropped off
	in the current direction
	*/
	public boolean continueInDirection(){
		int sum = 0;
		for(int i=currentFloor; i>=0||i<numFloors; i += direction){
			sum += elevatorButton.get(i) + upButton.get(i) + downButton.get(i);
		}
		return sum>0;
	}

	/*
	Simulate opening the doors of the elevators at the current
	floor, and turn off the elevator button and the button 
	in the direction of the elevator. 

	Our logic assumes that new passengers click their destination floor before
	the method finishes (doors close), otherwise they could yield the priority in their direction.
	*/
	public void visit(int floor){
		elevatorButton.set(floor,0);
		if ( continueInDirection)
		if (direction==1){
			//upward direction
			upButton.set(floor,0)
		} else {
			downButton.set(floor,0)
		}
	}

	public boolean openRequests(){
		int sum = 0;
		for(int i=0; i<numFloors; i++){
			sum += elevatorButton.get(i) + upButton.get(i) + downButton.get(i);
		}
		return sum>0;
	}

	public void toggleUp(int floor){
		if(floor>=0 && floor<(numFloors-1)){
			upButton.set(floor, 1);
		}	
	}

	public void toggleDown(int floor){
		if(floor>0 && floor<numFloors){
			downButton.set(floor, 1);
		}	
	}

	public void toggleFloor(int floor){
		if(floor>0 && floor<numFloors){
			elevatorButton.set(floor, 1);
		}	
	}

	public void run(){
		while (true){
			self.visit(currentFloor);
			if (self.openRequests()){
				if (self.continueInDirection(){
					self.move();
				} 
				else {
					/* Why an else? We are dealing with the edge 
					case of a person clicking both the up and down
					buttons at a particular floor without actually boarding
					the elevator
					*/
					self.flip();
					self.visit();
				}
			}
		}
	}
}