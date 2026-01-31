package us.lsi.geneticos.mochila.paco;

import java.util.List;

import us.lsi.common.Multiset;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;

public record Solucion(Integer valor, Integer peso, Multiset<ObjetoMochila> objetos) {
	
	public static Solucion of(List<Integer> acciones) {
		Multiset<ObjetoMochila> objetos = Multiset.empty();
		Integer valor = 0, peso = 0;
		for(int i=0; i< acciones.size();i++) {
			Integer a = acciones.get(i);
			if (a > 0) {
				objetos.add(DatosMochila.getObjeto(i), a);
				valor += a*DatosMochila.getValor(i);
				peso += a*DatosMochila.getPeso(i);
			}
		}
		return new Solucion(valor,peso,objetos);
	}
	
	@Override
	public String toString() {
		return String.format("Valor total: %d\nPeso total: %d\nUnidades elegidas:\n%s", valor, peso, objetos);
	}
}