package us.lsi.geneticos.tareasprocesadores.paco;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MetodosFitness {

	public static double tiempoProcesadorMasTiempo(List<Integer> cr) {
		return tiemposProcesadores(cr).stream().max(Comparator.naturalOrder()).get();
	}
	
	public static Collection<Double> tiemposProcesadores(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).boxed()
		.collect(Collectors.groupingBy(i->cr.get(i), 
				Collectors.summingDouble(i->Datos.getTiempo(i)))).values();
	}
}