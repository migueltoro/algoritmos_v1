package us.lsi.geneticos.estanteria.paco;

import static us.lsi.geneticos.estanteria.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements RangeIntegerData<Solucion> {

	private static final int K = 100000;
	
	public Cromosoma(String linea) {
		Datos.iniDatos(linea);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}

	@Override
	public Integer size() {
		return Datos.getNumLibros();
	}	
	
	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	@Override
	public Integer max(Integer i) {
		return Datos.getNumEstantes() + 1;
	}

	@Override
	public Solucion solution(List<Integer> ls) {
		return Solucion.of(ls);
	}	
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double t1 = totalMasAltura(cr);
		double d1 = Distances.distanceToEqZero(t1);
		
		double t2 = totalMasAnchura(cr);
		double d2 = Distances.distanceToEqZero(t2);

		return totalLibros(cr) -K*(d1 + d2);
	}

}