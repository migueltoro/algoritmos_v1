package us.lsi.alg.investigadores;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {
		DatosInv.iniDatos("ficheros/investigadores/inv3.txt");
		DatosInv.toConsole();
		
		EGraph<InvVertex, InvEdge> graph = EGraph.virtual(InvVertex.first())
				.pathType(PathType.Last)
				.vertexWeight(v->v.fo().doubleValue())
				.heuristic(InvHeuristic::heuristic).build();
		
		GreedyOnGraph<InvVertex, InvEdge> gd = GreedyOnGraph.of(graph);
		GraphPath<InvVertex, InvEdge> pgd = gd.path();
		System.out.println(pgd.getWeight());
	
		BT<InvVertex, InvEdge,SolucionInv> ms = 
				BTBuilder.<InvVertex, InvEdge,SolucionInv>of()
				.graph(graph)
				.type(BT.Type.Max)
				.bestValue(pgd.getWeight())
				.optimalPath(pgd)
				.build();
			
		
		GraphPath<InvVertex,InvEdge> path = ms.search().get();
		SolucionInv s = SolucionInv.of(path);
		System.out.println(s);

	}

}
