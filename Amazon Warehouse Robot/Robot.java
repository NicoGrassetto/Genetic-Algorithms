import java.util.ArrayList;

public class Robot {
	
	private enum Direction{NORTH, EAST, SOUTH, WEST};
	private int xPosition;
	private int yPosition;
	private Direction heading;
	int maxMoves;
	int moves;
	private int sensorVal;
	private int[] sensorActions;
	private Maze maze;
	private ArrayList<int[]> route;
	
	public Robot(int[] sensorActions, Maze maze, int maxMoves) {
		this.sensorActions = this.calcSensorActions(sensorActions);
		this.maze = maze;
		int startPos[] = this.maze.getStartPosition();
		this.xPosition = startPos[0];
		this.yPosition = startPos[1];
		this.sensorVal = -1;
		this.heading = Direction.EAST;
		this.maxMoves = maxMoves;
		this.moves = 0;
		this.route = new ArrayList<int[]>();
		this.route.add(startPos);
	}
	
	/**
	 * Runs the robot's actions based on sensor inputs.
	 */
	public void run() {
		while(true) {
			this.moves++;
			
			//Break if the robot stops moving
			if(this.getNextAction() == 0) {
				return;
			}
			
			//Break if we reach the goal.
			if(this.maze.getPositionValue(this.xPosition, this.yPosition) == 4) {
				return;
			}
			
			//Break if we reach a maximum number of moves.
			if(this.moves > this.maxMoves) {
				return;
			}
			
			//Run action.
			this.makeNextAction();
		}
	}
	
	/**
	 * Map robot's sensor data to actions from binary string
	 * @param sensorActionsStr Binary GA chromosome
	 * @return int[] An array to map sensor value to an action
	 */
	private int[] calcSensorActions(int[] sensorActionsStr) {
		//How many actions are there?
		int numActions = (int) sensorActionsStr.length / 2;
		int[] sensorActions = new int[numActions];
		
		//Loop through actions
		for(int i = 0; i < numActions; i++) {
			//Get sensor action
			int sensorAction = 0;
			if(sensorActionsStr[(i*2) +1] == 1) {
				sensorAction += 2;
			}
			if(sensorActionsStr[(i*2) + 1] == 1) {
				sensorAction += 1;
			}
			
			//Add to sensor-action map
			sensorActions[i] = sensorAction;
		}
		
		return sensorActions;
	}
	
	public void makeNextAction() {
		//If move forward
		if(this.getNextAction() == 1) {
			int currentX = this.xPosition;
			int currentY = this.yPosition;
			
			if(Direction.NORTH == this.heading) {
				this.yPosition += -1;
				if(this.yPosition < 0) {
					this.yPosition = 0;
				}
			}
			else if(Direction.EAST == this.heading) {
				this.xPosition += 1;
				if(this.xPosition > this.maze.getMaxX()) {
					this.xPosition = this.maze.getMaxX();
				}
			}else if(Direction.SOUTH == this.heading) {
				this.yPosition += 1;
				if(this.yPosition > this.maze.getMaxY()) {
					this.yPosition = this.maze.getMaxY();
				}
			}else if(Direction.WEST == this.heading) {
				this.xPosition += -1;
				if(this.xPosition < 0) {
					this.xPosition = 0;
				}
			}
			if(this.maze.isWall(this.xPosition, this.yPosition) == true) {
				this.xPosition = currentX;
				this.yPosition = currentY;
			}else {
				if(currentX != this.xPosition || currentY != this.yPosition) {
					this.route.add(this.getPosition());
				}
			}
		}
		//Move clockwise
		else if(this.getNextAction() == 2) {
			if(Direction.NORTH == this.heading) {
				this.heading = Direction.EAST;
			}else if(Direction.EAST == this.heading) {
				this.heading = Direction.NORTH;
			}else if(Direction.SOUTH == this.heading) {
				this.heading = Direction.WEST;
			}else if(Direction.WEST == this.heading) {
				this.heading = Direction.NORTH;
			}
		}
		
		//Move anti-clockwise
		else if(this.getNextAction() == 3) {
			if(Direction.NORTH == this.heading) {
				this.heading = Direction.WEST;
			}else if(Direction.EAST == this.heading) {
				this.heading = Direction.NORTH;
			}else if(Direction.SOUTH == this.heading) {
				this.heading = Direction.EAST;
			}else if(Direction.WEST == this.heading) {
				this.heading = Direction.SOUTH;
			}
		}
		
		//Reset sensor value
		this.sensorVal = -1;
	}
	
