package us.lsi.geneticos.estanteria.paco;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.common.String2;

public class Solucion {
	
	public static Solucion of(List<Integer> ls) {
		return new Solucion(ls);
	}

	private Map<Integer, List<String>> distribucion;

	private Solucion(List<Integer> cr) {
		int n = cr.size();
		distribucion = IntStream.range(0, n).boxed()
		.filter(i->cr.get(i)<Datos.getNumEstantes())
		.collect(Collectors.toMap(i->cr.get(i), i-> List.of("L"+(i+1)), (a,b)->List2.concat(a,b)));
	}
	
	@Override
	public String toString() {
		return distribucion.entrySet().stream()
		.map(e-> String.format("Estante %d: %s", e.getKey()+1, e.getValue()))
		.collect(Collectors.joining("\n", "Distribucion:\n", "\n"+String2.linea()));
	}
}

