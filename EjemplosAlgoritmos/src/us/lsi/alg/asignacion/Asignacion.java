package us.lsi.alg.asignacion;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import us.lsi.common.Files2;
public class Asignacion {
	
	public static Integer n;
	public static Integer m;
	public static Integer tmin;
	public static Integer tmax;
	private static Double inf = 10000.;
	private static Double[][] costes;
	
	public static Double costes(Integer i, Integer j) {
		return Asignacion.costes[i][j];
	}
	
	public static void leeFichero(String f) {
		List<String> lineas = Files2.linesFromFile(f);
		Asignacion.n = Integer.parseInt(lineas.get(0));
		Asignacion.m = Integer.parseInt(lineas.get(1));
		Asignacion.tmax = Collections.max(List.of(Asignacion.n, Asignacion.m));
		Asignacion.tmin = Collections.min(List.of(Asignacion.n, Asignacion.m));
		Asignacion.costes = new Double[tmax][tmax];
		String[] dat;
		for (int k = 2; k < lineas.size(); k++) {
			dat = lineas.get(k).split(",");
			Integer i = Integer.parseInt(dat[0].trim());
			Integer j = Integer.parseInt(dat[1].trim());
			Asignacion.costes[i][j] = Double.parseDouble(dat[2].trim());
		}
		for (int i = 0; i < Asignacion.tmax; i++) {
			for (int j = 0; j < Asignacion.tmax; j++) {
				if (i >= Asignacion.n || j >= Asignacion.m)
					Asignacion.costes[i][j] = Asignacion.inf;
			}
		}
	}

	public static void main(String[] args) throws IOException {	
		Locale.setDefault(Locale.of("en", "us"));
		Asignacion.leeFichero("ficheros/asignacion/asignacionDeTareas_2.txt");
		for (int i = 0; i < Asignacion.tmax; i++) {
			for (int j = 0; j < Asignacion.tmax; j++) {
				System.out.println(String.format("%d,%d,%.2f",i,j,Asignacion.costes[i][j]));
			}
		}
	}

}



