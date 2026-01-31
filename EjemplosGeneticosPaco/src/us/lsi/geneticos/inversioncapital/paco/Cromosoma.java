package us.lsi.geneticos.inversioncapital.paco;

import static us.lsi.geneticos.inversioncapital.paco.MetodosFitness.numInversiones;
import static us.lsi.geneticos.inversioncapital.paco.MetodosFitness.totalInvertido;
import static us.lsi.geneticos.inversioncapital.paco.MetodosFitness.valorTotal;

import java.util.List;
import java.util.stream.IntStream;
import us.lsi.ag.BinaryData;
import us.lsi.ag.Distances;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma implements BinaryData<String> {
	
	public static int R, S, U, V;
	private static final int K = 100000;
	
	public Cromosoma(String fichero) {
		R = 1; S = 2; U = 3; V = 4;
	}

	@Override
	public Integer size() {
		return Datos.getNumInversiones();
	}
	
	@Override
	public String solution(List<Integer> cr) {
		String s1 = "Inversiones: " + IntStream.range(0, size()).filter(i->cr.get(i)>0)
			.mapToObj(i->"nº "+(i+1)).toList();

		String s2 = "\nBeneficio: "+IntStream.range(0, size()).filter(i->cr.get(i)>0)
		.mapToDouble(i -> Datos.getValor(i) - Datos.getCantidad(i)).sum();
		
		return s1+s2; 
	}
			
	@Override
	public Double fitnessFunction(List<Integer> cr) {
		double ti = totalInvertido(cr);
		double d1 = Distances.distanceToLeZero(ti-Datos.TOTAL);
		
		double ni = numInversiones(cr);
		double d2 = Distances.distanceToLeZero(ni-2);
		
		double d3 = Distances.distanceToLeZero(cr.get(R)-cr.get(S)+0.);
		
		double d4 = Distances.distanceToLeZero(cr.get(U)+cr.get(V)-1.);

		return valorTotal(cr) - K*(d1+d2+d3+d4);
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}
	
}
