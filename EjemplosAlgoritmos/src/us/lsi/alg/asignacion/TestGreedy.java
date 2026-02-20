package us.lsi.alg.asignacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import us.lsi.common.IntPair;

public class TestGreedy {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		Asignacion.leeFichero("ficheros/asignacion/asignacionDeTareas.txt");
		AsignacionVertex v0 = AsignacionVertex.inicial();
//		System.out.println(v0);
		List<IntPair> acciones = new ArrayList<>();
		Double coste = 0.;
		while (!v0.goal()) {
			IntPair a = v0.greedyAction();
			acciones.add(a);
			coste += Asignacion.costes(a.first(), a.second());
			v0 = v0.neighbor(a);
		}
		System.out.println(acciones);
		System.out.println(coste);
	}

}
