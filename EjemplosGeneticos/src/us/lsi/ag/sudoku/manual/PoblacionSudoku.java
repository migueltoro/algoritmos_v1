package us.lsi.ag.sudoku.manual;


import java.util.ArrayList;
import java.util.List;


import us.lsi.ag.manual.Poblacion;

public class PoblacionSudoku implements Poblacion<Sudoku>{
	
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
	
	

}
