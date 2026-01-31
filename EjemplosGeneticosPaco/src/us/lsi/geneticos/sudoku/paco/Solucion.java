package us.lsi.geneticos.sudoku.paco;

import java.util.List;
import java.util.stream.Collectors;

public class Solucion {

	private List<List<Integer>> matriz;
	
	public static Solucion of(List<Integer> cr) {
		return new Solucion(cr);
	}
	
	private Solucion(List<Integer> cr) {
		matriz = Datos.montaMatriz(cr);
	}
	
	@Override
	public String toString() {
		return matriz.stream().map(f->filaToString(f))
		.collect(Collectors.joining("\n"));
	}
	
	private String filaToString(List<Integer> ls) {
		return ls.stream().map(e->String.format("%2d", e+1))
		.collect(Collectors.joining(" "));
	}

}