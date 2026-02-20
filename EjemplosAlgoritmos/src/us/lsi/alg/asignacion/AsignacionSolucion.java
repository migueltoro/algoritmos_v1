package us.lsi.alg.asignacion;

import java.util.List;

import org.jgrapht.GraphPath;

import us.lsi.common.IntPair;

public record AsignacionSolucion(Double coste, List<IntPair> asignaciones) {

	public static AsignacionSolucion of(Double coste, List<IntPair> asignaciones) {
		return new AsignacionSolucion(coste, asignaciones);
	}
	
	public static AsignacionSolucion of(GraphPath<AsignacionVertex, AsignacionEdge> path) {
		Double coste = path.getWeight();
		List<IntPair> asignaciones = path.getEdgeList().stream().map(e -> e.action()).toList();
		return AsignacionSolucion.of(coste, asignaciones);		
	}
	@Override
	public String toString() {
		return String.format("Coste: %f, Asignaciones: %s", coste, asignaciones);
	}

}
