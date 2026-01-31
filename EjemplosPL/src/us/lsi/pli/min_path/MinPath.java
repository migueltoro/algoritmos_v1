package us.lsi.pli.min_path;

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

public class MinPath {
	
	public static SimpleWeightedGraph<Ciudad,Carretera> graph;
	public static IntegerVertexGraphView<Ciudad, Carretera> g;
	
	public static void leeDatos(String file) {
		MinPath.graph =  GraphsReader.newGraph(file,
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);
		MinPath.g = IntegerVertexGraphView.of(graph);
		GraphData.n = MinPath.g.vertexSet().size();
		GraphData.vertexWeight = MinPath.g.vertexSet().stream()
				.collect(Collectors.toMap(x->x,x->1./MinPath.g.vertex(x).habitantes()));
		GraphData.graph = g;
	}
	
	public static Ciudad ciudad(String s) {
		String[] partes = s.split("_");
		Ciudad c = MinPath.g.getVertex(Integer.parseInt(partes[1]));
		return c;
	}
	
	public static Carretera carretera(String s) {
		String[] partes = s.split("_");
		Ciudad c1 = MinPath.g.getVertex(Integer.parseInt(partes[1]));
		Ciudad c2 = MinPath.g.getVertex(Integer.parseInt(partes[2]));
		Carretera c = MinPath.graph.getEdge(c1,c2);
		return c;
	}
	
	public static void min_path_model() throws IOException {
		MinPath.leeDatos("data/andalucia.txt");
		System.out.println(GraphData.graph);
		AuxGrammar.generate(GraphData.class, "modelos/min_path.lsi", "ficheros/min_path.lp");
		Optional<GurobiSolution> solution = GurobiLp.gurobi("ficheros/min_path.lp");
		if (solution.isPresent()) {
			System.out.println(solution.get().toString((s, d) -> (s.charAt(0) == 'x' && d > 0.) || (s.charAt(0) == 'y' && d >= 0.)));
			Set<Carretera> carreteras = solution.get().values.keySet().stream()
					.filter(s->s.charAt(0) == 'x')
					.filter(s -> solution.get().values.get(s) > 0)
					.map(s -> MinPath.carretera(s))
					.collect(Collectors.toSet());
			GraphColors.toDot(MinPath.graph, "ficheros/min_path.gv", v -> v.nombre(), e -> e.nombre(),
					v -> GraphColors.color(Color.black),
					e -> GraphColors.colorIf(Color.red, Color.black, carreteras.contains(e)));
		} else {
			System.out.println("\n\n*****Modelo sin solución****");
		}

	}

	public static void main(String[] args)  throws IOException{
		min_path_model();
	}

}

