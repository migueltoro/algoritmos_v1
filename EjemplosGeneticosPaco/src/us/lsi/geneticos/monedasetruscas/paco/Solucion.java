package us.lsi.geneticos.monedasetruscas.paco;

import java.util.List;
import java.util.function.Function;

import us.lsi.common.Multiset;
import us.lsi.geneticos.monedasetruscas.paco.Datos.Moneda;

public class Solucion {
	
	public static Solucion of(List<Integer> cr) {
		return new Solucion(cr);
	}

	private Double peso, tam, valor;
	private Multiset<String> seleccion;

	private Solucion(List<Integer> cr) {
		peso = tam = valor = 0.;
		seleccion = Multiset.of();
		Function<Moneda, String> f = m -> String.format("M%d (%.1f)", m.id(), m.valor());
		for(int i=0; i<cr.size(); i++) {
			if(cr.get(i)>0) {
				tam += cr.get(i);
				seleccion.add(f.apply(Datos.getMoneda(i)), cr.get(i));
				valor += cr.get(i)*Datos.getValor(i);
				peso += cr.get(i)*Datos.getPeso(i);
			}
		}
	}

	@Override
	public String toString() {
		String s1 = String.format("Monedas/Cantidades Elegidas: %s\n", seleccion);
		String s2 = String.format("Valor Total: %d\n", valor.intValue());
		String s3 = String.format("Peso Total: %d\n", peso.intValue());
		String s4 = String.format("Num. Total de monedas: %d", tam.intValue());
		return s1+s2+s3+s4;
	}
}

