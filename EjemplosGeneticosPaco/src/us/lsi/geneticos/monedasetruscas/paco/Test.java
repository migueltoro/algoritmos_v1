package us.lsi.geneticos.monedasetruscas.paco;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.String2;
import us.lsi.geneticos.monedasetruscas.paco.Cromosoma.TIPO;

public class Test {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;
		
		AlgoritmoAG.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Cromosoma cr = new Cromosoma(TIPO.MIN, "ficheros/geneticos/monedasetruscas/monedasetruscas.txt");
		AlgoritmoAG<List<Integer>,Solucion> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		String2.toConsole(String2.line("=", 100));
		System.out.println("Conjunto de menor peso:");
		System.out.println(ag.bestSolution());
				
		cr = new Cromosoma(TIPO.MAX, "ficheros/geneticos/monedasetruscas/monedasetruscas.txt");
		ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		String2.toConsole(String2.line("=", 100));
		System.out.println("Conjunto de mayor peso:");
		System.out.println(ag.bestSolution());
		String2.toConsole(String2.line("=", 100));
	}
}
