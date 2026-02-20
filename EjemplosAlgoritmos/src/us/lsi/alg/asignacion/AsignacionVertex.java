package us.lsi.alg.asignacion;

import java.util.Comparator;
import java.util.List;

import us.lsi.common.IntPair;
import us.lsi.common.IntegerSet;
import us.lsi.graphs.virtual.VirtualVertex;
import us.lsi.streams.Stream2;

public record AsignacionVertex(IntegerSet agentes,  IntegerSet tareas) 
		implements VirtualVertex<AsignacionVertex,AsignacionEdge,IntPair> { 
	
	public static AsignacionVertex of(IntegerSet agentes, IntegerSet tareas) {
        return new AsignacionVertex(agentes, tareas);
    }
	
	public static AsignacionVertex inicial() {
        return new AsignacionVertex(IntegerSet.empty(), IntegerSet.empty());
    }
	
	@Override
	public List<IntPair> actions() {
		return Stream2.allPairs(Asignacion.tmax, Asignacion.tmax)
				.filter(p->!agentes.contains(p.first()) && !tareas.contains(p.second()))
				.sorted(Comparator.comparing(p->Asignacion.costes(p.first(), p.second())))
				.toList();
	}
	@Override
	public AsignacionVertex neighbor(IntPair a) {
		return AsignacionVertex.of(this.agentes.addF(a.first()), this.tareas.addF(a.second()));
	}
	@Override
	public AsignacionEdge edge(IntPair a) {
		return AsignacionEdge.of(this, this.neighbor(a), a);
	}
	
	@Override
	public Boolean isValid() {
		return true;
	}
	
	@Override
	public Boolean goalHasSolution() {
		return true;
	}
	
	@Override
	public Boolean goal() {
		return this.agentes.size() == Asignacion.tmin && this.tareas.size() == Asignacion.tmin;
	}
	
	public IntPair greedyAction() {
		return this.actions().get(0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

