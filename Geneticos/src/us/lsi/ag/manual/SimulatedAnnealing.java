package us.lsi.ag.manual;

import java.util.List;
import java.util.Random;

public class SimulatedAnnealing<E extends Cromosoma<E>>{
	
	public static <E extends Cromosoma<E>> SimulatedAnnealing<E> of(
			double initialTemperature,
			double minTemperature, 
			double coolingRate, 
			int iterationsPerTemp) {
		return new SimulatedAnnealing<E>(initialTemperature, 
				minTemperature, coolingRate, 
				iterationsPerTemp);
	}

	
	private final double initialTemperature;
	private final double minTemperature;
	private final double coolingRate;
	private final int iterationsPerTemp;
	private final Random random = new Random();

	protected SimulatedAnnealing(double initialTemperature, 
			double minTemperature, 
			double coolingRate,
			int iterationsPerTemp) {
		this.initialTemperature = initialTemperature;
		this.minTemperature = minTemperature;
		this.coolingRate = coolingRate;
		this.iterationsPerTemp = iterationsPerTemp;
	}
	
	public E run(List<E> initials) {
		E best = null;
		for (E initial : initials) {
			E result = run(initial);
			if (best == null || result.fitness() > best.fitness()) {
				best = result.deepCopy();
			}
		}
		return best;		
	}

	public E run(E initial) {
		E current = initial.deepCopy();
		E best = current.deepCopy();
		double temperature = initialTemperature;
		while (temperature > minTemperature) {
			for (int i = 0; i < iterationsPerTemp; i++) {
				E neighbor = current.deepCopy();
				neighbor.mutate(); // vecindad = mutación
				double currentFitness = current.fitness();
				double neighborFitness = neighbor.fitness();
				if (acceptanceProbability(currentFitness, neighborFitness, temperature) > random.nextDouble()) {
					current = neighbor;
				}
				if (current.fitness() > best.fitness()) {
					best = current.deepCopy();
				}
			}
			temperature *= coolingRate;
		}
		return best;
	}

	private double acceptanceProbability(double currentFit, double newFit, double temp) {
		if (newFit > currentFit) {
			return 1.0;
		}
		return Math.exp((newFit - currentFit) / temp);
	}
	
	
}
