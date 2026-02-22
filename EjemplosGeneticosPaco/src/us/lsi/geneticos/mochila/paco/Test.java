package us.lsi.geneticos.mochila.paco;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.String2;
import us.lsi.mochila.datos.DatosMochila;

public class Test {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 500000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 623;
		StoppingConditionFactory.stoppingConditionType = 
				StoppingConditionFactory.StoppingConditionType.SolutionsNumber;
		
		DatosMochila.capacidadInicial = 78;
		Cromosoma cr = new Cromosoma("ficheros/geneticos/mochila/mochila.txt");
		AlgoritmoAG<List<Integer>,Solucion> ag = AlgoritmoAG.of(cr);
		String2.toConsole(DatosMochila.getObjetos(), "Objetos:");
		
		ag.ejecuta();
		System.out.println(ag.bestSolution());
		System.out.println("\n"+ag.stoppingCondition());
		System.out.println("Mejor fitness: "+ag.getBestFitness());
	}

}
