package us.lsi.geneticos.reinas.paco;

import static us.lsi.geneticos.reinas.paco.MetodosFitness.totalDiagPrincipal;
import static us.lsi.geneticos.reinas.paco.MetodosFitness.totalDiagSecundaria;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.ag.Distances;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.common.String2;

public class Cromosoma implements PermutationData<String> {

	private static final int K = 100000;
	private int numeroDeReinas;
	
	public Cromosoma(Integer numReinas) {
		numeroDeReinas = numReinas;
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
	
	@Override
	public Integer size() {
		return numeroDeReinas;
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double tdp = totalDiagPrincipal(cr);
		double d1 = Distances.distanceToEqZero(tdp - numeroDeReinas);
		
		double tds = totalDiagSecundaria(cr);
		double d2 = Distances.distanceToEqZero(numeroDeReinas - tds);
		
		return -K*(d1 + d2);
	}

	@Override
	public String solution(List<Integer> cr) {
		String prefix = String.format("Distribucion:\n%s\n", String2.line(".", 2*numeroDeReinas));
		String sufix = "\n"+String2.line(".", 2*numeroDeReinas);
		return IntStream.range(0, numeroDeReinas).boxed().map(i -> fila(cr.get(i)))
		.collect(Collectors.joining("\n", prefix, sufix));
	}

	private String fila(Integer j) {
		String p1 =  IntStream.range(0, j).boxed().map(i->"_")
			.collect(Collectors.joining(" "));
		
		String p2 =  IntStream.range(j+1, numeroDeReinas).boxed().map(i->"_")
			.collect(Collectors.joining(" "));
		
		String p = p1.length()>0? " ": "";
		return p1 + p + "R " + p2;
	}
}
