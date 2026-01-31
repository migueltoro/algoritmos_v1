package us.lsi.geneticos.hittingset.paco;

import java.util.List;
import java.util.stream.IntStream;
import us.lsi.ag.Distances;

public class MetodosFitness {
	
	public static double totalElementos(List<Integer> cr) {
		return cr.stream().mapToInt(j->j).sum();
	}
	
	public static double totalNoIntersec(List<Integer> cr) {
		return IntStream.range(0, Datos.getNumSubconjuntos())
		.mapToDouble(i->Distances.distanceToGeZero(conAlmenosUno(i, cr))).sum();
	}

	private static double conAlmenosUno(int i, List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.filter(j->cr.get(j)>0 && Datos.contieneElemento(i, j)>0).count()-1.;
	}
}