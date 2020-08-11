
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Population {
	/*
	 * 🔒 A population is made of individuals...
	 * Hence we store them into an array (Could be anything as long as the data structure is sequential)
	 * We set by default the population's fitness to -1.
	 * This is not mandatory but it can be seen as good pratice.
	 */
	private Individual[] population;
	private double populationFitness = -1;

	/**
	 * 📌 Build a population. The population is initialised at random.
	 * @param populationSize the population's size.
	 */
	public Population(int populationSize) {
		this.population = new Individual[populationSize];
	}

	/**
	 * 📌 Build a population. The population is initialised at random.
	 * @param populationSize The population's size.
	 * @param chromosomeLength The chromosome's length.
	 */
	public Population(int populationSize, int chromosomeLength) {
		this.population = new Individual[populationSize];

		for(int individualCount = 0; individualCount < populationSize; individualCount++) {
			Individual individual  =  new Individual(chromosomeLength);
			this.population[individualCount] = individual;
		}
	}

	/**
	 * 📌 Return an array of individuals.
	 * @return Individual[] - an array of individuals.
	 */
	public Individual[] getIndividuals() {
		return this.population;
	}

	/**
	 * 📌 Return the population's fittest individual.
	 * @param offset
	 * @return
	 */
	public Individual getFittest(int offset) {
		Arrays.sort(this.population, new Comparator<Individual>() {
			public int compare(Individual o1, Individual o2) {
				if(o1.getFitness() > o2.getFitness()) {
					return -1;
				}else if(o1.getFitness() < o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});
		return this.population[offset];
	}

	/**
	 * 📌 Set the population's fitness.
	 * @param fitness
	 */
	public void setPopulationFitness(double fitness) {
		this.populationFitness = fitness;
	}

	/**
	 * 📌 Return the population's fitness.
	 * @return populationFitness - the population's fitness.
	 */
	public double getPopulationFitness() {
		return this.populationFitness;
	}

	/**
	 * 📌 Return the population's size.
	 * @return population.length - the population's size.
	 */
	public int size() {
		return this.population.length;
	}

	/**
	 * 📌 Set a population's individual to a new one.
	 * @param offset - The individual's index.
	 * @param individual - The new individual.
	 */
	public void setIndividual(int offset, Individual individual) {
		this.population[offset] = individual;
	}

	/**
	 * 📌 Return the individual at the given index.
	 * @param offset - the index of the individual.
	 * @return the individual.
	 */
	public Individual getIndividual(int offset) {
		return population[offset];
	}

	/**
	 * 📌 shuffle the population.
	 */
	public void shuffle() {
		Random rand = new Random();
		for(int i = population.length - 1; i > 0; i--) {
			//We take a random index within 0 and the iteration number.
			int index = rand.nextInt(i + 1);
			//We take an individual at random.
			Individual a = population[index];
			//We swap these individuals.
			population[index] = population[i];
			population[i] = a;
		}
	}
}
