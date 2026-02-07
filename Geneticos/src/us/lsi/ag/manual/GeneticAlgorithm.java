package us.lsi.ag.manual;

import java.util.Optional;

public class GeneticAlgorithm<E extends Cromosoma<E>> {
	
	public static int POP_SIZE = 200;
	public static int MAX_GEN = 3000;
	public static double PROB_CROSS = 0.8;
	public static double PROB_MUT = 0.3;
	E initial;
	E mejor;
	Poblacion<E> poblacion;

	public GeneticAlgorithm() {
		super();
	}
	
	public E best() {
		return mejor;
	}
	
	public Poblacion<E> poblacion() {
		return poblacion;
	}
	
	// ============================
    // Algoritmo Genético principal
    // ============================
	public Optional<E> solve(E initial) {

		Integer ngr = 0;
		Double error = 1.0;

		Integer bestFit = Integer.MAX_VALUE;
		int lastFit = -1;

		this.initial = initial;
		
		mejor = null;

		poblacion = initial.emptyPoblacion();
		for (int i = 0; i < POP_SIZE; i++)
			poblacion.add(initial.generateIndividual());

		for (int gen = 0; gen < MAX_GEN; gen++) {

			// Evaluar mejor individuo (elitismo)
			mejor = null;
			bestFit = Integer.MAX_VALUE;

			for (E ind : poblacion.individuals()) {
				int fit = ind.fitness().intValue();
				if (fit < bestFit) {
					bestFit = fit;
					mejor = ind;
				}
			}

			System.out.println("Generación " + gen + " Mejor fitness: " + bestFit + "\n" + mejor);

			if (bestFit == 0) {
				System.out.println("Solución encontrada en generación " + gen);
				return Optional.of(mejor);
			}

			// Nueva población
			Poblacion<E> nueva = mejor.emptyPoblacion();

			// ELITISMO: conservar el mejor individuo
			nueva.add(mejor.deepCopy());

			// Rellenar el resto
			while (nueva.size() < POP_SIZE) {
				E p1 = poblacion.tournament();
				E p2 = poblacion.tournament();

				@SuppressWarnings("unchecked")
				E[] hijos = (E[]) new Cromosoma[2];
				if (Cromosoma.rand.nextDouble() < PROB_CROSS)
					hijos = p1.crossover(p2);
				else {
					hijos[0] = p1.deepCopy();
					hijos[1] = p2.deepCopy();
				}
				if (Cromosoma.rand.nextDouble() < PROB_MUT) {
					hijos[0] = hijos[0].mutate();
					hijos[0] = hijos[0].mutate();
				}
				hijos[0] = hijos[0].repair();
				hijos[1] = hijos[1].repair();

				nueva.add(hijos[0]);
				nueva.add(hijos[1]);
			}

			poblacion = nueva;

			if (Math.abs(lastFit - bestFit) < error) {
				ngr++;
			}
			if (ngr >= 20) {
				System.out.println("Reboot");
				poblacion = poblacion.reboot();
				ngr = 0;
			}
			lastFit = bestFit;
		}
		System.out.println(poblacion.dispersion());
		System.out.println("No se encontró solución: la mejor encontrada es:\n" + mejor);
		return Optional.empty();
	}

	// ============================
	// Ejemplo de uso
	// ============================
	public static void main(String[] args) {

	}

}
