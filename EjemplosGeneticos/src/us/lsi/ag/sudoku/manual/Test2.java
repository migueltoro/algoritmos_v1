package us.lsi.ag.sudoku.manual;

import us.lsi.ag.manual.GeneticAlgorithm;

public class Test2 {

	public static void main(String[] args) {
		int[][] puzzle = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
				{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

		GeneticAlgorithm.POP_SIZE = 200;
		GeneticAlgorithm.MAX_GEN = 5000;
		GeneticAlgorithm.PROB_CROSS = 0.8;
		GeneticAlgorithm.PROB_MUT = 0.3;

		GeneticAlgorithm<Sudoku> alg = new GeneticAlgorithm<Sudoku>();
		Sudoku gs = Sudoku.initial(puzzle).generateIndividual();
		System.out.println("Puzzle inicial:\n" + gs);
		System.out.println("Puzzle mutate:\n" + gs.mutate());
		System.out.println("Puzzle copy:\n" + gs.deepCopy());

	}

}
