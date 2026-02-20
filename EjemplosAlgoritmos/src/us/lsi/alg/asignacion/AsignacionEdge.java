package us.lsi.alg.asignacion;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record AsignacionEdge (AsignacionVertex source, AsignacionVertex target, IntPair action, Double weight) 
	implements SimpleEdgeAction<AsignacionVertex,IntPair> {

	public static AsignacionEdge of(AsignacionVertex c1, AsignacionVertex c2, IntPair action) {
		return new AsignacionEdge(c1, c2, action, Asignacion.costes(action.first(), action.second()));
	}
}
