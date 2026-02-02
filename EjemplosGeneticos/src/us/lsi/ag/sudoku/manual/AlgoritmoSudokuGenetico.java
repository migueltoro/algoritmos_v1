package us.lsi.ag.sudoku.manual;

public class AlgoritmoSudokuGenetico {
	
	static final int POP_SIZE = 200;
    static final int MAX_GEN = 5000;
    static final double PROB_CROSS = 0.95;
    static final double PROB_MUT = 0.3;
    
    public Sudoku initial;
    
	
	public AlgoritmoSudokuGenetico() {
		super();
	}

	// ============================
    // Algoritmo Genético principal
    // ============================
    public Sudoku solve(Sudoku initial) {
    	
    	this.initial = initial;

        PoblacionSudoku poblacion = PoblacionSudoku.of();
        for (int i = 0; i < POP_SIZE; i++)
            poblacion.add(initial.generateIndividual());
        
        int ngr = 0;
        double error = 1.0;
        
        int bestFit = Integer.MAX_VALUE;
        int lastFit = -1;
        Sudoku mejor = poblacion.best();
        
        for (int gen = 0; gen < MAX_GEN; gen++) {

            // Evaluar mejor individuo (elitismo)
			mejor = poblacion.best();
			bestFit = mejor.fitness().intValue();            

            System.out.println("Generación " + gen + " Mejor fitness: " + bestFit);
            
            if (bestFit == 0) {
                System.out.println("Solución encontrada en generación " + gen);
                return mejor;
            }

            // Nueva población
            PoblacionSudoku nueva = PoblacionSudoku.of();

            // ELITISMO: conservar el mejor individuo
            nueva.add(mejor.deepCopy());

            // Rellenar el resto
            while (nueva.size() < POP_SIZE) {
            	
            	Sudoku p1 = poblacion.tournament();
            	Sudoku p2 = poblacion.tournament();

            	Sudoku[] hijos = new Sudoku[2];
                if (Sudoku.rand.nextDouble() < PROB_CROSS)
                    hijos = p1.crossover(p2);
                else {
                    hijos[0] = p1.deepCopy();
                    hijos[1] = p2.deepCopy();
                }
                if (Sudoku.rand.nextDouble() < PROB_MUT) {               
                	hijos[0] = hijos[0].mutate();
                	hijos[1] = hijos[1].mutate();
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
        return mejor;
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

        AlgoritmoSudokuGenetico alg = new AlgoritmoSudokuGenetico();
//        Sudoku gs = Sudoku.initial(puzzle);
       Sudoku gs = Sudoku.ofFilas("ficheros/sudoku_filas.txt");
//        Sudoku gs = Sudoku.of("ficheros/sudoku1.txt");
        System.out.println("Puzzle inicial:\n" +gs);
        Sudoku solucion = alg.solve(gs);
        System.out.println("Puzzle Solucion:\n" + solucion);
    }


}
