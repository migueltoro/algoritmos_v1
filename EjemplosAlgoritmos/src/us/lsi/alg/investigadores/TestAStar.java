package us.lsi.alg.investigadores;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.ASBuilder;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;


public class TestAStar {

	public static void main(String[] args) {
			DatosInv.iniDatos("ficheros/investigadores/inv1.txt");
			DatosInv.toConsole();
			
			EGraph<InvVertex, InvEdge> graph = EGraph.virtual(InvVertexI.first())
						.pathType(PathType.Last)
						.vertexWeight(v->v.fo().doubleValue())
						.heuristic(InvHeuristic::heuristic).build();
			
			GreedyOnGraph<InvVertex, InvEdge> gd = GreedyOnGraph.of(graph);
			GraphPath<InvVertex, InvEdge> pgd = gd.path();
			System.out.println(pgd.getEndVertex());
			
			AStar<InvVertex, InvEdge,SolucionInv> ms = 
					ASBuilder.<InvVertex, InvEdge,SolucionInv>of()
					.graph(graph)
					.type(AStar.Type.Max)
					.bestValue(pgd.getWeight())
					.optimalPath(pgd)
					.build();
					
			
			GraphPath<InvVertex,InvEdge> path = ms.search().get();
			SolucionInv s = SolucionInv.of(path);
			System.out.println(s);
	}

}
