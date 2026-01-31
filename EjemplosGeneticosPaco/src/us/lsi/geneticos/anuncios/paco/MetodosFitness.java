package us.lsi.geneticos.anuncios.paco;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.anuncios.datos.DatosAnuncios;

public class MetodosFitness {

	public static double valorTotal(List<Integer> cr) {
		int n = numAnuncios(cr);
		return IntStream.range(0, n)
		.mapToDouble(i->DatosAnuncios.getAnuncio(cr.get(i)).getPrecio(i+1)).sum();
	}
	
	public static double tiempoTotal(List<Integer> cr) {
		int n = numAnuncios(cr);
		return IntStream.range(0, n)
		.mapToDouble(i->DatosAnuncios.getAnuncio(cr.get(i)).getDuracion()).sum();
	}
	
	public static double totalIncompatibles(List<Integer> cr) {
		List<Integer> ls = cr.subList(0, numAnuncios(cr));
		return DatosAnuncios.restricciones.stream()
		.filter(p-> ls.contains(p.first()) && ls.contains(p.second())).count();
	}
	
	public static int numAnuncios(List<Integer> cr) {
		int n = cr.size();
		return Long.valueOf(IntStream.range(0, n).map(i->tiempoAcumulado(i,cr))
			.filter(t-> t<=DatosAnuncios.tiempoTotal).count()).intValue();
	}
	
	private static int tiempoAcumulado(int pos, List<Integer> cr) {
		return IntStream.rangeClosed(0, pos)
			.map(i->DatosAnuncios.getAnuncio(cr.get(i)).getDuracion()).sum();
	}
}
