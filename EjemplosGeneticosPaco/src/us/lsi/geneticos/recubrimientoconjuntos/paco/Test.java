package us.lsi.geneticos.recubrimientoconjuntos.paco;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Test {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 5000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Cromosoma cr = new Cromosoma("ficheros/geneticos/recubrimientoconjuntos/conjuntos1.txt");
		AlgoritmoAG<List<Integer>,Solucion> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");
		
		cr = new Cromosoma("ficheros/geneticos/recubrimientoconjuntos/conjuntos2.txt");
		ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");
	}
}