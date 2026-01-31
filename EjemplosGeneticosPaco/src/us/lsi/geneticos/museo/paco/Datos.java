package us.lsi.geneticos.museo.paco;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class Datos {

	public record Obra(Integer id, String estilo, Double interes) {
		static int cont = 1;
		static Obra parse(String s) {
			String[] v = s.split(":");
			return new Obra(cont++, v[0].trim(), Double.parseDouble(v[1].trim()));
		}
		
		@Override
		public String toString() {
			return String.format("%d - %s: %.1f", id, estilo, interes);
		}
	}
	
	private record MinMax(int min, int max) {
		static MinMax parse(String s) {
			String[] v = s.trim().replace("(", "").replace(")", "").split(",");
			return new MinMax(Integer.valueOf(v[0].trim()), Integer.valueOf(v[1].trim()));
		}
	}
	
	private static List<Obra> obras; 
	private static List<MinMax> salas;
	
	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	
	public static void iniDatos(String fichero, boolean show) {
		List<String> ls = Files2.linesFromFile(fichero);
		
		salas = Arrays.stream(ls.get(0).split(";")).map(MinMax::parse).toList();
		
		obras = List2.empty();
		IntStream.range(1, ls.size()).forEach(i->obras.add(Obra.parse(ls.get(i))));
		
		if(show)
			toConsole();
	}

	public static Obra getObra(int index) {
		return obras.get(index);
	}
	
	public static Integer getNumObras() {
		return obras.size();
	}
	
	public static Integer getNumSalas() {
		return salas.size();
	}
	
	public static String getEstilo(Integer i) {
		return obras.get(i).estilo();
	}
	
	public static Double getInteres(Integer i) {
		return obras.get(i).interes();
	}
	
	public static Integer getMinObras(Integer j) {
		return salas.get(j).min();
	}
	
	public static Integer getMaxObras(Integer j) {
		return salas.get(j).max();
	}

	public static void toConsole() {
		String prefix = String.format("Num. de salas: %d\n", getNumSalas());
		String sufix = String.format("\n%s", String2.linea());
		String txt = obras.stream().map(a->a.toString())
		.collect(Collectors.joining("\n", prefix, sufix));
		String2.toConsole(txt);
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/geneticos/museo/museo.txt", true);
	}
	
}