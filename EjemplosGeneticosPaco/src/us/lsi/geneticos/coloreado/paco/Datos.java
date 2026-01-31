package us.lsi.geneticos.coloreado.paco;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Datos {
	public static Integer MaxNumColors;
	
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
	
	public static Ciudad getCiudad(int i) {
		return ciudades.get(i);
	}
	
	public static List<Integer> vecinos(int i) {
		return Graphs.neighborListOf(grafo, ciudades.get(i)).stream().map(v->ciudades.indexOf(v)).toList();
	}
}