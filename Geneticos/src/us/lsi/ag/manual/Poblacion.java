package us.lsi.ag.manual;

import java.util.List;

public interface Poblacion<E extends Cromosoma<E>> {	
	E tournament();
	Integer size();
	List<E> individuals();
	void add(E e);
}
