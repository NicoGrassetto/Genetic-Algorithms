

public class GeneticAlgorithm {
	
	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	private int tournamentSize;
	
	/**
	 * 📌 Initialise a genetic algorithm.
	 * @param int populationSize - The population size.
	 * @param double mutationRate -  The  mutation rate at each generation.
	 * @param double crossoverRate - The crossover rate.
	 * @param int elitismCount - The number describing the elitism. 
	 */
	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}
	
	
	
	public Population initialisePopulation(int chromosomeLength) {
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
	}
	
	/**
	 * Compute the fitness of an individual.
	 * @param individual An individual.
	 * @return the individual's fitness.
	 */
	public double computeFitness(Individual individual) {
		int correctGenes = 0;
		
		for(int i = 0; i < individual.getChromosomeLength(); i++) {
			if(individual.getChromosome()[i] == 1) {
				//Again... really problem specific.
				//What is considered as a correct gene is likely to be different
				//for each problem.
				correctGenes += 1;
			}
		}
		double fitness = (double)correctGenes/individual.getChromosomeLength();
		
		individual.setFitness(fitness);
		return fitness;
	}
	
	public void evaluatePopulation(Population population) {
		double populationFitness = 0;
		
		for(Individual ind : population.getIndividuals()) {
			populationFitness += computeFitness(ind);
		}
		
		population.setPopulationFitness(populationFitness);
	}
	
	public boolean isTerminationConditionMet(int generationCount, int maxGenerations) {
		return (generationCount > maxGenerations);
	}
	
	public Individual selectParent(Population population) {
		Individual[] individuals = population.getIndividuals();
		
		//Spin roulette wheel 
		double populationFitness = population.getPopulationFitness();
		double rouletteWheelPosition = Math.random()*populationFitness;
		//Find parent
		double spinWheel = 0;
		
		for(Individual ind : individuals) {
			spinWheel += ind.getFitness();
			
			if(spinWheel >= rouletteWheelPosition) {
				return ind;
			}
		}
		return individuals[population.size() - 1];
		
	}
	
	public Population crossoverPopulation(Population population) {
		//Create a new population.
		Population newPopulation = new Population(population.size());
		
		//Loop over current population by fitness.
		for(int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);
			
			//Apply crossover to this individual?
			if(this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
				//Initialise offspring
				Individual offspring = new Individual(parent1.getChromosomeLength());
				
				//Find second parent
				
				Individual parent2 = selectParent(population);
				
				//Loop over genome 
				
				for(int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
					//Use half of parent1's genes and half of parent2's genes
					
					if(0.5 > Math.random()) {
					}else {
						offspring.setGene(geneIndex, parent2.getChromosome()[geneIndex]);
					}
				}
				
				//Add offspring to new population
				newPopulation.setIndividual(populationIndex, offspring);
			}else {
				// Add individual to new population without applying crossover
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}
		
		return newPopulation;
	}
	
	public Population mutatePopulation(Population population) {
		
		//Initialise new population
		Population newPopulation = new Population(this.populationSize);
		
		//Loop over current population by fitness.
		for(int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual ind = population.getFittest(populationIndex);
			
			//Loop over individual's genes.
			for(int i = 0; i < ind.getChromosomeLength(); i++) {
				//Skip mutation if this is an elite individual.
				if(populationIndex >= this.elitismCount) {
					//Does this gene need mutation?
					if(this.mutationRate > Math.random()) {
						int newGene = 1;
						if(ind.getChromosome()[i] == 1) {
							newGene = 0;
						}
						//Mutate gene
						ind.setGene(i, newGene);
					}
					
					
				}
			}
			newPopulation.setIndividual(populationIndex, ind);
		}
		//return mutated population
		return newPopulation;
	}
}
