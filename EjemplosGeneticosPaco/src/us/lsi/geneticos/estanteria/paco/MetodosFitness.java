package us.lsi.geneticos.estanteria.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {
	
	public static double totalLibros(List<Integer> cr) {
		return cr.stream().filter(i->i<Datos.getNumEstantes()).count();
	}
	
	public static double totalMasAltura(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.filter(i -> cr.get(i)<Datos.getNumEstantes() && 
			Datos.getAlturaLibro(i)>Datos.getAlturaEstante(cr.get(i))).count();
	}
	
	public static double totalMasAnchura(List<Integer> cr) {
		int m = Datos.getNumEstantes();
		return IntStream.range(0, m)
		.mapToDouble(j -> anchoSobrante(cr, j)).sum();
	}

	private static double anchoSobrante(List<Integer> cr, int j) {
		int n = cr.size();
		double at = IntStream.range(0, n).filter(i->cr.get(i)==j)
			.mapToDouble(i->Datos.getAnchuraLibro(i)).sum();
		return Math.max(at-Datos.getAnchura(), 0);
	}

}
