package us.lsi.p3.ej_1.paco;

import java.util.List;
import us.lsi.common.Multiset;

public class Solucion {
	
	public static Solucion of(List<Integer> ls) {
		return new Solucion(ls);
	}

	private Integer suma, tam;
	private Multiset<Integer> solucion;

	private Solucion() {
		suma = tam = 0;
		solucion = Multiset.empty();
	}
	private Solucion(List<Integer> ls) {
		suma = tam  = 0;
		solucion = Multiset.of();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {				
				Integer e = ls.get(i);
				Integer v = Datos.getElemento(i);
				tam += e;
				solucion.add(v, e);
				suma += v*e;
			}
		}
	}
	
	public static Solucion empty() {
		return new Solucion();
	}

	public void add(Integer e) {
		solucion.add(e, 1);
		tam++;
		suma += e;
	}
	public void add(Integer e, Integer n) {
		solucion.add(e, n);
		tam += n;
		suma += e*n;
	}

	@Override
	public String toString() {
		int error = Math.abs(Datos.getSuma() - suma);
		String e = error<1? "": String.format("Error = %d", error);
		return String.format("MS = %s; Distintos = %d; Total = %d; %s", solucion, solucion.size(), tam, e);
	}
}

