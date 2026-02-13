package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.ABinaryChromosome;

public interface BinaryData<S> extends RangeData<Integer,S>{
	
	default Integer max(Integer i) {
		return 2;
	}
	
	default Integer min(Integer i) {
		return 0;
	}
	
	default List<Integer> initialValues() {
		return ABinaryChromosome.getInitialChromosome().representation();
	}
}
