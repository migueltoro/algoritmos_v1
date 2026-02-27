package us.lsi.alg.reinas;


import java.util.Optional;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {
		ReinasVertexI.n = 10;
		ReinasVertex e1 = ReinasVertex.first();
		
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
				EGraph.virtual(e1)
				.pathType(PathType.Last)
				.vertexWeight(v->v.errores().doubleValue())
				.build();

		BT<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>, SolucionReinas> ms = 
				BTBuilder.<ReinasVertex, SimpleEdgeAction<ReinasVertex,Integer>,SolucionReinas>of()
				.graph(graph)
				.type(BT.Type.All)
				.solutionNumber(2)
				.build();
				

		Optional<GraphPath<ReinasVertex, SimpleEdgeAction<ReinasVertex, Integer>>> gp = ms.search();
		System.out.println(SolucionReinas.of(gp.get()));
//		ms.getSolutions().stream().forEach(s->System.out.println(s));
		System.out.println(ms.getSolutions().size());
	}
}
