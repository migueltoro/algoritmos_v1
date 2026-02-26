package us.lsi.alg.investigadores;

import org.jgrapht.GraphPath;


import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.alg.PDRBuilder;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestPDR {

	public static void main(String[] args) {
		DatosInv.iniDatos("ficheros/investigadores/inv3.txt");
		DatosInv.toConsole();
		
		EGraph<InvVertex, InvEdge> graph = EGraph.virtual(InvVertexI.first())
				.pathType(PathType.Last)
				.vertexWeight(v->v.fo().doubleValue())
				.heuristic(InvHeuristic::heuristic).build();
		
		GreedyOnGraph<InvVertex, InvEdge> gd = GreedyOnGraph.of(graph);
		GraphPath<InvVertex, InvEdge> pgd = gd.path();
		System.out.println(pgd.getEndVertex().fo());
	
		PDR<InvVertex, InvEdge, SolucionInv> ms = 
				PDRBuilder.<InvVertex, InvEdge,SolucionInv>of()
				.graph(graph)
				.type(PDR.Type.Max)
				.build();
		
		GraphPath<InvVertex,InvEdge> path = ms.search().get();
		SolucionInv s = SolucionInv.of(path);
		System.out.println(s);
	}

}
