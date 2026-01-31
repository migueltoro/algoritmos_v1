package us.lsi.geneticos.museo.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {

	public static double interesTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
			.filter(i -> cr.get(i)<Datos.getNumSalas())
			.mapToDouble(i->Datos.getInteres(i)).sum();
	}
	
	public static double totalEstilos(List<Integer> cr) {
		int m = Datos.getNumSalas();
		return IntStream.range(0, m).mapToDouble(j->estilos(j, cr)-1.).sum();
	}
	
	private static long estilos(int j, List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
			.filter(i -> cr.get(i)==j)
			.mapToObj(i->Datos.getEstilo(i)).distinct().count();
	}

	public static double totalSalas(List<Integer> cr) {
		int m = Datos.getNumSalas();
		return IntStream.range(0, m).mapToDouble(j->diferencia(j, cr)).sum();
	}
	
	private static long diferencia(int j, List<Integer> cr) {
		int n = cr.size();
		long nos = IntStream.range(0, n).filter(i -> cr.get(i)==j).count();
		return nos<Datos.getMinObras(j)? Datos.getMinObras(j) - nos:
			nos>Datos.getMaxObras(j)? nos-Datos.getMaxObras(j): 0;
	}
}