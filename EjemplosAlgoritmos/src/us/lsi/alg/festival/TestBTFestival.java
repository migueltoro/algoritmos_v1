package us.lsi.alg.festival;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestBTFestival {

	public static void main(String[] args) {
		DatosFestival.iniDatos("ficheros/festival/DatosEntrada2.txt");
		DatosFestival2.iniDatos();
		FestivalVertex v1 = FestivalVertex.initial();
		EGraph<FestivalVertex, FestivalEdge> graph = 
				EGraph.virtual(v1)
				.pathType(PathType.Sum)
				.heuristic(Greedy::heuristic)
				.build();		
		GraphPath<FestivalVertex, FestivalEdge> path = Greedy.greedy(v1,graph);
		System.out.println("G1 "+path.getWeight());
		BT<FestivalVertex,FestivalEdge,GraphPath<FestivalVertex, FestivalEdge>> ms = 
				BTBuilder.<FestivalVertex,FestivalEdge,GraphPath<FestivalVertex, FestivalEdge>>of()
				.graph(graph)
				.type(BT.Type.Min)
				.bestValue(path.getWeight())
				.optimalPath(path)
				.build();
	
		GraphPath<FestivalVertex, FestivalEdge> gp = ms.search().get();
		System.out.println("BT "+gp.getWeight());
	}

}
