package us.lsi.geneticos.camino.paco;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.grafos.datos.Ciudad;

public class Test {
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));		
		testsFichero(1,"Cadiz", "Granada",c -> c.habitantes() > 100000,w -> w > 100.);
		testsFichero(2,"Toledo","Guadalajara",c -> c.habitantes() <= 200000,w -> w >= 120.);
		testsFichero(3,"C01","C25",c -> c.habitantes() > 25000,w -> w < 200.);

	}
	
	public static void testsFichero(Integer i, String origen, String destino, 
			Predicate<Ciudad> pv1, Predicate<Double> pe1) {
		
			AlgoritmoAG.POPULATION_SIZE = 750;
			AlgoritmoAG.NUM_GENERATIONS = 2000;
			
			Datos.iniDatos("ficheros/geneticos/camino/camino"+i+".txt", pv1, pe1, origen, destino);
			
			Cromosoma cr = new Cromosoma();
			AlgoritmoAG<List<Integer>, String> ag = AlgoritmoAG.of(cr);
			ag.ejecuta();
			System.out.println("Genetico "+i+" ####################");
			System.out.println(ag.bestSolution());
			System.out.println("####################");
	}

}
