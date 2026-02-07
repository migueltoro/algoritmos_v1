package us.lsi.ag.manual;

import java.util.List;

public interface Poblacion<E extends Cromosoma<E>> {	
	E tournament();
	Integer size();
	List<E> individuals();
	void add(E e);
	Poblacion<E> reboot();
	E best();
	
	public default List<E> bests(Integer n){
		return this.individuals().stream()
				.sorted((c1, c2) -> c1.fitness().compareTo(c2.fitness()))
				.limit(n).toList();
	}
	
	public default Double dispersion() {
		Double s= 0.;
		Double s2= 0.;
		Integer n = 0;
		for (E c : this.individuals()) {
			Double f = c.fitness();
			s += f;
			s2 += f * f;
			n++;
		}
		Double media = s / n;
		return Math.sqrt(s2 / n - media * media);	
	}
}
