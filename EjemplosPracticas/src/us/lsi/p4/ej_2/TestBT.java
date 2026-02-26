package us.lsi.p4.ej_2;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.BT.Type;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/p4/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
			DatosSubconjuntos.toConsole();
			
			// V�rtices clave

			SubconjuntosVertex start = SubconjuntosVertex.initial();

			// Grafo

			EGraph<SubconjuntosVertex, SubconjuntosEdge> graph = 
					EGraph.virtual(start)
					.edgeWeight(x-> x.weight())
					.heuristic(SubconjuntosHeuristic::heuristic)
					.build();

			Boolean conVoraz = false;
			
			System.out.println("\n\n#### Algoritmo BT ####");
			System.out.println(conVoraz?"\nCon Voraz":"\nSin Voraz");

			BT<SubconjuntosVertex, SubconjuntosEdge, SolucionSubconjuntos> bta = null;
			SolucionSubconjuntos sv = null;
			
			Optional<GraphPath<SubconjuntosVertex, SubconjuntosEdge>> gp = Optional.empty();
			if (conVoraz) {
				GreedyOnGraph<SubconjuntosVertex, SubconjuntosEdge> ga = GreedyOnGraph.of(graph);
				gp = ga.search();
				if (gp.isPresent()) sv = SolucionSubconjuntos.of(gp.get());
				System.out.println("Sv = "+sv);
			}
			if(gp.isPresent()) 
				bta = BTBuilder.<SubconjuntosVertex,SubconjuntosEdge,SolucionSubconjuntos>of()
						.graph(graph)
						.type(Type.Min)
						.bestValue(gp.get().getWeight())
						.optimalPath(gp.get())
						.withGraph(true)
						.fsolution(SolucionSubconjuntos::of)
						.build();
			else 
				bta = BTBuilder.<SubconjuntosVertex,SubconjuntosEdge,SolucionSubconjuntos>of()
						.graph(graph)
						.type(Type.Min)
						.withGraph(true)
						.build();
			bta.search();
			
			sv = SolucionSubconjuntos.of(bta.optimalPath().orElse(null));
			List<SubconjuntosEdge> le = bta.optimalPath().get().getEdgeList();
			
			System.out.println("Sol opt = "+sv);

			var outGraph = bta.outGraph();
			if(outGraph!=null)
				GraphColors.toDot(bta.outGraph(),"ficheros_generados/p4/ejemplo2/subconjuntosBTGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,v.goal()),
					e->GraphColors.colorIf(Color.red,le.contains(e))
					);
			
		}
	}

}

