package us.lsi.alg.asignaturas;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.ASBuilder;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestAstar {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice v0 = AsignaturasVertice.inicial();

		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafo = 
				EGraph.virtual(v0)
				.pathType(PathType.Last)
				.vertexWeight(v->(double)v.getPeso())
				.heuristic(Heuristica::heuristic)
				.build();
	
		
		AStar<AsignaturasVertice, AsignaturasEdge, SolucionAsignaturas> as = 
				ASBuilder.<AsignaturasVertice,AsignaturasEdge,SolucionAsignaturas>of()
				.graph(grafo)
				.type(AStar.Type.Max)
				.build();
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionAsignaturas.of(s1));
	}

}
