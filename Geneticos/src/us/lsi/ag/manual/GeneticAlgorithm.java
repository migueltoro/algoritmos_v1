package us.lsi.ag.manual;

public class GeneticAlgorithm<E extends Cromosoma<E>> {
	
	static final int POP_SIZE = 200;
	static final int MAX_GEN = 15000;
	static final double PROB_CROSS = 0.8;
	static final double PROB_MUT = 0.3;
	public E initial;

	public GeneticAlgorithm() {
		super();
	}
	
	// ============================
    // Algoritmo Genético principal
    // ============================
    public E solve(E initial) {
    	this.initial = initial;
    	
        Poblacion<E> poblacion = initial.emptyPoblacion();
        for (int i = 0; i < POP_SIZE; i++)
            poblacion.add(initial.generateIndividual());

        for (int gen = 0; gen < MAX_GEN; gen++) {

            // Evaluar mejor individuo (elitismo)
        	E mejor = null;
            Integer bestFit = Integer.MAX_VALUE;

            for (E ind : poblacion.individuals()) {
                int fit = ind.fitness().intValue();
                if (fit < bestFit) {
                    bestFit = fit;
                    mejor = ind;
                }
            }

            System.out.println("Generación " + gen + " Mejor fitness: " + bestFit + "\n" +mejor);
            
            if (bestFit == 0) {
                System.out.println("Solución encontrada en generación " + gen);
                return mejor;
            }

            // Nueva población
            Poblacion<E> nueva = mejor.emptyPoblacion();

            // ELITISMO: conservar el mejor individuo
            nueva.add(mejor.deepCopy());

            // Rellenar el resto
            while (nueva.size() < POP_SIZE) {
            	E p1 = poblacion.tournament();
            	E p2 = poblacion.tournament();

            	E hijo;
                if (Cromosoma.rand.nextDouble() < PROB_CROSS)
                    hijo = p1.crossover(p2);
                else {
                    hijo = p1.deepCopy();
                }
                if (Cromosoma.rand.nextDouble() < PROB_MUT)
                    hijo = hijo.mutate();

                hijo = hijo.repair();

                nueva.add(hijo);
            }

            poblacion = nueva;
        }
        System.out.println("No se encontró solución");
        return null;
    }
    
 // ============================
    // Ejemplo de uso
    // ============================
    public static void main(String[] args) {

    }


}
