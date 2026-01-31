package us.lsi.geneticos.bufete.paco;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.bufete.datos.DatosBufete;
import us.lsi.bufete.datos.SolucionBufete;

public class Cromosoma implements RangeIntegerData<SolucionBufete> {

	public Cromosoma(String fichero) {
		DatosBufete.iniDatos(fichero);
	}	

	@Override
	public ChromosomeType type() {
		return ChromosomeType.RangeInteger;
	}	
	
	@Override
	public Integer size() {
		return DatosBufete.NUM_CASOS;
	}

	@Override
	public Integer max(Integer i) {
		return DatosBufete.NUM_ABOGADOS;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}	
	
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return -tiempoTotal(cr) ;
	}

	private double tiempoTotal(List<Integer> cr) {
		return IntStream.range(0, cr.size()).boxed()
		.collect(Collectors.groupingBy(i->cr.get(i),
				Collectors.summingDouble(i->DatosBufete.getHoras(cr.get(i), i))))
		.values().stream().max(Comparator.naturalOrder()).get();
	}
	
	@Override
	public SolucionBufete solution(List<Integer> cr) {
		return SolucionBufete.create(cr);
	}

}