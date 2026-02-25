package us.lsi.p4.ej_1;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 7; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/p4/multiconjuntos.txt", id_fichero);
			System.out.println("=============");
			System.out.println("\tResultados para el test " + id_fichero + "\n");
			
			DatosMulticonjunto.toConsole();

			// V�rtices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.start();

			// Grafo

			

			System.out.println("\n#### Algoritmo BT ####");
			
			// Algoritmo BT
			
			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.edgeWeight(x -> x.weight())
					.heuristic(MulticonjuntoHeuristic::heuristic)
					.build();
			
			
			GreedyOnGraph<MulticonjuntoVertex, MulticonjuntoEdge> rr = GreedyOnGraph.of(graph);
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> r = rr.path();
			
			System.out.println("Voraz = "+r.getWeight()+"  == "+SolucionMulticonjunto.of(r));
			
			BT<MulticonjuntoVertex, MulticonjuntoEdge, SolucionMulticonjunto> bta = 
					new BTBuilder<MulticonjuntoVertex, MulticonjuntoEdge, SolucionMulticonjunto>()
					.graph(graph)
					.fsolution(SolucionMulticonjunto::of)
					.withGraph(true)
					.build();

			if (rr.isSolution(r)) {
				bta = BTBuilder.<MulticonjuntoVertex, MulticonjuntoEdge, SolucionMulticonjunto>of()
						.graph(graph)
						.fsolution(SolucionMulticonjunto::of)
						.bestValue(r.getWeight())
						.optimalPath(r)
						.withGraph(true)
						.build();
			}
			Optional<GraphPath<MulticonjuntoVertex, MulticonjuntoEdge>> gp = bta.search();
			System.out.println(SolucionMulticonjunto.of(gp.get()));
			
//			System.out.println(bta.path.getEdgeList().stream().map(x -> x.action())
//					.collect(Collectors.toList()));
			
			
//			GraphColors.toDot(bta.graph(), "ficheros_generados/p4/ejemplo1/multiconjuntosBTGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, bta.optimalPath.getEdgeList().contains(e)));

		}
	}

}


