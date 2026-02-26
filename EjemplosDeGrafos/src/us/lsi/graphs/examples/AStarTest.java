package us.lsi.graphs.examples;


import java.util.List;
import java.util.Locale;

import org.jgrapht.Graph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.ASBuilder;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.AStar.Type;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;


public class AStarTest {
	
	public static Ciudad ciudad(Graph<Ciudad,Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);		
		
		
		Ciudad start = ciudad(graph,"Sevilla");
		Ciudad end = ciudad(graph,"Almeria");

		EGraph<Ciudad,Carretera> g = 
				EGraph.ofGraph(graph)
				.startVertex(start)
				.goalVertex(v->v.equals(end))
				.endVertex(end)
				.pathType(PathType.Sum)
				.edgeWeight(x->x.km())
				.build();
					
				
		AStar<Ciudad, Carretera, ?> ra = 
				ASBuilder.<Ciudad,Carretera,Integer>of()
				.graph(g)
				.type(Type.Min)
				.build();
		
		List<Carretera> carreteras = ra.search().orElse(null).getEdgeList();
		
		
		GraphColors.<Ciudad,Carretera>toDot(graph,"ficheros/andalucia.gv",x->x.nombre(),x->x.nombre()+"--"+x.km());
		
		GraphColors.toDot(graph,"ficheros/andaluciaAStar.gv",
				x->x.nombre(),
				x->String.format("%.2f",x.km()),
				v->GraphColors.color(Color.black),
				e->GraphColors.styleIf(Style.bold,carreteras.contains(e)));
		
		
		System.out.println(carreteras);
	}

}
