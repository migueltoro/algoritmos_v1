package us.lsi.p3.ej_2.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {
	public static double pesoTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.mapToDouble(i->cr.get(i)*Datos.getPeso(i)).sum();
	}
	public static double totalElementos(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).filter(i->cr.get(i)>0).boxed()
		.flatMap(i->Datos.getElementos(i).stream()).distinct().count();
	}
}
