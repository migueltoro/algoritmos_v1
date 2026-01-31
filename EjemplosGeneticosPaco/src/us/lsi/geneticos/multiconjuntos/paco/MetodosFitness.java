package us.lsi.geneticos.multiconjuntos.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {
	
	public static double totalElementos(List<Integer> cr) {
		return cr.stream().mapToDouble(i->i).sum();
	}
	
	public static double sumaTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.mapToDouble(i -> cr.get(i)*Datos.getElemento(i)).sum();
	}

}
