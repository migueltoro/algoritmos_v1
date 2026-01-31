package us.lsi.geneticos.coloreado.paco;

import java.util.Map;

import java.util.List;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.String2;
import us.lsi.grafos.datos.Ciudad;

public class Test {

	public static void main(String[] args){
		AlgoritmoAG.ELITISM_RATE  = 0.3;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		StoppingConditionFactory.NUM_GENERATIONS = 100;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		
		RangeIntegerData<Map<Ciudad,Integer>> cr = 
		new Cromosoma("ficheros/geneticos/coloreado/andalucia.txt");		
		Datos.MaxNumColors = 5;
		AlgoritmoAG<List<Integer>,  Map<Ciudad, Integer>> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
	    AChromosome<List<Integer>,?, Map<Ciudad, Integer>> mejorSolucion = ag.getBestAChromosome();
		System.out.println("================================");
		int nc = Double.valueOf(mejorSolucion.fitness()*-1).intValue();
		System.out.println("Numero de colores: "+nc);
		String2.toConsole(ag.bestSolution().entrySet(),"Coloreado obtenido");
		System.out.println("================================");
	}

}
