package us.lsi.geneticos.hittingset.paco;

import static us.lsi.geneticos.hittingset.paco.MetodosFitness.*;

import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements BinaryData<Solucion> { // Hitting Set
	
	private static final int K = 100000;
	
	public Cromosoma(String fichero) {
		Datos.iniDatos(fichero);
	}

	@Override
	public Integer size() {
		return Datos.getNumElementos();
	}
	
	@Override
	public Solucion solution(List<Integer> ls) {
		return Solucion.create(ls);
	}
			
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return -totalElementos(cr) - K*totalNoIntersec(cr);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
}