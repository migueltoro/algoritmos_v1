package us.lsi.ag.sudoku.manual;

public class AlgoritmoSudokuGenetico {
	
	static final int POP_SIZE = 200;
    static final int MAX_GEN = 15000;
    static final double PROB_CROSS = 0.8;
    static final double PROB_MUT = 0.3;
	
	// ============================
    // Algoritmo Genético principal
    // ============================
    public static Sudoku solve(Sudoku initial) {

        PoblacionSudoku poblacion = PoblacionSudoku.of();
        for (int i = 0; i < POP_SIZE; i++)
            poblacion.add(initial.generarIndividuo());

        for (int gen = 0; gen < MAX_GEN; gen++) {

            // Evaluar mejor individuo (elitismo)
        	Sudoku mejor = null;
            int bestFit = Integer.MAX_VALUE;

            for (Sudoku ind : poblacion.individuals()) {
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
            PoblacionSudoku nueva = PoblacionSudoku.of();

            // ELITISMO: conservar el mejor individuo
            nueva.add(mejor.copy());

            // Rellenar el resto
            while (nueva.size() < POP_SIZE) {
            	Sudoku p1 = poblacion.tournament();
            	Sudoku p2 = poblacion.tournament();

            	Sudoku hijo;
                if (Sudoku.rand.nextDouble() < PROB_CROSS)
                    hijo = p1.crossover(p2);
                else {
                    hijo = p1.copy();
                }
                if (Sudoku.rand.nextDouble() < PROB_MUT)
                    hijo.mutate();

                hijo.repair();

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

        int[][] puzzle = {
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
        };

        Sudoku gs = Sudoku.of(puzzle);
        System.out.println("Puzzle inicial:\n" +gs);
        Sudoku solucion = solve(gs);
        System.out.println("Puzzle Solucion:\n" + solucion);
    }


}
