package us.lsi.geneticos.recubrimientoconjuntos.paco;

import static us.lsi.geneticos.recubrimientoconjuntos.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.BinaryData;
import us.lsi.ag.Distances;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements BinaryData<Solucion> {
	
	private static final int K = 100000;
	
	public Cromosoma(String fichero) {
		Datos.iniDatos(fichero);
	}

	@Override
	public Integer size() {
		return Datos.getNumSubconjuntos();
	}
	
	@Override
	public Solucion solution(List<Integer> ls) {
		return Solucion.create(ls);
	}
			
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double te = totalElementos(cr);
		double d = Distances.distanceToEqZero(te-Datos.getNumElementos());

		return -pesoTotal(cr) - K*d;
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
}
