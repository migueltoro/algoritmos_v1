package us.lsi.alg.asignacion;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.alg.BT.Type;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		Asignacion.leeFichero("ficheros/asignacion/asignacionDeTareas.txt");
		AsignacionVertex v0 = AsignacionVertex.inicial();
		
		EGraph<AsignacionVertex, AsignacionEdge> graph = EGraph.virtual(v0)
				.pathType(PathType.Sum)
				.heuristic(Heuristica::heuristic)
				.build();
		
		System.out.println("\n\n#### Algoritmo Greedy ####");
		
		GreedyOnGraph<AsignacionVertex, AsignacionEdge> rr = GreedyOnGraph.of(graph);
		
		GraphPath<AsignacionVertex, AsignacionEdge> path = rr.path();
		Double bv = path.getWeight();
		System.out.println(bv);
		
		System.out.println("\n\n#### Algoritmo BT ####");

		BT<AsignacionVertex, AsignacionEdge, AsignacionSolucion> bt = 
				BTBuilder.<AsignacionVertex,AsignacionEdge,AsignacionSolucion>of()
				.graph(graph)
				.type(Type.Min)
				.bestValue(path.getWeight())
				.optimalPath(path)
				.withGraph(true)
				.fsolution(AsignacionSolucion::of)
				.build();

		GraphPath<AsignacionVertex, AsignacionEdge> s = bt.search().get();

		System.out.println(AsignacionSolucion.of(s));

	}

}
