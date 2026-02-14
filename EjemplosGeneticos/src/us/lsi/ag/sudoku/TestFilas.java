package us.lsi.ag.sudoku;

import java.util.List;


import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;


public class TestFilas {
	
	public static void main(String[] args) {
		
		int[][] puzzle = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
				{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

		AlgoritmoAG.ELITISM_RATE  = 0.3;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 300;
		
		// Condiciones de parada
		AlgoritmoAG.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
//		SudokuFilas g = SudokuFilas.initial(puzzle);
//		SudokuFilas g = SudokuFilas.ofFilas("ficheros/sudoku/sudoku_filas.txt");
		SudokuFilas g = SudokuFilas.of("ficheros/sudoku/sudoku4.txt");
		
		AlgoritmoAG<List<Integer>, SudokuFilas> ap = AlgoritmoAG.of(g);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");

		AChromosome<List<Integer>, ?, SudokuFilas> cr = ap.getBestAChromosome();
		System.out.println(cr.fitness());
		System.out.println(ap.bestSolution());
	}

}
