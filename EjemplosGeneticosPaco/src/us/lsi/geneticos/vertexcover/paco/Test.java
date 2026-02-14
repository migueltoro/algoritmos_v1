package us.lsi.geneticos.vertexcover.paco;

import java.io.IOException;
import java.util.List;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.grafos.datos.Ciudad;

public class Test {

	public static void main(String[] args) throws IOException {
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		AlgoritmoAG.NUM_GENERATIONS = 40000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Cromosoma cr = new Cromosoma("ficheros/geneticos/vertexcover/andalucia.txt");
		AlgoritmoAG<List<Integer>,List<Ciudad>> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		List<Integer> dc = ag.getBestAChromosome().decode();
		System.out.println(dc);
		System.out.println(ag.getBestAChromosome().fitness());
		System.out.println(ag.bestSolution());
		System.out.println("================================");
	}

}