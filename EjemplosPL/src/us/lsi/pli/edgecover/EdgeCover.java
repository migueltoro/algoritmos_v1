package us.lsi.pli.edgecover;

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

public class EdgeCover {
	
	public static SimpleWeightedGraph<Ciudad,Carretera> graph;
	public static IntegerVertexGraphView<Ciudad, Carretera> g;
	
	public static void leeDatos(String file) {
		EdgeCover.graph =  GraphsReader.newGraph(file,
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);
		EdgeCover.g = IntegerVertexGraphView.of(EdgeCover.graph);
		GraphData.n = EdgeCover.g.vertexSet().size();
		GraphData.vertexWeight = EdgeCover.g.vertexSet().stream()
				.collect(Collectors.toMap(x->x,x->1./EdgeCover.g.vertex(x).habitantes()));
		GraphData.graph = EdgeCover.g;
	}
	
	public static Ciudad ciudad(String s) {
		String[] partes = s.split("_");
		Ciudad c = EdgeCover.g.getVertex(Integer.parseInt(partes[1]));
		return c;
	}
	
	public static Carretera carretera(String s) {
		String[] partes = s.split("_");
		Ciudad c1 = EdgeCover.g.getVertex(Integer.parseInt(partes[1]));
		Ciudad c2 = EdgeCover.g.getVertex(Integer.parseInt(partes[2]));
		Carretera c = EdgeCover.graph.getEdge(c1,c2);
		return c;
	}
	
	public static void edge_cover_model() throws IOException {
		EdgeCover.leeDatos("data/andalucia.txt");
		System.out.println(GraphData.graph);
		AuxGrammar.generate(GraphData.class, "modelos/edge_cover.lsi", "ficheros/edge_cover.lp");
		Optional<GurobiSolution> solution = GurobiLp.gurobi("ficheros/edge_cover.lp");
		if (solution.isPresent()) {
			System.out.println(solution.get().toString((s, d) -> d > 0.));
			Set<Carretera> carreteras = solution.get().values.keySet().stream()
					.filter(s -> solution.get().values.get(s) > 0)
					.map(s -> EdgeCover.carretera(s))
					.collect(Collectors.toSet());
			GraphColors.toDot(EdgeCover.graph, "ficheros/edge_cover.gv", v -> v.nombre(), e -> e.nombre(),
					v -> GraphColors.color(Color.black),
					e -> GraphColors.colorIf(Color.red, Color.black, carreteras.contains(e)));
		} else {
			System.out.println("\n\n*****Modelo sin solución****");
		}

	}

	public static void main(String[] args)  throws IOException{
		edge_cover_model();

	}

}
