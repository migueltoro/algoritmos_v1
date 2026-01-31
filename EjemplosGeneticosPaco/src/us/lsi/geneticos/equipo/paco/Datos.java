package us.lsi.geneticos.equipo.paco;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class Datos {
	
	public static final int ZONAS = 0;
	public static final double UMBRAL = 5.;

	public record Jugador(String nombre, List<Double> rendimiento) {
		static Jugador parse(String s) {
			String[] tokens = s.split(":");
			return new Jugador(tokens[0].trim(),
					List2.parse(tokens[1].trim().split(","), Double::parseDouble));
		}
		
		public Double getRendimiento(int posicion) {
			return rendimiento.get(posicion);
		}
		
		@Override
		public String toString() {
			int pos = IntStream.range(0, rendimiento.size()).boxed()
			.max(Comparator.comparing(i->rendimiento.get(i))).get();
			return String.format("%s -> Mejor Posicion: %d", nombre, pos);
		}
	}
	
	//numero de jugadores
	private static int N;
	//numero de posiciones
	private static int M;
	private static List<Jugador> jugadores; 
	
	public static void iniDatos(String file) {
		iniDatos(file, false, ZONAS);
	}
	
	public static void iniDatos(String file, int zonas) {
		iniDatos(file, false, zonas);
	}
	
	public static void iniDatos(String file, boolean show) {
		iniDatos(file, show, ZONAS);
	}
	
	public static void iniDatos(String fichero, boolean show, int zonas) {
		jugadores = Files2.streamFromFile(fichero)
		.map(s->Jugador.parse(s)).collect(Collectors.toList());
		
		N = jugadores.size();
		M = zonas>0? zonas: jugadores.get(0).rendimiento().size();
		
		if(show)
			toConsole();
	}

	public static Jugador getJugador(int index) {
		return jugadores.get(index);
	}
	
	public static Integer getNumJugadores() {
		return N;
	}

	public static Integer getNumPosiciones() {
		return M;
	}

	public static String getNombre(Integer i) {
		return jugadores.get(i).nombre();
	}
	
	public static Double getRendimiento(Integer i, Integer j) {
		return jugadores.get(i).getRendimiento(j);
	}
	
	// devuelve la mejor posicion para el jugador i
	public static int mejorPosicion(Integer i, List<Integer> posiciones) {
		return IntStream.range(0, M).boxed()				
		.max(Comparator.comparing(j->getRendimiento(i,j))).get();
	}
	
	public static void toConsole() {
		String prefix = String.format("Num. de jugadores: %d\n", N);
		String sufix = String.format("\n%s", String2.linea());
		String txt = jugadores.stream().map(a->a.toString())
		.collect(Collectors.joining("\n", prefix, sufix));
		String2.toConsole(txt);
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/geneticos/equipo/equipo1.txt", true);
	}
	
}