package us.lsi.geneticos.hittingset.paco;

import java.util.List;
import java.util.stream.IntStream;

public class Solucion {
	
	public static Solucion create(List<Integer> ls) {
		return new Solucion(ls);
	}
	
	private List<Integer> seleccion;	

	private Solucion(List<Integer> ls) {
		seleccion = IntStream.range(0, ls.size())
		.filter(j->ls.get(j)>0).mapToObj(j->Datos.getUniverso().get(j)).toList();
	}

	@Override
	public String toString() {
		return String.format("Elementos seleccionados: %s", seleccion);	
	}
}