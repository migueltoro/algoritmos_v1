package us.lsi.geneticos.edgescover.paco;

import java.util.List;

import org.jgrapht.Graph;

import us.lsi.common.List2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Datos {

	private static Graph<Ciudad,Carretera> grafo; 
	private static List<Carretera> carreteras;
	
	public static void iniDatos(String file) {
		grafo = GraphsReader.newGraph(file, 
				Ciudad::ofFormat,
				Carretera::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Carretera::km);
		
		carreteras = grafo.edgeSet().stream().toList();
	}
	
	public static long getNumCiudades() {
		return grafo.vertexSet().size();
	}
	
	public static int getNumAristas() {
		return grafo.edgeSet().size();
	}
	
	public static Carretera getCarretera(int i) {
		return carreteras.get(i);
	}

	public static List<Ciudad> getVertices(int i) {
		Carretera a = carreteras.get(i);
		return List2.of(grafo.getEdgeSource(a), grafo.getEdgeTarget(a));
	}
	
	public static Ciudad getOpuesto(Ciudad v, int i) {
		List<Ciudad> lv = getVertices(i);
		lv.remove(v);
		return lv.get(0);
	}

}