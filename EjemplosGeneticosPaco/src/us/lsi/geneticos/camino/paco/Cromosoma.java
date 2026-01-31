package us.lsi.geneticos.camino.paco;

import static us.lsi.geneticos.camino.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.common.List2;

public class Cromosoma implements PermutationData<String> {
	
	private static final int K = 100000;

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
	
	@Override
	public Integer size() {
		return Datos.getNumVertices() - 2;
	}
	
	@Override
	public List<Integer> normalSequence() {
		return Datos.restoVertices();
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double pv = predicadoVertices(cr);
		double d1 = Distances.distanceToGeZero(pv-2);
		
		double pa = predicadoAristas(cr);
		double d2 = Distances.distanceToGeZero(pa-2);
		
		return -pesoTotal(cr) - K*(d1 + d2);
	}
	
	@Override
	public String solution(List<Integer> cr) {
		return "Kms: "+ pesoTotal(cr) + "\n" +
		
		List2.addLast(List2.addFirst(cr, Datos.ORIGEN), Datos.DESTINO).stream()
		.map(i->Datos.getVertice(i)).toList();
	}

}