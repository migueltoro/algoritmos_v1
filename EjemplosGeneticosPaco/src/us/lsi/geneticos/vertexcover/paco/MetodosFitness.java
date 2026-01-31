package us.lsi.geneticos.vertexcover.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {

	public static double getPesoTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n)
		.mapToDouble(i->cr.get(i)/Datos.getHabitantes(i)).sum();
	}
	
	public static double aristasRestantes(List<Integer> cr) {
		int n = cr.size();
		return Datos.getNumAristas() - 
		IntStream.range(0, n).filter(i->cr.get(i)>0)
		.mapToObj(i->Datos.getAristas(i)).flatMap(s->s.stream())
		.distinct().count();
	}
}
