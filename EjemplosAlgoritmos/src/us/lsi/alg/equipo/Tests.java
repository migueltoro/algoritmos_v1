package us.lsi.alg.equipo;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.ASBuilder;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BTBuilder;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.alg.PDRBuilder;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class Tests {
	
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		tests("ficheros/equipo/DatosEquipo1.txt");
		tests("ficheros/equipo/DatosEquipo2.txt");
		tests("ficheros/equipo/DatosEquipo3.txt");
	}

	private static void tests(String fichero) {
		
		DatosEquipo.iniDatos(fichero);
		
		EGraph<EquipoVertex, EquipoEdge> g = 
				EGraph.virtual(EquipoVertex.first())
				.pathType(PathType.Sum)
				.heuristic((v1,p,v2)->1000.)
				.build();
		
		testPDR(g);
		testBT(g);
		testAStar(g);
	}

	
	private static void testPDR(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== PDR ======================== ");
//		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		PDR<EquipoVertex, EquipoEdge, ?> alg_pdr = 
			PDRBuilder.<EquipoVertex,EquipoEdge,SolucionEquipo>of()
				.graph(grafo)
				.type(PDR.Type.Max)
				.build();
		
		Optional<GraphPath<EquipoVertex, EquipoEdge>> p = alg_pdr.search();
		System.out.println(EquipoVertex.getSolucion(p.get()));
	}
	
	private static void testBT(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== BT ======================== ");
		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		BT<EquipoVertex, EquipoEdge,SolucionEquipo> alg_bt = 
				BTBuilder.<EquipoVertex,EquipoEdge,SolucionEquipo>of()
				.graph(grafo)
				.type(BT.Type.Max)
				.bestValue(gp.getWeight())
				.optimalPath(gp)
				.build();
			
		Optional<GraphPath<EquipoVertex, EquipoEdge>> p = alg_bt.search();
	    System.out.println(EquipoVertex.getSolucion(p.get()));
	}

	private static void testAStar(EGraph<EquipoVertex, EquipoEdge> grafo) {
		System.out.println("======================== A* ======================== ");
		GraphPath<EquipoVertex, EquipoEdge> gp = GreedyOnGraph.random(grafo).path();
		AStar<EquipoVertex, EquipoEdge,?> alg_star = 
				ASBuilder.<EquipoVertex,EquipoEdge,SolucionEquipo>of()
				.graph(grafo)
				.type(AStar.Type.Max)
				.bestValue(gp.getWeight())
				.optimalPath(gp)
				.build();

		Optional<GraphPath<EquipoVertex, EquipoEdge>> p = alg_star.search();
		System.out.println(EquipoVertex.getSolucion(p.get()));
	}

}
