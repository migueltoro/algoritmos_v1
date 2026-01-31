package us.lsi.geneticos.sudoku.paco;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public class MetodosFitness {
	
	public static double totalFilas(List<List<Integer>> m) {
		return IntStream.range(0, Datos.DIM)
			.mapToDouble(i-> repetidos(m.get(i))).sum();
	}
	
	private static double repetidos(List<Integer> ls) {
		return Datos.DIM - Set.copyOf(ls).size();
	}	

	public static double totalColumnas(List<List<Integer>> m) {
		return IntStream.range(0, Datos.DIM)
			.mapToDouble(j-> repetidos(columna(j, m))).sum();
	}
	
	private static List<Integer> columna(int j, List<List<Integer>> m) {
		return IntStream.range(0, Datos.DIM)
			.mapToObj(i -> m.get(i).get(j)).toList();
	}

	public static double totalSubCuadrados(List<List<Integer>> m) {
		return IntStream.range(0, Datos.DIM)
			.mapToDouble(sc-> repetidos(subCuadrado(sc, m))).sum();
	}
	
	private static List<Integer> subCuadrado(int sc, List<List<Integer>> m) {
		List<Integer> ls = List2.empty();
		for(int i=0; i<Datos.DIM; i++) {
			for(int j=0; j<Datos.DIM; j++) {
				int n = Datos.subCuadrado(i*Datos.DIM + j);
				if(n==sc)
					ls.add(m.get(i).get(j));
			}
		}
		return ls;
	}	
	
}
