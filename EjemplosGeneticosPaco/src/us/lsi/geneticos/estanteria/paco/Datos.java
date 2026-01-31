package us.lsi.geneticos.estanteria.paco;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class Datos {
	public record Libro(Integer id, Double ancho, Double alto) {
		public static int cont = 1;
		public static Libro parse(String s) {
			String[] v = s.split(":");
			return new Libro(cont++, 
				Double.parseDouble(v[0].trim()), Double.parseDouble(v[1].trim()));
		}
		
		@Override
		public String toString() {
			return String.format("L%d: Ancho = %.1f; Alto = %.1f", id, alto, ancho);
		}
	}
	
	public static double ANCHURA;
	private static List<Double> alturas;
	private static List<Libro> libros;
	
	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	public static void iniDatos(String file, boolean show) {
		Libro.cont = 1;
		List<String> lineas = Files2.linesFromFile(file);
		ANCHURA = Double.parseDouble(lineas.get(0).split("=")[1].trim());
		alturas = List2.parse(lineas.get(1).split("=")[1].trim().split(","), Double::valueOf);
		libros = List2.empty();
		for(int i=2;i<lineas.size();i++) {
			libros.add(Libro.parse(lineas.get(i)));
		}
		if(show)
			toConsole();
	}

	public static double getAnchura() {
		return ANCHURA;
	}
	
	public static int getNumEstantes() {
		return alturas.size();
	}

	public static Integer getNumLibros() {
		return libros.size();
	}

	public static double getAlturaEstante(int i) {
		return alturas.get(i);
	}

	public static double getAlturaLibro(int i) {
		return libros.get(i).alto();
	}

	public static double getAnchuraLibro(int i) {
		return libros.get(i).ancho();
	}
	
	public static void toConsole() {
		String2.toConsole("Anchura Estanteria: %.1f\nAltura Estantes: %s", ANCHURA, alturas);
		String2.toConsole(libros, "LIBROS");
	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/geneticos/estanteria/estanteria.txt", true);
	}

}

