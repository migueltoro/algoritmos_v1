package us.lsi.alg.asignacion;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		Asignacion.leeFichero("ficheros/asignacion/asignacionDeTareas.txt");
		AsignacionVertex v0 = AsignacionVertex.inicial();
		
		EGraph<AsignacionVertex, AsignacionEdge> graph = EGraph.virtual(v0)
				.pathType(PathType.Sum)
				.type(Type.Min)
				.heuristic(Heuristica::heuristic)
				.build();
		
		System.out.println("\n\n#### Algoritmo Greedy ####");
		
		GreedyOnGraph<AsignacionVertex, AsignacionEdge> rr = GreedyOnGraph.of(graph);
		
		GraphPath<AsignacionVertex, AsignacionEdge> path = rr.path();
		Double bv = path.getWeight();
		System.out.println(bv);
		
		System.out.println("\n\n#### Algoritmo BT ####");

		BT<AsignacionVertex, AsignacionEdge, AsignacionSolucion> bt = 
				BT.of(graph, AsignacionSolucion::of, 
						path.getWeight(),
						path,
						false);

		GraphPath<AsignacionVertex, AsignacionEdge> s = bt.search().get();

		System.out.println(AsignacionSolucion.of(s));

	}

}
