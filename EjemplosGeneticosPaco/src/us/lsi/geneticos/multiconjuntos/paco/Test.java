package us.lsi.geneticos.multiconjuntos.paco;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Test {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;
		
		AlgoritmoAG.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Cromosoma cr = new Cromosoma("ficheros/geneticos/multiconjuntos/multiconjuntos1.txt");
		AlgoritmoAG<List<Integer>,Solucion> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");
		
		cr = new Cromosoma("ficheros/geneticos/multiconjuntos/multiconjuntos2.txt");
		ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");
		
		cr = new Cromosoma("ficheros/geneticos/multiconjuntos/multiconjuntos3.txt");
		ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");	
		
	}
}
