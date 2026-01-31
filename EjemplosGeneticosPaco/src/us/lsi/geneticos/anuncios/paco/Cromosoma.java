package us.lsi.geneticos.anuncios.paco;

import static us.lsi.geneticos.anuncios.paco.MetodosFitness.totalIncompatibles;
import static us.lsi.geneticos.anuncios.paco.MetodosFitness.valorTotal;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.anuncios.datos.DatosAnuncios;

public class Cromosoma extends DatosAnuncios implements PermutationData<Solucion> {

	private static final int K = 100000;
	
	public Cromosoma(String file) {
		super();
		super.leeYOrdenaAnuncios(file);
	}	
	
	@Override
	public Solucion solution(List<Integer> dc) {		
		return  Solucion.of(dc);
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {	
		double tt = totalIncompatibles(cr);
		double d = Distances.distanceToEqZero(tt);		
		return valorTotal(cr) - K*d;
	}

	@Override
	public Integer size() {
		return DatosAnuncios.todosLosAnunciosDisponibles.size();
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
	
}
