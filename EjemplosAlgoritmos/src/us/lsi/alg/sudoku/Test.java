package us.lsi.alg.sudoku;

import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class Test {

	public static void main(String[] args) {
		int[][] puzzle = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
				{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

//		Sudoku g = Sudoku.of(puzzle);
//		Sudoku g = Sudoku.ofFilas("ficheros/sudoku/sudoku_filas.txt");
		Sudoku g = Sudoku.of("ficheros/sudoku/sudoku3.txt");
		
		SudokuVertex e1 = SudokuVertex.of(g,g.nextCasilla());
		
		EGraph<SudokuVertex,SudokuEdge> graph = 
				EGraph.virtual(e1)
				.pathType(PathType.Last)
				.type(Type.One)
				.vertexWeight(v->v.sd().conflictos().doubleValue())
				.build();

		BT<SudokuVertex,SudokuEdge,SudokuSolucion> ms = 
				BT.of(graph,SudokuSolucion::of,null,null,false);

		Optional<GraphPath<SudokuVertex,SudokuEdge>> gp = 
				ms.search();
		System.out.println(SudokuSolucion.of(gp.get()));
//		ms.getSolutions().stream().forEach(s->System.out.println(s));
//		System.out.println(ms.getSolutions().size());

	}

}
