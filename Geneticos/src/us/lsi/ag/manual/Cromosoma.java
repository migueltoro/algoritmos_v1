package us.lsi.ag.manual;

import java.util.Random;

public interface Cromosoma<E extends Cromosoma<E>> {
	Double fitness();
	E generateIndividual();	
	E mutate();
	E repair();
	E[] crossover(E other);
	E deepCopy();
	Boolean isValid();
	Poblacion<E> emptyPoblacion();
	public static Random rand = new Random();
}
