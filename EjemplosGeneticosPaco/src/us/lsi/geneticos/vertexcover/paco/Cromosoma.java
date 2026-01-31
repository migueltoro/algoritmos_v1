package us.lsi.geneticos.vertexcover.paco;

import static us.lsi.geneticos.vertexcover.paco.MetodosFitness.*;

import java.util.List;
import java.util.stream.IntStream;
import us.lsi.ag.BinaryData;
import us.lsi.ag.Distances;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.grafos.datos.Ciudad;

public class Cromosoma implements BinaryData<List<Ciudad>>{

	private static final int K = 100000;
	
	public Cromosoma(String file) {
		Datos.iniDatos(file);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
	@Override
	public Integer size() {
		return Datos.getNumCiudades();
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double ta = aristasRestantes(cr);
		double d = Distances.distanceToEqZero(ta);
		
		return -getPesoTotal(cr) - K*d;
	}
	
	@Override
	public List<Ciudad> solution(List<Integer> cr) {
		return IntStream.range(0, size())
			.filter(i->cr.get(i)>0).mapToObj(i->Datos.getCiudad(i)).toList();
	}

}