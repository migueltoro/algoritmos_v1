package us.lsi.p4.ej_3;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.ASBuilder;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "alumnos_1.txt";
		DatosAlumnos.iniDatos("ficheros/p4/"+id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		AlumnosVertex start = AlumnosVertex.initial();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<AlumnosVertex, AlumnosEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.edgeWeight(x -> x.weight())
					.heuristic(AlumnosHeuristic::heuristic)
					.build();
					
		GreedyOnGraph<AlumnosVertex, AlumnosEdge> gd = GreedyOnGraph.of(graph);
		
		Optional<GraphPath<AlumnosVertex, AlumnosEdge>> gdp = gd.search();
		
		AStar<AlumnosVertex, AlumnosEdge,Integer> aStar = 
				ASBuilder.<AlumnosVertex,AlumnosEdge,Integer>of()
				.graph(graph)
				.bestValue(gdp.get().getWeight())
				.optimalPath(gdp.get())
				.build();
			
		GraphPath<AlumnosVertex, AlumnosEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
	
		SolucionAlumnos s_as = SolucionAlumnos.of(gp);

		System.out.println(s_as);
		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "ficheros_generados/p4/ejemplo3/AlumnosAStarGraph1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red,v.goal()),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}
