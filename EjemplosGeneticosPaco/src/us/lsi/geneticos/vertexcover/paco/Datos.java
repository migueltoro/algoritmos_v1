package us.lsi.geneticos.vertexcover.paco;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Datos {

	private static Graph<Ciudad,Carretera> grafo; 
	private static List<Ciudad> ciudades;
	
	public static void iniDatos(String file) {
		grafo = GraphsReader.newGraph(file, 
				Ciudad::ofFormat,
				Carretera::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Carretera::km);
		ciudades = grafo.vertexSet().stream().toList();
	}
	
	public static int getNumCiudades() {
		return ciudades.size();
	}
	
	public static int getNumAristas() {
		return grafo.edgeSet().size();
	}
	
	public static Ciudad getCiudad(int i) {
		return ciudades.get(i);
	}

	public static double getHabitantes(int i) {
		return ciudades.get(i).habitantes();
	}	
	
	public static Set<Carretera> getAristas(int i) {
		return grafo.edgesOf(ciudades.get(i));
	}
}