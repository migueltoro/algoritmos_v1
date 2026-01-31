package us.lsi.geneticos.agentestareas.paco;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements PermutationData<String> {

	public Cromosoma(String file) {
		Datos.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
	
	@Override
	public Integer size() {
		return Datos.getN();
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return -costeTotal(cr);
	}
	
	private double costeTotal(List<Integer> cr) {
		return IntStream.range(0, size()).mapToDouble(i->Datos.getCoste(i, cr.get(i))).sum();
	}
	
	@Override
	public String solution(List<Integer> cr) {
		String s1 = IntStream.range(0, size())
		.mapToObj(i -> String.format("Agente %d -> Tarea %d", i, cr.get(i)))
		.collect(Collectors.joining("\n\t", "Asignacion:\n\t", "\n"));
		
		return String.format("%s\nCoste Total: %.1f", s1, costeTotal(cr));
	}

}