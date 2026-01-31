package us.lsi.p3.ej_1.paco;

import static us.lsi.p3.ej_1.paco.MetodosFitness.*;

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
		return Datos.getNumElementos();
	}	
	
	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	@Override
	public Integer max(Integer i) {
		return Datos.getMultiplicidad(i) + 1;
	}

	@Override
	public Solucion solution(List<Integer> ls) {
		return Solucion.of(ls);
	}	
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double st = sumaTotal(cr);
		double d = Distances.distanceToEqZero(st-Datos.SUMA);

		return -totalElementos(cr) -K*d;
	}

}