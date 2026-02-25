package us.lsi.p4.ej_1;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.ASBuilder;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 7; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/p4/multiconjuntos.txt", id_fichero);
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// V�rtices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.start();

			// Grafo

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.edgeWeight(x -> x.weight())
					.heuristic(MulticonjuntoHeuristic::heuristic)
					.build();
					
			GreedyOnGraph<MulticonjuntoVertex, MulticonjuntoEdge> rr = GreedyOnGraph.of(graph);
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> r = rr.path();
			
			AStar<MulticonjuntoVertex, MulticonjuntoEdge,Integer> aStar = 
					ASBuilder.<MulticonjuntoVertex, MulticonjuntoEdge,Integer>of()
					.graph(graph)
					.type(AStar.Type.Min)
					.bestValue(r.getWeight())
					.optimalPath(r)
					.build();					
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = aStar.search().get();
			
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
			
			
			SolucionMulticonjunto s_as = SolucionMulticonjunto.of(gp_as);
			
			
			System.out.println(s_as);
			System.out.println(gp_as);

//			GraphColors.toDot(aStar.outGraph, "ficheros_generados/p4/ejemplo1/multiconjuntosAStarGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}

}

