package us.lsi.p4.ej_3;


import java.util.function.Predicate;

import org.jgrapht.GraphPath;


import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.alg.BT.Type;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {

		DatosAlumnos.iniDatos("ficheros/p4/alumnos_1.txt");

		AlumnosVertex vInicial = AlumnosVertex.initial();
		
		EGraph<AlumnosVertex, AlumnosEdge> graph = //(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
			EGraph.virtual(vInicial)
				.pathType(PathType.Sum)
				.heuristic(AlumnosHeuristic::heuristic)
				.build();

		GreedyOnGraph<AlumnosVertex, AlumnosEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<AlumnosVertex, AlumnosEdge> path = alg_voraz.path();

		BT<AlumnosVertex, AlumnosEdge, SolucionAlumnos> alg_bt = null;
		
		if (path != null) {
			alg_bt = BTBuilder.<AlumnosVertex, AlumnosEdge, SolucionAlumnos>of()
			.graph(graph)
			.type(Type.Max)
			.bestValue(path.getWeight())
			.optimalPath(path)
			.build();
		} else {
			BTBuilder.<AlumnosVertex, AlumnosEdge, SolucionAlumnos>of()
			.graph(graph)
			.type(Type.Max)
			.build();
		}
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<AlumnosVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<AlumnosEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "ficheros_generados/p4/ejemplo3/AlumnosBTGraph1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	

		if(res!=null)
			System.out.println("Solucion BT: " + SolucionAlumnos.of(res) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
		
	}	

}
