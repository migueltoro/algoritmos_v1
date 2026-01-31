package us.lsi.geneticos.edgescover.paco;

import java.io.IOException;
import java.util.List;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.String2;
import us.lsi.grafos.datos.Carretera;

public class Test {

	public static void main(String[] args) throws IOException {
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Cromosoma cr = new Cromosoma("ficheros/geneticos/edgescoverconnect/andalucia.txt");
		AlgoritmoAG<List<Integer>,List<Carretera>> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		List<Integer> dc = ag.getBestAChromosome().decode();
		System.out.println(dc);
		String2.toConsole("Num. de aristas %d", -Double.valueOf(ag.getBestAChromosome().fitness()).intValue());
		System.out.println(ag.bestSolution());
		System.out.println("================================");
	}

}