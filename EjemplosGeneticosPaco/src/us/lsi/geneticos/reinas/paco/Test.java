package us.lsi.geneticos.reinas.paco;

import java.util.List;

import us.lsi.common.String2;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;

public class Test {

	private static final int NUM_REINAS = 10;
	
	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 40;
		
		AlgoritmoAG.NUM_GENERATIONS = 6000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		PermutationData<String> cr = new Cromosoma(NUM_REINAS);
		AlgoritmoAG<List<Integer>, String> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		System.out.println("================================");
		
		AChromosome<List<Integer>, ?, String> ac = ag.getBestAChromosome();
		String2.toConsole("Num. errores: %d", -Double.valueOf(ac.fitness()).intValue());
		System.out.println(ag.bestSolution());
		}	
}