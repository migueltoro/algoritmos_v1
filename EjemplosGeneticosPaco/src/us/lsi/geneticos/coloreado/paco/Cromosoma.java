package us.lsi.geneticos.coloreado.paco;

import static us.lsi.geneticos.coloreado.paco.MetodosFitness.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.ag.Distances;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.grafos.datos.Ciudad;

public class Cromosoma implements RangeIntegerData<Map<Ciudad,Integer>> {
	
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
		return Datos.getNumCiudades();
	}
	
	@Override	
    public Integer max(Integer index){
		return Datos.MaxNumColors;	
	}

	@Override	
    public Integer min(Integer index){
		return 0;		
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double ti = totalIncompatibles(cr);
		double d = Distances.distanceToEqZero(ti);
		
		return -totalColores(cr) - K*d;		
	}

	@Override
	public Map<Ciudad,Integer> solution(List<Integer> cr) {
		return IntStream.range(0, cr.size()).boxed()
			.collect(Collectors.toMap(i->Datos.getCiudad(i),i->cr.get(i)));
	}

}
