package us.lsi.alg.asignacion;

import java.util.Comparator;
import java.util.function.Predicate;

import us.lsi.common.IntPair;
import us.lsi.streams.Stream2;


public class Heuristica {

	public static Double heuristic(AsignacionVertex v1, Predicate<AsignacionVertex> pd, AsignacionVertex v2) {
		Integer index = v1.agentes().size();
		if (pd.test(v1)) return 0.;
		IntPair pt = Stream2.allPairs(Asignacion.tmax, Asignacion.tmax)
		.filter(p->!v1.agentes().contains(p.first()) && !v1.tareas().contains(p.second()))
		.min(Comparator.comparing(p->Asignacion.costes(p.first(), p.second())))
		.orElseThrow();
		return (Asignacion.tmin-index)*Asignacion.costes(pt.first(), pt.second());
	}

}
