package us.lsi.geneticos.museo.paco;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import us.lsi.common.List2;
import us.lsi.geneticos.museo.paco.Datos.Obra;

public class Solucion implements Comparable<Solucion>{
	
	public static Solucion of(List<Integer> ls) {
		return new Solucion(ls);
	}
	
	private int numObras;
	private Double int_total, int_med;
	private SortedMap<Integer,List<Obra>> seleccion;
	
	private Solucion(List<Integer> ls) {
		numObras = 0;
		int_total = 0.;
		seleccion = new TreeMap<>();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)<Datos.getNumSalas()) {
				numObras++;
				int_total += Datos.getInteres(i);
				seleccion.computeIfAbsent(ls.get(i), k -> List2.empty()).add(Datos.getObra(i));
			}
		}
		int_med = int_total / ls.size();
	}
	
	@Override
	public String toString() {
		String msg = String.format("%d Obras seleccionadas:\n", numObras);
		for (Map.Entry<Integer,List<Obra>> g: seleccion.entrySet()) {
			msg += String.format("Sala %d: %s\n", g.getKey()+1, g.getValue());
		}
		return msg+String.format("Interes Total/Medio: %.1f / %.1f", int_total, int_med);
	}

	@Override
	public int compareTo(Solucion other) {
		return this.int_total.compareTo(other.int_total);
	}

}