package us.lsi.geneticos.anuncios.paco;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.anuncios.datos.DatosAnuncios;

public class Test {
	
	public static void main(String[] args){
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		AlgoritmoAG.NUM_GENERATIONS = 400;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		
		DatosAnuncios.tiempoTotal = 30;
		PermutationData<Solucion> cr = 
		new Cromosoma("ficheros/geneticos/anuncios/anuncios.txt");	
		AlgoritmoAG<List<Integer>,Solucion> ag = AlgoritmoAG.of(cr);
		ag.ejecuta();
		
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println("================================");
		System.out.println(ag.bestSolution());
		System.out.println("================================");		
	}	

}
