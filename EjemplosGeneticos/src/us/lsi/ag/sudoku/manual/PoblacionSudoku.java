package us.lsi.ag.sudoku.manual;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.ag.manual.Poblacion;

public class PoblacionSudoku implements Poblacion<Sudoku>{
	
	public static Double porcentaje_conservacion_reboot = 0.1;
	
	public static PoblacionSudoku of(List<Sudoku> individuos) {
		return new PoblacionSudoku(individuos);
	}
	
	public static PoblacionSudoku of() {
		return new PoblacionSudoku(new ArrayList<>());
	}

	private List<Sudoku> individuals;
	
	private PoblacionSudoku(List<Sudoku> individuos) {
		super();
		this.individuals = individuos;
	}

	@Override
	public Sudoku tournament() {
		int k = 3;
		Sudoku mejor = null;
        int bestFit = Integer.MAX_VALUE;

        for (int i = 0; i < k; i++) {
            Sudoku ind = this.individuals().get(Sudoku.rand.nextInt(this.size()));
            int fit = ind.fitness().intValue();
            if (fit < bestFit) {
                bestFit = fit;
                mejor = ind;
            }
        }
        return mejor;
	}
	
	@Override
	public Integer size() {
		return individuals.size();
	}

	@Override
	public List<Sudoku> individuals() {
		return individuals;
	}

	@Override
	public void add(Sudoku e) {
	    this.individuals.add(e);		
	}

	@Override
	public PoblacionSudoku reboot() {
		Integer n = this.size();
		Integer s1 = (int)(n*PoblacionSudoku.porcentaje_conservacion_reboot);
		Integer ind = Sudoku.rand.nextInt(n);
		Sudoku sd = this.individuals.get(ind);
		List<Sudoku> crs = this.individuals.stream()
				.sorted(Comparator.comparing(Sudoku::fitness))
				.limit(s1)
				.collect(Collectors.toList());
		for (int i = s1; i < n; i++) {
			crs.add(sd.generateIndividual());
		}
		return PoblacionSudoku.of(crs);
	}

	@Override
	public Sudoku best() {
		return this.individuals.stream()
				.min(Comparator.comparing(Sudoku::fitness)).get();
	}

}
