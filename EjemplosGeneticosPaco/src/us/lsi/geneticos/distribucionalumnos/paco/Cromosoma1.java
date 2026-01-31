package us.lsi.geneticos.distribucionalumnos.paco;

import static us.lsi.geneticos.distribucionalumnos.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements RangeIntegerData<Solucion> {
	
	private static final int K = 10000;

	public Cromosoma1(String fichero) {
		Datos.iniDatos(fichero);
	}

	@Override
	public Integer size() {
		return Datos.getNumAlumnos();
	}

	@Override
	public Integer max(Integer i) {
		return Datos.getNumGrupos();
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double ca = totalCeroAfinR(cr);
		double d1 = Distances.distanceToEqZero(ca);
		
		double tg = totalGruposR(cr);
		double d2 = Distances.distanceToEqZero(tg);
		
		return afinidadTotalR(cr) -K*(d1 + d2);
	}

	@Override
	public Solucion solution(List<Integer> ls) {
		Solucion res = Solucion.empty();
		for(int i=0; i<ls.size(); i++) {
			res.add(i, ls.get(i));
		}
		return res;
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}
}