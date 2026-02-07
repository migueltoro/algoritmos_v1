package us.lsi.ag.sudoku.manual;

import us.lsi.ag.manual.SimulatedAnnealing;

public class SimulatedAnnealingSudoku extends SimulatedAnnealing<Sudoku> {

	public SimulatedAnnealingSudoku(
			double initialTemperature, 
			double minTemperature, 
			double coolingRate,
			int iterationsPerTemp) {
		super(initialTemperature, minTemperature, coolingRate, iterationsPerTemp);
	}
	
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
	    Sudoku gs = Sudoku.initial(puzzle).generateIndividual();
		SimulatedAnnealingSudoku sa = new SimulatedAnnealingSudoku(1000.0,0.001,0.95,100);
		Sudoku result = sa.run(gs); 
		System.out.println("Mejor encontrado: " + result); 
	}
}

