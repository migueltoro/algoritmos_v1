package us.lsi.geneticos.equipo.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {

	public static double rendimientoTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
			.mapToDouble(i->Datos.getRendimiento(cr.get(i), i)).sum();
	}
	
	public static double totalMalPosicion(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
			.filter(i->Datos.getRendimiento(cr.get(i), i)<Datos.UMBRAL).count();
	}
	
	public static double totalRepetidos(List<Integer> cr) {
		return Datos.getNumPosiciones() - cr.stream().distinct().count();
	}
}