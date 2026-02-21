package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.SelectionPolicy;

import us.lsi.ag.ChromosomeData;
import us.lsi.common.Pair;

public interface AChromosome<V,G,S> {
	
	CrossoverPolicy crossOverPolicy();
	
	MutationPolicy mutationPolicy();
	
	SelectionPolicy selectionPolicy();
	
	Chromosome initialChromosome();
	
	AChromosome<V,G,S> mutate();
	
	Pair<AChromosome<V,G,S>,AChromosome<V,G,S>> crossover(AChromosome<V,G,S> second);
	
	double fitness();
	
	S solution();
	
	V decode();
	
	V decode(Chromosome chromosome);
	
	G representation();
	
	V decodeValues(G g);
	
	Integer dimension();
	
	AChromosome<V,G,S> deepCopy();
	
	ChromosomeData<V,S> data();
}
