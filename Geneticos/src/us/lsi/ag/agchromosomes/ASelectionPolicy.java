package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;

public class ASelectionPolicy implements SelectionPolicy {
	
	public ASelectionPolicy() {
		super();
	}

	public static int TOURNAMENT_ARITY = 2;
	
	public ChromosomePair select(Population population) {
		TournamentSelection ts = new TournamentSelection(TOURNAMENT_ARITY);
		return ts.select(population);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
