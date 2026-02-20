package us.lsi.alg.typ;

import java.util.List;

import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.VirtualVertex;

public interface TyPVertex extends 
	VirtualVertex<TyPVertex, SimpleEdgeAction<TyPVertex,Integer>, Integer> {
	
	Integer index();
	
	List<Double> cargas();

	Integer npMax();

	Integer npMin();

	Double maxCarga();

	List<Double> cargasDespues(Integer a);
	
	public static TyPVertex first() {
		return TyPVertexI.first();
	}

}