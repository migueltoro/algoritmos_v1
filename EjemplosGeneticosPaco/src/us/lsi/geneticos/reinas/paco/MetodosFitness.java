package us.lsi.geneticos.reinas.paco;

import java.util.List;
import java.util.stream.IntStream;

public class MetodosFitness {

	public static double totalDiagPrincipal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).map(i->cr.get(i)-i).distinct().count();
	}
	
	public static double totalDiagSecundaria(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).map(i->cr.get(i)+i).distinct().count();
	}
}
