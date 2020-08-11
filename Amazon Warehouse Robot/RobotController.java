
public class RobotController {
	public static final int MAX_GENERATION = 1000;
	
	/*
	 * 0 = Empty
	 * 1 = Wall
	 * 2 = Starting position
	 * 3 = Route
	 * 4 = Goal position
	 */
	public static final int[][] MAZE_ARRAY = {
			{0, 0, 0, 0, 1, 0, 1, 3, 2},
			{1, 0, 1, 1, 1, 0, 1, 3, 1},
			{1, 0, 0, 1, 3, 3, 3, 3, 1},
			{3, 3, 3, 1, 3, 1, 1, 0, 1},
			{3, 1, 3, 3, 3, 1, 1, 0, 0},
			{3, 3, 1, 1, 1, 1, 0, 1, 1},
			{1, 3, 0, 1, 3, 3, 3, 3, 3},
			{0, 3, 1, 1, 3, 1, 0, 1, 3},
			{1, 3, 3, 3, 3, 1, 1, 1, 4}
	};
	public static void main(String[]args) {
		
		Maze maze = new Maze(MAZE_ARRAY);
		//Create genetic algorithm
		GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.05, 0.9, 2, 10);
		Population pop = ga.initialisePopulation(128);
		//Evaluate population
		ga.evaluatePopulation(pop, maze);
		int generation = 1;
		//Start evolution loop
		while(ga.isTerminationConditionMet(generation, maxGenerations) == false) {
			//print fittest individual from population
			Individual fittest = pop.getFittest(0);
			System.out.println("G " + generation + " Best solution " + fittest.getFitness() + " " + fittest.toString());
			ga.evalPopulation(pop, maze);
			
			//increment the current generation
			generation++;
		}
		
		System.out.println("Stopped after " + maxGenerations + " generations.");
		Individual fittest = pop.getFittest(0);
		System.out.println("Best solution")
	}
}
