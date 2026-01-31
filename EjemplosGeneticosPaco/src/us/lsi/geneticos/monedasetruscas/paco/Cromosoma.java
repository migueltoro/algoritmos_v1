package us.lsi.geneticos.monedasetruscas.paco;

import static us.lsi.geneticos.monedasetruscas.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements RangeIntegerData<Solucion> {

	public enum TIPO {MAX, MIN};
	
	private int tipo;
	private static final int K = 100000;
	
	public Cromosoma(TIPO tp, String file) {
		tipo = tp.equals(TIPO.MAX)? 1: -1;
		Datos.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}

	@Override
	public Integer size() {
		return Datos.getNumMonedas();
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
	public Double fitnessFunction(List<Integer> cr) {
		double st = valorTotal(cr);
		double d = Distances.distanceToEqZero(st-Datos.VALOR);

		return tipo*pesoTotal(cr) -K*d;
	}
	
	@Override
	public Solucion solution(List<Integer> ls) {
		return Solucion.of(ls);
	}	

}