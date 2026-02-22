package us.lsi.alg.sudoku;

import us.lsi.graphs.virtual.VirtualVertex;

import java.util.List;
import us.lsi.alg.sudoku.Sudoku.Casilla;

public record SudokuVertex(Sudoku sd, Casilla c) 
		implements VirtualVertex<SudokuVertex, SudokuEdge, Integer>{
	
	public static SudokuVertex of(Sudoku sd, Casilla c) {
		return new SudokuVertex(sd, c);
	}
	
	@Override
	public List<Integer> actions() {
		if(this.sd.size == 0) return List.of();
		return this.sd.valoresLibresEnNextCasilla;
	}

	@Override
	public SudokuVertex neighbor(Integer a) {
		if(this.sd.size == 0) return this;
		Sudoku nsd = this.sd.setValor(this.c(), a);
		Casilla nc = nsd.nextCasilla();
		SudokuVertex sv = SudokuVertex.of(nsd,nc); 
		return sv;
	}

	@Override
	public SudokuEdge edge(Integer a) {
		return SudokuEdge.of(this, this.neighbor(a),a);
	}
	
	@Override
	public Boolean goal() {
		return this.sd.size == 0;
	}
	
	@Override
	public Boolean goalHasSolution() {
		return this.sd.conflictos() == 0;
	}
	
	@Override
	public String toString() {
		return String.format("Vertex: %sSize:%d, Casilla: %s, ValoresLibres: %s", 
				this.sd(), this.sd.size(),this.c(), this.sd.valoresLibresEnNextCasilla());
	}
	
	
	public static void main(String[] args) {
		int[][] puzzle = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
				{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

		System.out.println(Casilla.of(3, 0));
		Sudoku g = Sudoku.of(puzzle);
		SudokuVertex v = SudokuVertex.of(g,g.nextCasilla());
		System.out.println("Individuo inicial:" + "\n" + v);
	}

	// ============================
	// Ejemplo de uso
	// ============================    

}
