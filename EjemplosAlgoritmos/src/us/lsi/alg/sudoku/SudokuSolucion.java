package us.lsi.alg.sudoku;

import java.util.List;

import org.jgrapht.GraphPath;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public class SudokuSolucion {
	
	private Sudoku sd;
	
	public static SudokuSolucion of(SudokuVertex v) {
		return new SudokuSolucion(v.sd());
	}
	
	public static SudokuSolucion of(GraphPath<SudokuVertex,SudokuEdge> gp) {
		List<SudokuVertex> lv = gp.getVertexList();
		return new SudokuSolucion(lv.get(lv.size()-1).sd());
	}

	private SudokuSolucion(Sudoku sd) {
		super();
		this.sd = sd;
	}

	@Override
	public String toString() {
		return String.format("SudokuSolucion: %s", this.sd);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
