package us.lsi.geneticos.distribucionalumnos.paco;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Test1 {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 75;
		
		StoppingConditionFactory.NUM_GENERATIONS = 7500;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Cromosoma1 cr = new Cromosoma1("ficheros/geneticos/distribucionalumnos/alumnos1.txt");
		AlgoritmoAG<List<Integer>,Solucion> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");
		
		cr = new Cromosoma1("ficheros/geneticos/distribucionalumnos/alumnos2.txt");
		ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");
		
		cr = new Cromosoma1("ficheros/geneticos/distribucionalumnos/alumnos3.txt");
		ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");		
	}
}