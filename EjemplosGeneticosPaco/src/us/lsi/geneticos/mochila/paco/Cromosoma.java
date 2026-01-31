package us.lsi.geneticos.mochila.paco;

import static us.lsi.geneticos.mochila.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.mochila.datos.DatosMochila;

public class Cromosoma implements RangeIntegerData<Solucion> {

	private static final int K = 100000;
	
	public Cromosoma(String fichero) {
		DatosMochila.iniDatos(fichero);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}
	
	@Override
	public Integer size() {
		return DatosMochila.getObjetos().size();
	}
	
	@Override
	public Integer max(Integer i) {
		return DatosMochila.getObjetos().get(i).numMaxDeUnidades()+1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double pt = pesoTotal(cr);
		double d = Distances.distanceToGeZero(DatosMochila.capacidadInicial - pt);

		return valorTotal(cr) - K*d;
	}

	@Override
	public Solucion solution(List<Integer> cr) {
		return Solucion.of(cr);
	}
	
}