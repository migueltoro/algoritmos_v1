package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.SelectionPolicy;


import us.lsi.ag.ChromosomeData;
import us.lsi.common.Pair;

import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;

/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista de ceros y unos del tama�o especificado en el problema.
 * La implementaci�n es una adaptaci�n de la clase {@link org.apache.commons.math3.genetics.Chromosome Chromosome} de Apache. </p>
 *
 */
public class ABinaryChromosome<S> extends BinaryChromosome implements AChromosome<List<Integer>,List<Integer>,S> {
	
	/**
	 * Dimensi�n del cromosoma
	 */
	
	protected static Integer DIMENSION;
	protected static ChromosomeData<List<Integer>,Object>  data;
	
	@SuppressWarnings("unchecked")
	public static <S>void iniValues(ChromosomeValues<List<Integer>,List<Integer>,S> values){
		ABinaryChromosome.data = (ChromosomeData<List<Integer>, Object>) values.data();
		ABinaryChromosome.DIMENSION = values.dimension();
	}

	public ABinaryChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
	}

	public ABinaryChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new ABinaryChromosome<>(ar);
	}
	
	public static <S> ABinaryChromosome<S> getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(ABinaryChromosome.DIMENSION);
		return new ABinaryChromosome<>(ls);
	}
	
	public List<Integer> decode(){
		return super.getRepresentation();
	}

	@Override
	public double fitness() {
		return ABinaryChromosome.data.fitnessFunction(decode());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public S solution() {
		return (S) data.solution(this.decode());
	}
	
	public CrossoverPolicy crossOverPolicy() {
		return ACrossOverPolicy.getCrossoverPolicyBin();
	}
	
	public MutationPolicy mutationPolicy() {
		return new BinaryMutation();
	}
	
	public SelectionPolicy selectionPolicy() {
		return new ASelectionPolicy();
	}
	
	@Override
	public Chromosome initialChromosome() {
		return ABinaryChromosome.getInitialChromosome();
	}
	
	@Override
	public List<Integer> decodeValues(List<Integer> values) {
		return values;
	}
	
	@Override
	public List<Integer> decode(Chromosome cr) {
		return this.decode();
	}

	@Override
	public Integer dimension() {
		return DIMENSION;
	}
	
	@Override
	public List<Integer> representation() {
		return this.getRepresentation();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ChromosomeData<List<Integer>, S> data() {
		return (ChromosomeData<List<Integer>, S>) data;
	}

	@Override
	public ABinaryChromosome<S> deepCopy() {
		return new ABinaryChromosome<S>(this.representation());
	}

	@SuppressWarnings("unchecked")
	@Override
	public AChromosome<List<Integer>, List<Integer>, S> mutate() {
		return (AChromosome<List<Integer>, List<Integer>, S>) this.mutationPolicy().mutate(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pair<AChromosome<List<Integer>, List<Integer>, S>, AChromosome<List<Integer>, List<Integer>, S>> crossover(
			AChromosome<List<Integer>, List<Integer>, S> second) {
		ChromosomePair r = this.crossOverPolicy().crossover(this, (Chromosome) second);
		return Pair.of(
				(AChromosome<List<Integer>, List<Integer>, S>) r.getFirst(),
				(AChromosome<List<Integer>, List<Integer>, S>) r.getSecond());
	}
	
	

}
