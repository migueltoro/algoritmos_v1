package us.lsi.geneticos.tareasprocesadores.paco;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class Datos {

	private static Integer numProcesadores;
	private static List<Double> tiempos;

	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	public static void iniDatos(String file, boolean show) {
		List<String> lineas = Files2.linesFromFile(file);
		numProcesadores = Integer.parseInt(lineas.get(0).split("=")[1].trim());
		tiempos = List2.empty();
		for (int i = 1; i < lineas.size(); i++) {
			tiempos.add(Double.parseDouble(lineas.get(i).trim()));
		}
		if(show) {
			String2.toConsole("Num. Procesadores: ", numProcesadores);
			String2.toConsole("Tiempos de cada tarea: %s", tiempos);
		}
	}

	public static Integer getNumTareas() {
		return tiempos.size();
	}

	public static Integer getNumProcesadores() {
		return numProcesadores;
	}
	
	public static double getTiempo(int i) {
		return tiempos.get(i);
	}

}