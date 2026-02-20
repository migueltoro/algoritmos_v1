package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.SelectionPolicy;

import us.lsi.ag.ChromosomeData;
import us.lsi.ag.agchromosomes.ACrossOverPolicy.CrossoverPolicyInteger;
import us.lsi.ag.agchromosomes.AMutatePolicy.MutatePolicyInteger;

/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista enteros 
 * del tamaño especificado en el problema.
 *
 */
public class IntegerChromosome<S> extends AbstractListChromosome<Integer> 
	implements AChromosome<List<Integer>,List<Integer>,S> {
	
	/**
	 * Dimensi�n del cromosoma
	 */
	
	protected static Integer DIMENSION;
	protected static ChromosomeData<List<Integer>,Object>  data;
	public static CrossoverPolicy crossOverPolicy = new CrossoverPolicyInteger();
	public static MutationPolicy mutationPolicy = new MutatePolicyInteger();
	public static SelectionPolicy selectionPolicy = new ASelectionPolicy();
	
	public IntegerChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
	}
	
	public IntegerChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
	}

	@SuppressWarnings("unchecked")
	public static <S> void iniValues(ChromosomeValues<List<Integer>,List<Integer>,S> values){
		IntegerChromosome.data = (ChromosomeData<List<Integer>, Object>) values.data();
		IntegerChromosome.DIMENSION = values.dimension();
	}
	
	public static <S> void iniValues(
			ChromosomeValues<List<Integer>,List<Integer>,S> values,
			CrossoverPolicy crossOverPolicy,
			MutationPolicy mutationPolicy,
            SelectionPolicy selectionPolicy){
		IntegerChromosome.iniValues(values);
		IntegerChromosome.crossOverPolicy = crossOverPolicy;
		IntegerChromosome.mutationPolicy = mutationPolicy;
		IntegerChromosome.selectionPolicy = selectionPolicy;				
	}
	
	@Override
	protected void checkValidity(List<Integer> arg0) throws InvalidRepresentationException {
		// No se hace ninguna comprobaci�n de la representaci�n
	}

	public IntegerChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new IntegerChromosome<>(ar);
	}
	
	public static <S> IntegerChromosome<S> getInitialChromosome() {
		List<Integer> ls = IntegerChromosome.data.initialValues();
		return new IntegerChromosome<>(ls);
	}
	
	public List<Integer> decode(){
		return this.representation();
	}

	@Override
	public double fitness() {
		return IntegerChromosome.data.fitnessFunction(decode());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public S solution() {
		return (S) data.solution(this.decode());
	}
	
	public CrossoverPolicy crossOverPolicy() {
		return IntegerChromosome.crossOverPolicy;
	}
	
	public MutationPolicy mutationPolicy() {
		return IntegerChromosome.mutationPolicy;
	}
	
	public SelectionPolicy selectionPolicy() {
		return IntegerChromosome.selectionPolicy;
	}
	
	@Override
	public Chromosome initialChromosome() {
		return IntegerChromosome.getInitialChromosome();
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
		return super.getRepresentation();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ChromosomeData<List<Integer>, S> data() {
		return (ChromosomeData<List<Integer>, S>) data;
	}
	
	@Override
	public IntegerChromosome<S> copy() {
		return new IntegerChromosome<S>(this.representation());
	}
}

