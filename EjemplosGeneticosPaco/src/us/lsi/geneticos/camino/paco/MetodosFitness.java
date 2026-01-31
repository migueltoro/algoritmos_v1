package us.lsi.geneticos.camino.paco;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public class MetodosFitness {

	public static double pesoTotal(List<Integer> cr) {
		List<Integer> ls = List2.addFirst(cr, Datos.ORIGEN);
		ls.addLast(Datos.DESTINO);
		return IntStream.range(0, ls.size()-1)
			.mapToDouble(i->Datos.getPeso(ls.get(i), ls.get(i+1))).sum();
	}
	
	public static double predicadoVertices(List<Integer> cr) {
		return IntStream.range(0, cr.size())
			.filter(i->Datos.test(cr.get(i))).count();
	}
	
	public static double predicadoAristas(List<Integer> cr) {
		List<Integer> ls = List2.addFirst(cr, Datos.ORIGEN);
		ls.addLast(Datos.DESTINO);
		return IntStream.range(0, ls.size()-1)
			.filter(i->Datos.test(ls.get(i), ls.get(i+1))).count();
	}
	
}
