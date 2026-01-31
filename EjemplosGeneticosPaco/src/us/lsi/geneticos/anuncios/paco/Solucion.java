package us.lsi.geneticos.anuncios.paco;

import static us.lsi.geneticos.anuncios.paco.MetodosFitness.numAnuncios;
import static us.lsi.geneticos.anuncios.paco.MetodosFitness.tiempoTotal;
import static us.lsi.geneticos.anuncios.paco.MetodosFitness.totalIncompatibles;
import static us.lsi.geneticos.anuncios.paco.MetodosFitness.valorTotal;

import java.util.List;

import us.lsi.anuncios.datos.Anuncio;
import us.lsi.anuncios.datos.DatosAnuncios;

public class Solucion {
	
	public static Solucion of(List<Integer> indices) {
		return new Solucion(indices);
	}

	private List<Anuncio> anuncios;
	private Double valorTotal, tiempoConsumido, totalIncompatibles;

	private Solucion(List<Integer> cr) {
		int n = numAnuncios(cr);
		anuncios = cr.subList(0, n).stream().map(i->DatosAnuncios.getAnuncio(i)).toList();
		valorTotal = valorTotal(cr);
		tiempoConsumido = tiempoTotal(cr);
		totalIncompatibles = totalIncompatibles(cr);
	};
	
	@Override
	public String toString() {
		String s1 = String.format("\nAnuncios Elegidos: %s", anuncios);
		String s2 = String.format("\nValor Total: %.2f", valorTotal);
		String s3 = String.format("\nTiempo Consumido: %d", tiempoConsumido.intValue());
		String s4 = String.format("\nNum. Anuncios Incompatibles: %d", totalIncompatibles.intValue());
		String s5 = String.format("\nTiempo Total: %d", DatosAnuncios.tiempoTotal);
		
		return s1+s2+s3+s4+s5;
	}
	
}