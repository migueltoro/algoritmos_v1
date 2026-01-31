package us.lsi.geneticos.tareasprocesadores.paco;

import static us.lsi.geneticos.tareasprocesadores.paco.MetodosFitness.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements RangeIntegerData<String> {
	
	public Cromosoma(String file) {
		Datos.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}
	
	@Override
	public Integer max(Integer i) {
		return Datos.getNumProcesadores();
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	@Override
	public Integer size() {
		return Datos.getNumTareas();
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return -tiempoProcesadorMasTiempo(cr);
	}
	
	@Override
	public String solution(List<Integer> cr) {
		String s1 = IntStream.range(0, size()).boxed()
		.collect(Collectors.groupingBy(i->cr.get(i))).entrySet().stream()
			.map(e->String.format("P%d: %s", e.getKey(), e.getValue()))
				.collect(Collectors.joining("\n", "Distribucion:\n", "\n"));
		
		String s2 = String.format("Tiempos: %s", tiemposProcesadores(cr));
	
		return s1 + s2;
	}

}