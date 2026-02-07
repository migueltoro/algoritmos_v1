package us.lsi.ag.sudoku.manual;

import java.util.Optional;

import us.lsi.ag.manual.GeneticAlgorithm;

public class ALgSudokuGenetico extends GeneticAlgorithm<Sudoku> {

	public ALgSudokuGenetico() {
		super();
	}
	
	public ALgSudokuGenetico(int POP_SIZE,
			int MAX_GEN,
			double PROB_CROSS,
			double PROB_MUT) {
		super();
		GeneticAlgorithm.POP_SIZE = POP_SIZE;
		GeneticAlgorithm.MAX_GEN = MAX_GEN;
		GeneticAlgorithm.PROB_CROSS = PROB_CROSS;
		GeneticAlgorithm.PROB_MUT = PROB_MUT;
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

       ALgSudokuGenetico alg = new ALgSudokuGenetico();
//       Sudoku gs = Sudoku.initial(puzzle);
       Sudoku gs = Sudoku.ofFilas("ficheros/sudoku_filas.txt");
 //      Sudoku gs = Sudoku.of("ficheros/sudoku2.txt");
        System.out.println("Puzzle inicial:\n" +gs);
        Optional<Sudoku> solucion = alg.solve(gs);
        if(solucion.isPresent())
        	System.out.println("Mejor en genetics encontrado:\n" + solucion);
//        Sudoku initial = Sudoku.of(puzzle); // tu implementación 
		SimulatedAnnealingSudoku sa = new SimulatedAnnealingSudoku(1000.0,0.001,0.95,100);
		Sudoku result = sa.run(alg.best()); 
		System.out.println("Mejor en simulated annealing encontrado: " + result); 
    }



}
