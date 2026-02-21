package us.lsi.ag.sudoku.manual;


import java.util.Optional;

import us.lsi.ag.manual.GeneticAlgorithm;
import us.lsi.ag.manual.SimulatedAnnealing;


public class TestSudoku {

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
		 
		 
		 	GeneticAlgorithm.POP_SIZE = 200;
		 	GeneticAlgorithm.MAX_GEN = 5000;
			GeneticAlgorithm.PROB_CROSS = 0.8;
			GeneticAlgorithm.PROB_MUT = 0.3;

		       GeneticAlgorithm<Sudoku> alg = new GeneticAlgorithm<Sudoku>();
//		       Sudoku gs = Sudoku.initial(puzzle);
//		       Sudoku gs = Sudoku.ofFilas("ficheros/sudoku/sudoku_filas.txt");
		       Sudoku gs = Sudoku.of("ficheros/sudoku/sudoku5.txt");
		        System.out.println("Puzzle inicial:\n" +gs);
		        Optional<Sudoku> solucion = alg.solve(gs);
		        if(solucion.isPresent())
		        	System.out.println("Mejor en genetics encontrado:\n" + solucion);
//		        Sudoku initial = Sudoku.of(puzzle); // tu implementación 
//		        System.out.println("Puzzle mutate:\n" +gs.mutate());
		        SimulatedAnnealing<Sudoku> sa = 
//		        		SimulatedAnnealing.of(1000.0, 0.01, 0.95, 100);
		        		SimulatedAnnealing.of(1000.0, 0.01, 0.99, 100);
		        Sudoku saSolution = sa.run(alg.poblacion().bests(10));
		        System.out.println("Mejor en simulated annealing encontrado:\n" + 
		        		saSolution);
		    }
	}

