package us.lsi.alg.sudoku;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record SudokuEdge(SudokuVertex source, SudokuVertex target, Integer action, Double weight) 
        implements SimpleEdgeAction<SudokuVertex,Integer> {
	
	public static SudokuEdge of(SudokuVertex v1, SudokuVertex v2, Integer a) {	
		return new SudokuEdge(v1,v2, a, 1.0);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
