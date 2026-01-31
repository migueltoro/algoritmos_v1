package us.lsi.geneticos.camino.paco;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.common.List2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class Datos {

	public static Integer ORIGEN, DESTINO;
	private static IntegerVertexGraphView<Ciudad,Carretera> graph;
	
	public static Predicate<Ciudad> pv;
	public static Predicate<Double> pe;
	
	public static void iniDatos(String fichero, Predicate<Ciudad> vp, Predicate<Double> ep, 
			String origen, String destino) {	
		
		Graph<Ciudad,Carretera> g = GraphsReader.newGraph(fichero,
			Ciudad::ofFormat, Carretera::ofFormat, Graphs2::simpleWeightedGraph, Carretera::km);

		graph = IntegerVertexGraphView.of(g);
		pv = vp; pe = ep;		

		ORIGEN = find(origen);
		DESTINO = find(destino);
	}

	private static Integer find(String name) {
		int n = graph.vertexSet().size();
		return IntStream.range(0, n).filter(i->graph.vertex(i).nombre().equals(name))
			.findFirst().getAsInt();
	}

	public static int getNumVertices() {
		return graph.vertexSet().size();
	}
	
	public static Ciudad getVertice(int i) {
		return graph.getVertex(i);
	}
	
	
	public static double getPeso(int i, int j) {
		return graph.containsEdge(i, j)? graph.getEdgeWeight(i, j): 10000;
	}

	public static boolean test(Integer i) {
		return pv.test(graph.getVertex(i));
	}
	
	public static boolean test(Integer i, Integer j) {
		return graph.containsEdge(i, j) && pe.test(graph.getEdgeWeight(i, j));
	}

	public static List<Integer> restoVertices() {
		int n = graph.vertexSet().size();
		return List2.difference(List2.rangeList(0, n), List.of(ORIGEN, DESTINO));
	}

}