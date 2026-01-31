package us.lsi.p3.ej_3.paco;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import us.lsi.common.List2;

public class Solucion {
	
	public static Solucion of(List<Integer> ls) {
		return new Solucion(ls);
	}
	public static Solucion empty() {
		return new Solucion();
	}

	private double af_tot;
	private SortedMap<Integer,List<String>> solucion;
	
	private Solucion(List<Integer> ls) {
		af_tot = 0;
		solucion = new TreeMap<>();
		for(int n=0; n<ls.size(); n++) {
			if(ls.get(n)>0) {
				int i = n%Datos.getNumAlumnos();
				int j = n/Datos.getNumAlumnos();
				af_tot += Datos.getAfinidad(i,j);
				if(solucion.containsKey(j))
					solucion.get(j).add(Datos.getAlumno(i).nombre());
				else
					solucion.put(j, List2.of(Datos.getAlumno(i).nombre()));
			}
		}
	}
	
	private Solucion() {
		af_tot = 0;
		solucion = new TreeMap<>();
	}
	public void add(int i, int j) {
		af_tot += Datos.getAfinidad(i,j);
		if(solucion.containsKey(j))
			solucion.get(j).add(Datos.getAlumno(i).nombre());
		else
			solucion.put(j, List2.of(Datos.getAlumno(i).nombre()));		
	}
	
	@Override
	public String toString() {
		double med = af_tot/Datos.getNumAlumnos();
		String s = String.format("\nAfinidad total: %.1f; A. med: %.1f", af_tot, med);
		return solucion.entrySet().stream().map(e -> "Grupo "+(e.getKey()+1)+": "+e.getValue())
		.collect(Collectors.joining("\n", "Reparto obtenido:\n", s));
	}
}

