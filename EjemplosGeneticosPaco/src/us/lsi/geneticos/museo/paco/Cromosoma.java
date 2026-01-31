package us.lsi.geneticos.museo.paco;

import static us.lsi.geneticos.museo.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements RangeIntegerData<Solucion> {

	private static final int K = 100000;
	
	public Cromosoma(String file) {
		Datos.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}
	
	@Override
	public Integer size() {
		return Datos.getNumObras();
	}
	
	@Override
	public Integer max(Integer i) {
		return Datos.getNumSalas() + 1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double te = totalEstilos(cr);
		double d1 = Distances.distanceToEqZero(te);
		
		double ts = totalSalas(cr);
		double d2 = Distances.distanceToEqZero(ts);

		return interesTotal(cr) - K*(d1 + d2);
	}

	@Override
	public Solucion solution(List<Integer> cr) {
		return Solucion.of(cr);
	}
	
}