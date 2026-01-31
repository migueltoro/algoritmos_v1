package us.lsi.geneticos.agentestareas.paco;

import java.util.Arrays;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.String2;

public class Datos {

	private static Integer n;
	private static Double[][] costes;

	public static void iniDatos(String f) {
		List<String> lineas = Files2.linesFromFile(f);
		n = Integer.parseInt(lineas.get(0));
		costes = new Double[n][n];
		String[] dat;
		Integer i, j;
		for (int k = 1; k < lineas.size(); k++) {
			dat = lineas.get(k).split(",");
			i = Integer.parseInt(dat[0].trim());
			j = Integer.parseInt(dat[1].trim());
			costes[i][j] = Double.parseDouble(dat[2].trim());
		}
	}

	public static Integer getN() {
		return n;
	}

	public static Double getCoste(int i, int j) {
		return costes[i][j];
	}
	
	public static void main(String[] args) {
		iniDatos("ficheros/geneticos/agentestareas/agentestareas.txt");
		String2.toConsole("Num Agentes/Tareas: %d\nTabla de costes:", n);
		for(var f: costes)
			System.out.println(Arrays.toString(f));
	}

}