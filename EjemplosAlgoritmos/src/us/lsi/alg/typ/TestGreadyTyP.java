package us.lsi.alg.typ;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.alg.typ.DatosTyP.Tarea;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;

public class TestGreadyTyP {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		List<Tarea> tareas = DatosTyP.tareas;
		Collections.sort(tareas,Comparator.<Tarea,Integer>comparing(t->t.duracion()).reversed());
//		Collections.shuffle(tareas);
		
		EGraph<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> graph = 
				EGraph.virtual(e1)
				.pathType(PathType.Last)
				.vertexWeight(v->v.maxCarga())
				.build();	
		
		GreedyOnGraph<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> ms = GreedyOnGraph.of(graph);	
		
		GraphPath<TyPVertex, SimpleEdgeAction<TyPVertex, Integer>> path = ms.path();
		
		System.out.println(SolucionTyP.of(path));
		
		System.out.println(path.getWeight());
	}

}