	public int getNextAction() {
		return this.sensorActions[this.getSensorValue()];
	}
	
	public int getSensorValue() {
		if(this.sensorVal > -1) {
			return this.sensorVal;
		}
		
		boolean frontSensor, frontLeftSensor, frontRightSensor, leftSensor, rightSensor, backSensor;
		frontSensor = frontLeftSensor = frontRightSensor = leftSensor = rightSensor = backSensor = false;
		
		if(this.getHeading() == Direction.NORTH) {
			frontSensor = this.maze.isWall(this.xPosition, this.yPosition - 1);
			frontLeftSensor = this.maze.isWall(this.xPosition-1, this.yPosition-1);
			frontRightSensor = this.maze.isWall(this.xPosition+1, this.yPosition-1);
			leftSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
			rightSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
			backSensor = this.maze.isWall(this.xPosition, this.yPosition+1);
		}else if(this.getHeading() == Direction.EAST) {
			frontSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
			frontLeftSensor = this.maze.isWall(this.xPosition+1, this.yPosition-1);
			frontRightSensor = this.maze.isWall(this.xPosition+1, this.yPosition+1);
			leftSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
			rightSensor = this.maze.isWall(this.xPosition, this.yPosition+1);
			backSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
		}else if(this.getHeading() == Direction.SOUTH) {
			frontSensor = this.maze.isWall(this.xPosition, this.yPosition + 1);
			frontLeftSensor = this.maze.isWall(this.xPosition+1, this.yPosition+1);
			frontRightSensor = this.maze.isWall(this.xPosition-1, this.yPosition+1);
			leftSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
			rightSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
			backSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
		}else {
			frontSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
			frontLeftSensor = this.maze.isWall(this.xPosition-1, this.yPosition+1);
			frontRightSensor = this.maze.isWall(this.xPosition-1, this.yPosition-1);
			leftSensor = this.maze.isWall(this.xPosition, this.yPosition+1);
			rightSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
			backSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
		}
		
		//Calculate sensor value
		int sensoVal = 0;
		
		if(frontSensor == true) {
			sensorVal += 1;
		}
		
		if(frontLeftSensor == true) {
			sensorVal += 2;
		}
		
		if(frontRightSensor == true) {
			sensorVal += 4;
		}
		
		if(leftSensor == true) {
			sensorVal += 8;
		}
		
		if(rightSensor == true) {
			sensorVal += 16;
		}
		
		if(backSensor == true) {
			sensorVal += 32;
		}
		
		this.sensorVal = sensorVal;
		
		return sensorVal;
	}
	
	public int[] getPosition() {
		return new int[] {this.xPosition, this.yPosition};
	}
	
	private Direction getHeading() {
		return this.heading;
	}
	
	public ArrayList<int[]> getRoute(){
		return this.route;
	}
	
	public String printRoute() {
		String route = "";
		
		for(Object routeStep : this.route) {
			int[] step = (int[]) routeStep;
			route += "{" + step[0] + ", " + step[1] + "}";
		}
		return route;
	}
	
	public double calcFitness(Individual individual, Maze maze) {
		int[] chromosome = individual.getChromosome();
		Robot robot = new Robot(chromosome, maze, 100);
		robot.run();
		int fitness = maze.scoreRoute(robot.getRoute());
		individual.setFitness(fitness);
		return fitness;
	}
	
	public void evalPopulation(Population population, Maze maze) {
		double populationFitness = 0;
		
		for(Individual individual : population.getIndividuals()) {
			populationFitness += this.calcFitness(individual, maze);
		}
		
		population.setPopulationFitness(populationFitness);
	}
	
	public Individual selectParent(Population population) {
		
	}
}

