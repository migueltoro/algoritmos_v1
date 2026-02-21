package us.lsi.sa;

import java.util.List;
import java.util.Random;

import us.lsi.ag.agchromosomes.AChromosome;

public class AlgoritmoSA<V,S>{
	
	public static <V, S> AlgoritmoSA<V, S> of() {
		return new AlgoritmoSA<V, S>(1000.0, 0.001, 0.95, 100);
	}
	
	public static <V,S> AlgoritmoSA<V,S> of(
			double initialTemperature,
			double minTemperature, 
			double coolingRate, 
			int iterationsPerTemp) {
		return new AlgoritmoSA<V,S>(initialTemperature, 
				minTemperature, coolingRate, 
				iterationsPerTemp);
	}
	
	private final double initialTemperature;
	private final double minTemperature;
	private final double coolingRate;
	private final int iterationsPerTemp;
	private final Random random = new Random();
	private AChromosome<V,?,S> best = null;

	protected AlgoritmoSA(double initialTemperature, 
			double minTemperature, 
			double coolingRate,
			int iterationsPerTemp) {
		this.initialTemperature = initialTemperature;
		this.minTemperature = minTemperature;
		this.coolingRate = coolingRate;
		this.iterationsPerTemp = iterationsPerTemp;
	}
	
	public AChromosome<V,?,S> run(List<AChromosome<V,?,S>> initials) {
		for (AChromosome<V,?,S> initial : initials) {
			AChromosome<V,?,S> result = run(initial);
			if (this.best == null || result.fitness() > this.best.fitness()) {
				this.best = result.deepCopy();
			}
		}
		return this.best;		
	}

	public AChromosome<V,?,S> run(AChromosome<V,?,S> initial) {
		AChromosome<V,?,S> current = initial.deepCopy();
		double temperature = initialTemperature;
		while (temperature > minTemperature) {
			for (int i = 0; i < iterationsPerTemp; i++) {
				AChromosome<V,?,S> neighbor = current.deepCopy();
				neighbor = neighbor.mutate(); // vecindad = mutación
				double currentFitness = current.fitness();
				double neighborFitness = neighbor.fitness();
				if (acceptanceProbability(currentFitness, neighborFitness, temperature) > random.nextDouble()) {
					current = neighbor;
				}
				if (this.best == null || current.fitness() > best.fitness()) {
					this.best = current.deepCopy();
				}
			}
			temperature *= coolingRate;
		}
		return this.best;
	}

	private double acceptanceProbability(double currentFit, double newFit, double temp) {
		if (newFit > currentFit) {
			return 1.0;
		}
		return Math.exp((newFit - currentFit) / temp);
	}
	
	
}

