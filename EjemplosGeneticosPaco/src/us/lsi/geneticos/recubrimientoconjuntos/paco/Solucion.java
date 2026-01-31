package us.lsi.geneticos.recubrimientoconjuntos.paco;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.geneticos.recubrimientoconjuntos.paco.Datos.Subconjunto;

public class Solucion {
	
	public static Solucion create(List<Integer> ls) {
		return new Solucion(ls);
	}
	
	private Double total;
	private List<Subconjunto> subconjuntos;	

	private Solucion(List<Integer> ls) {
		total = 0.;
		subconjuntos = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				total += Datos.getPeso(i);
				subconjuntos.add(Datos.getSubConjunto(i));
			}
		}
	}

	@Override
	public String toString() {
		String s = subconjuntos.stream().map(e -> "S"+e.id())
		.collect(Collectors.joining(", ", "Conjuntos elegidos: {", "}\n"));
		return String.format("%sCoste Total: %.1f", s, total);	
	}
}

