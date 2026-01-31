package us.lsi.p3.ej_3.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {
	
	public static double afinidadTotalR(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.mapToDouble(i->Datos.getAfinidad(i, cr.get(i))).sum();
	}
	
	public static double totalCeroAfinR(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.filter(i->Datos.getAfinidad(i, cr.get(i))<1).count();
	}
	
	public static double totalGruposR(List<Integer> cr) {
		int t = Datos.getTamGrupo();
		return IntStream.range(0, Datos.getNumGrupos())
		.mapToDouble(j->Math.abs(t-numAlumnosR(j, cr))).sum();
	}

	private static double numAlumnosR(int j, List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).filter(i->cr.get(i)==j).count();
	}

	public static double afinidadTotalP(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.mapToDouble(i->Datos.getAfinidad(cr.get(i), i/Datos.getTamGrupo())).sum();
	}
	
	public static double totalCeroAfinP(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.filter(i->Datos.getAfinidad(cr.get(i), i/Datos.getTamGrupo())<1).count();
	}
}
