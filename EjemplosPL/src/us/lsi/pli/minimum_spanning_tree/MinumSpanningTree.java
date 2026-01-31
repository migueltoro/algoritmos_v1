package us.lsi.pli.minimum_spanning_tree;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.grafos.datos.Carretera;
import us.lsi.graphs.GraphData;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class MinumSpanningTree {
	
	public static SimpleWeightedGraph<Ciudad,Carretera> graph;
	public static IntegerVertexGraphView<Ciudad, Carretera> g;
	
	public static void leeDatos(String file) {
		MinumSpanningTree.graph =  GraphsReader.newGraph(file,
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);
		MinumSpanningTree.g = IntegerVertexGraphView.of(MinumSpanningTree.graph);
		GraphData.n = MinumSpanningTree.g.vertexSet().size();
		GraphData.vertexWeight = MinumSpanningTree.g.vertexSet().stream()
				.collect(Collectors.toMap(x->x,x->1./MinumSpanningTree.g.vertex(x).habitantes()));
		GraphData.graph = MinumSpanningTree.g;
	}
	
	public static Ciudad ciudad(String s) {
		String[] partes = s.split("_");
		Ciudad c = MinumSpanningTree.g.getVertex(Integer.parseInt(partes[1]));
		return c;
	}
	
	public static Carretera carretera(String s) {
		String[] partes = s.split("_");
		Ciudad c1 = MinumSpanningTree.g.getVertex(Integer.parseInt(partes[1]));
		Ciudad c2 = MinumSpanningTree.g.getVertex(Integer.parseInt(partes[2]));
		Carretera c = MinumSpanningTree.graph.getEdge(c1,c2);
		return c;
	}
	
	public static void minimum_spanning_tree_model() throws IOException {
		MinumSpanningTree.leeDatos("data/andalucia.txt");
		System.out.println(GraphData.graph);
		AuxGrammar.generate(GraphData.class, "modelos/minimum_spanning_tree.lsi", "ficheros/minimum_spanning_tree.lp");
		Optional<GurobiSolution> solution = GurobiLp.gurobi("ficheros/minimum_spanning_tree.lp");
		if (solution.isPresent()) {
			System.out.println(solution.get().toString((s, d) -> d > 0.));
			Set<Carretera> carreteras = solution.get().values.keySet().stream()
					.filter(s->s.charAt(0) == 'x')
					.filter(s -> solution.get().values.get(s) > 0)
					.map(s -> MinumSpanningTree.carretera(s))
					.collect(Collectors.toSet());
			GraphColors.toDot(MinumSpanningTree.graph, "ficheros/minimum_spanning_tree.gv", v -> v.nombre(), e -> e.nombre(),
					v -> GraphColors.color(Color.black),
					e -> GraphColors.colorIf(Color.red, Color.black, carreteras.contains(e)));
		} else {
			System.out.println("\n\n*****Modelo sin solución****");
		}

	}

	public static void main(String[] args)  throws IOException{
		minimum_spanning_tree_model();
	}

}

