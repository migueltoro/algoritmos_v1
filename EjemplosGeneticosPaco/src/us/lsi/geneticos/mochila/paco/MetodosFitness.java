package us.lsi.geneticos.mochila.paco;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.mochila.datos.DatosMochila;

public class MetodosFitness {

	public static double valorTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).mapToDouble(i->cr.get(i)*DatosMochila.getValor(i)).sum();
	}
	
	public static double pesoTotal(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).mapToDouble(i->cr.get(i)*DatosMochila.getPeso(i)).sum();
	}
}
