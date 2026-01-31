package us.lsi.geneticos.equipo.paco;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import us.lsi.common.List2;

public class Solucion implements Comparable<Solucion>{
	
	public static Solucion of(List<Integer> ls) {
		return new Solucion(ls);
	}
	
	private Double rnd_eqp;
	private SortedMap<Integer,List<String>> plantel;
	
	private Solucion(List<Integer> ls) {
		rnd_eqp = 0.;
		plantel = new TreeMap<>();
		for(int i=0; i<ls.size(); i++) {
			rnd_eqp += Datos.getRendimiento(ls.get(i), i);
			plantel.computeIfAbsent(i, k -> List2.empty()).add(Datos.getNombre(ls.get(i)));
		}
		rnd_eqp /= ls.size();
	}
	
	@Override
	public String toString() {
		String msg = "Alineacion obtenida:\n";
		for (Map.Entry<Integer,List<String>> g: plantel.entrySet()) {
			msg += String.format("Zona/Puesto %d: %s\n", g.getKey()+1, g.getValue());
		}
		return msg+String.format("Rendimiento medio: %.2f", rnd_eqp);
	}

	@Override
	public int compareTo(Solucion other) {
		return this.rnd_eqp.compareTo(other.rnd_eqp);
	}

}