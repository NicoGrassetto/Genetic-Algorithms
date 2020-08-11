

public class Individual {
	/* 🔒 An individual is defined by its chromosome.
	 * The chromosome in a genetic algorithm will hold the information about the solution.
	 * This is somehow a representation of the solution saying how good it is.
	 * In the same fashion the fitness function describes how good the solution is.
	 * Usually a number between 0 and 1. You might want to normalise it for some problems 
	 * as it is not always possible to get a number between 0 and 1.
	*/
	private int[] chromosome;
	private double fitness;
	/**
	 * 🔨 Build an individual.
	 * @param chromosome the chromosome representing a solution.
	 */
	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
		this.fitness = -1;
	}
	
	/**
	 * 🔨 Build an individual made of a random chromosome. 
	 * Thus, a random solution to the previously defined problem.
	 * @param chromosomeLength the length of the chromosome.
	 */
	public Individual(int chromosomeLength) {
		//Here the way we randomly initialise our chromosome
		//is really problem specific and might (actually is likely to be)
		// different in other potential applications.
		this.chromosome = new int[chromosomeLength];
		this.fitness = -1;
		for(int i = 0; i < chromosomeLength; i++) {
			if(0.5 < Math.random()) {
				this.setGene(i, 1);
			}else {
				this.setGene(i, 0);
			}
		}
	}
	
	/**
	 * 📌 Return the individual's chromosome.
	 * @return
	 */
	public int[] getChromosome() {
		return this.chromosome;
	}
	/**
	 * 📌 Return the individual's chromosome's length.
	 * @return
	 */
	public int getChromosomeLength() {
		return this.chromosome.length;
	}
	/**
	 * 📌 Set a gene (in genetic a gene is a chromosome section) to a new one.
	 * @param offset the index of the gene to replace
	 * @param gene the new gene 
	 */
	public void setGene(int offset, int gene) {
		//Again this method is really specific to our
		//problem and might hold strings or even objects for
		//most complex cases.
		this.chromosome[offset] = gene;
	}
	
	/**
	 * 📌 Set the individual's fitness.
	 * @param fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	/**
	 * Return the individual's fitness.
	 * @return double fitness - the individual's fitness value.
	 */
	public double getFitness() {
		return this.fitness;
	}
	
	/**
	 * 📺 A nice way to visualise what does our individual look like.
	 */
	public String toString() {
		String output = "";
		for(int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene];
		}
		return output;
	}
}
