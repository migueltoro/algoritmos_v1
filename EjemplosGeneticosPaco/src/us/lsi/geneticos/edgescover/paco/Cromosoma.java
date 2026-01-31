package us.lsi.geneticos.edgescover.paco;

import static us.lsi.geneticos.edgescover.paco.MetodosFitness.*;

import java.util.List;
import java.util.stream.IntStream;
import us.lsi.ag.BinaryData;
import us.lsi.ag.Distances;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.grafos.datos.Carretera;

public class Cromosoma implements BinaryData<List<Carretera>>{

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
		return Datos.getNumAristas();
	}
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double tc = totalOtraComponente(cr);
		double d = Distances.distanceToEqZero(tc);
		
		return -totalAristas(cr) - K*d;
	}
	
	@Override
	public List<Carretera> solution(List<Integer> cr) {
		return IntStream.range(0, size())
			.filter(i->cr.get(i)>0).mapToObj(i->Datos.getCarretera(i)).toList();
	}

}