package us.lsi.geneticos.inversioncapital.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {
	public static double valorTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.mapToDouble(i->cr.get(i)*Datos.getValor(i)).sum();
	}
	
	public static double totalInvertido(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.mapToDouble(i->cr.get(i)*Datos.getCantidad(i)).sum();
	}
	
	public static double numInversiones(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).filter(i->cr.get(i)>0).count();
	}
}