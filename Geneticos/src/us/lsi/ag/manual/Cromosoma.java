package us.lsi.ag.manual;

import java.util.Random;

public interface Cromosoma<E extends Cromosoma<E>> {
	Double fitness();
	E generarIndividuo();
	void mutate();
	void repair();
	E crossover(E other);
	E copy();
	Poblacion<E> emptyPoblacion();
	public static Random rand = new Random();
}
