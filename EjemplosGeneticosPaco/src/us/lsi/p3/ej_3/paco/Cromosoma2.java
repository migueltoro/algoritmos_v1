package us.lsi.p3.ej_3.paco;

import static us.lsi.p3.ej_3.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2 implements PermutationData<Solucion> {
	
	private static final int K = 10000;

	public Cromosoma2(String fichero) {
		Datos.iniDatos(fichero);
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double ca = totalCeroAfinP(cr);
		double d = Distances.distanceToEqZero(ca);
		
		return afinidadTotalP(cr) - K*d;
	}

	@Override
	public Solucion solution(List<Integer> ls) {
		Solucion res = Solucion.empty();
		for(int i=0; i<ls.size(); i++) {
			res.add(ls.get(i), i/Datos.getTamGrupo());
		}
		return res;
	}

	@Override
	public Integer size() {
		return Datos.getNumAlumnos();
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
}

