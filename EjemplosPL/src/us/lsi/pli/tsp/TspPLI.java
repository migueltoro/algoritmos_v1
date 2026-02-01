package us.lsi.pli.tsp;



import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.graphs.GraphData;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.math.Math2;
import us.lsi.solve.AuxGrammar;
import us.lsi.streams.Stream2;

public class TspPLI {
	
	public static Graph<Integer, SimpleEdge<Integer>> graph;
	public static int n; //numero de vertices
 

	public static Graph<Integer, SimpleEdge<Integer>> graph(Integer n, Double pb) {
		Locale.setDefault(Locale.of("en", "US"));
		Graph<Integer, SimpleEdge<Integer>> graph = Graphs2.simpleWeightedGraph();
		IntStream.range(0, n).forEach(v -> graph.addVertex(v));
		Stream2.allPairs(0,n, 0,n).filter(p -> p.second() > p.first()).forEach(p -> {
			if (Math2.getDoubleAleatorio(0., 1.) < pb) {
				Double w = Math2.getDoubleAleatorio(0., 100.);
				SimpleEdge<Integer> e1 = SimpleEdge.of(p.first(), p.second(),w);
				graph.addEdge(p.first(), p.second(), e1);
				graph.setEdgeWeight(e1,w);
			}
		});
		return graph;
	}
	
	
	public static void tsp_model_1() throws IOException {
		TspPLI.graph = graph(40,0.6);
		TspPLI.n = TspPLI.graph.vertexSet().size();
		System.out.println(TspPLI.graph);
		GraphData.graph = TspPLI.graph;
		GraphData.n = TspPLI.n;
		AuxGrammar.generate(GraphData.class,"modelos/tsp_1.lsi","ficheros/tsp_1.lp");
		Optional<GurobiSolution> solution = GurobiLp.gurobi("ficheros/tsp_1.lp");
		if (solution.isPresent()) {
			Locale.setDefault(Locale.of("en", "US"));
			System.out.println(solution.get().toString((s,d)->d>0.));
		} else {
			System.out.println("\n\n*****Modelo sin solución****");
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		tsp_model_1();
	}

}
