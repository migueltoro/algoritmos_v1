package us.lsi.geneticos.tareasprocesadores.paco;

import java.util.List;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;

public class Test {

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		AlgoritmoAG.NUM_GENERATIONS = 500;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;

		Cromosoma cr = new Cromosoma("ficheros/geneticos/tareasprocesadores/tareasprocesadores.txt");
		AlgoritmoAG<List<Integer>,String> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");
	}	

}