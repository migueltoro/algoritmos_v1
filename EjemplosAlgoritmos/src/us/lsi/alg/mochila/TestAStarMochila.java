package us.lsi.alg.mochila;


import java.util.Locale;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.ASBuilder;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.path.EGraphPath.PathType;

public class TestAStarMochila {


	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.iniDatos("ficheros/mochila/objetosMochila.txt");
//		Integer n = DatosMochila.numeroDeObjetos;
//		MochilaVertex.capacidadInicial = 2457;
		MochilaVertex.capacidadInicial = 78;
		System.out.println(Double.MAX_VALUE);
		MochilaVertex e1 = MochilaVertex.initialVertex();
		
		EGraph<MochilaVertex, MochilaEdge> graph = 
				EGraph.virtual(e1)
				.pathType(PathType.Sum)
				.heuristic(MochilaHeuristic::heuristic1)
				.build();
		
		
		GreedyOnGraph<MochilaVertex, MochilaEdge> rr = GreedyOnGraph.of(graph);
		
		GraphPath<MochilaVertex, MochilaEdge> gp = rr.path();
		
		System.out.println(gp.getWeight());
	
		AStar<MochilaVertex, MochilaEdge,SolucionMochila> ms = 
				ASBuilder.<MochilaVertex, MochilaEdge,SolucionMochila>of()
				.graph(graph)
				.type(AStar.Type.Max)
				.bestValue(gp.getWeight())
				.optimalPath(gp)
				.build();
				
		
		GraphPath<MochilaVertex, MochilaEdge> path = ms.search().get();
		SolucionMochila s = MochilaVertex.getSolucion(path);
		System.out.println(s);
		SimpleDirectedGraph<MochilaVertex, MochilaEdge> r = ms.outGraph();
		System.out.println(ms.tree.keySet().size());
		
		GraphColors.toDot(r,"ficheros/MochilaAstarGraph.gv",
				v->String.format("((%d,%d)",v.index(),v.capacidadRestante()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,path.getVertexList().contains(v)),
				e->GraphColors.colorIf(Color.red,path.getEdgeList().contains(e))
				);
	}

}
