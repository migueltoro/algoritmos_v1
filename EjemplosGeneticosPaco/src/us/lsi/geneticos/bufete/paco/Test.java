package us.lsi.geneticos.bufete.paco;

import java.util.List;
import java.util.Locale;


import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.bufete.datos.SolucionBufete;

public class Test {
	
	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 250;
		
		AlgoritmoAG.NUM_GENERATIONS = 2500;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = -4;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		Locale.setDefault(Locale.of("en", "US"));

		Cromosoma cr = new Cromosoma("ficheros/geneticos/bufete/bufete.txt");
		AlgoritmoAG<List<Integer>, SolucionBufete> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		System.out.println(ag.bestSolution());	
	}

}