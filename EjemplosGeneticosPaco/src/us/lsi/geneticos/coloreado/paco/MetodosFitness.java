package us.lsi.geneticos.coloreado.paco;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public class MetodosFitness {
	
	public static double totalColores(List<Integer> cr) {
		return cr.stream().distinct().count();
	}
	
	public static double totalIncompatibles(List<Integer> cr) {
		int n = cr.size();
		Map<Integer,List<Integer>> m = IntStream.range(0, n).boxed().collect(Collectors.groupingBy(i-> cr.get(i)));
		return m.values().stream().mapToDouble(ls->Math.max(0., numVecinos(ls)-1.)).sum();
	}
	
	private static double numVecinos(List<Integer> ls) {
		return ls.stream().filter(v->!List2.intersection(ls, Datos.vecinos(v)).isEmpty()).count();
	}
	
}