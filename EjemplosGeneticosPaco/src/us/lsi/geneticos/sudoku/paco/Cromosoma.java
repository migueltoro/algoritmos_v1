package us.lsi.geneticos.sudoku.paco;

import static us.lsi.geneticos.sudoku.paco.MetodosFitness.*;

import java.util.List;
import us.lsi.ag.Distances;
import us.lsi.ag.InSetData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma  implements InSetData<Solucion> {
	
	private static final int K = 100000;
	
	public Cromosoma(String file) {
		Datos.iniDatos(file);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.InSet;
	}

	@Override
	public Integer size() {
		return Datos.getNumGenes();
	}
	
	@Override
	public List<Integer> values(Integer i) {
		return Datos.getValoresGen(i);
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		List<List<Integer>> m = Datos.montaMatriz(cr);
		
		double tf = totalFilas(m);
		double d1 = Distances.distanceToEqZero(tf);
		
		double tc = totalColumnas(m);
		double d2 = Distances.distanceToEqZero(tc);
		
		double tsc = totalSubCuadrados(m);
		double d3 = Distances.distanceToEqZero(tsc);

		return -K*(d1 + d2 + d3);
	}

	@Override
	public Solucion solution(List<Integer> cr) {
		return Solucion.of(cr);
	}

}