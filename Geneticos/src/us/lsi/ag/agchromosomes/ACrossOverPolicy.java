package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.CycleCrossover;
import org.apache.commons.math3.genetics.NPointCrossover;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.commons.math3.genetics.UniformCrossover;

import us.lsi.ag.BlocksData;

public class ACrossOverPolicy {

	public static Random rnd = new Random(System.currentTimeMillis());

	public enum CrossoverType {
		Cycle, NPoint, OnePoint, Ordered, Uniform
	};

	/**
	 * Tipo del operador de cruce
	 */
	public static CrossoverType crossoverType = CrossoverType.OnePoint;

	/**
	 * N�mero de puntos usados en la partici�n si se usa un operador de cruce de
	 * tipo NPointCrossover
	 */
	public static int NPOINTCROSSOVER = 3;
	/**
	 * La ratio si se usa el operador de cruce de tipo UniformCrossover
	 */
	public static double RATIO_UNIFORMCROSSOVER = 0.7;

	/**
	 * @param tipo     El tipo del cromosoma
	 * @param problema Las propiedades del probblema a resolver
	 * @return Un operador de cruce adecuado para un cromosma del tipo indicado
	 */
	public static CrossoverPolicy getCrossoverPolicyBin() {
		CrossoverPolicy crossOverPolicy = null;
		switch (ACrossOverPolicy.crossoverType) {
		case Cycle:
			crossOverPolicy = new CycleCrossover<Integer>();
			break;
		case NPoint:
			crossOverPolicy = new NPointCrossover<Integer>(NPOINTCROSSOVER);
			break;
		case OnePoint:
			crossOverPolicy = new OnePointCrossover<Integer>();
			break;
		case Ordered:
			crossOverPolicy = new OrderedCrossover<Integer>();
			break;
		case Uniform:
			crossOverPolicy = new UniformCrossover<Integer>(RATIO_UNIFORMCROSSOVER);
			break;
		}
		return crossOverPolicy;
	}

	public static CrossoverPolicy getCrossoverPolicyKey() {
		CrossoverPolicy crossOverPolicyKey = null;
		switch (ACrossOverPolicy.crossoverType) {
		case Cycle:
			crossOverPolicyKey = new CycleCrossover<Double>();
			break;
		case NPoint:
			crossOverPolicyKey = new NPointCrossover<Double>(NPOINTCROSSOVER);
			break;
		case OnePoint:
			crossOverPolicyKey = new OnePointCrossover<Double>();
			break;
		case Ordered:
			crossOverPolicyKey = new OrderedCrossover<Double>();
			break;
		case Uniform:
			crossOverPolicyKey = new UniformCrossover<Double>(RATIO_UNIFORMCROSSOVER);
			break;
		}
		return crossOverPolicyKey;
	}

	public static class CrossoverPolicyBlocks implements CrossoverPolicy {

		@Override
		public ChromosomePair crossover(Chromosome first, Chromosome second) {
			return CrossoverPolicyBlocks.crossover​PolicyBlocks(first, second);
		}

		public static <S> ChromosomePair crossover​PolicyBlocks(Chromosome first, Chromosome second) {
			@SuppressWarnings("unchecked")
			AChromosome<List<Integer>, List<Integer>, S> c1 = (AChromosome<List<Integer>, List<Integer>, S>) first;
			@SuppressWarnings("unchecked")
			AChromosome<List<Integer>, List<Integer>, S> c2 = (AChromosome<List<Integer>, List<Integer>, S>) second;
			List<Integer> r1 = c1.representation();
			List<Integer> r2 = c2.representation();
			BlocksData<S> d = (BlocksData<S>) c1.data();
			Integer bn = d.bloksNumber();
			List<Integer> limits = d.blocksLimits();
			List<Integer> r1n = new ArrayList<>();
			List<Integer> r2n = new ArrayList<>();
			for (int i = 0; i < bn; i++) {
				Double p = rnd.nextDouble();
				if (p < 0.5) {
					r1n.addAll(r1.subList(limits.get(i), limits.get(i + 1)));
					r2n.addAll(r2.subList(limits.get(i), limits.get(i + 1)));
				} else {
					r1n.addAll(r2.subList(limits.get(i), limits.get(i + 1)));
					r2n.addAll(r1.subList(limits.get(i), limits.get(i + 1)));
				}
			}
			return new ChromosomePair(new IntegerChromosome<S>(r1n), new IntegerChromosome<S>(r2n));
		}
	}

	public static class CrossoverPolicyInteger implements CrossoverPolicy {

		@Override
		public ChromosomePair crossover(Chromosome first, Chromosome second) {
			return CrossoverPolicyInteger.crossover​PolicyInteger(first, second);
		}

		public static <E, S> ChromosomePair crossover​PolicyInteger(Chromosome first, Chromosome second) {
			@SuppressWarnings("unchecked")
			AChromosome<List<Integer>, List<Integer>, S> c1 = (AChromosome<List<Integer>, List<Integer>, S>) first;
			@SuppressWarnings("unchecked")
			AChromosome<List<Integer>, List<Integer>, S> c2 = (AChromosome<List<Integer>, List<Integer>, S>) second;
			List<Integer> r1 = c1.representation();
			List<Integer> r2 = c2.representation();
			Integer n = r1.size();
			Integer bn = rnd.nextInt(n);
			List<Integer> r1n = new ArrayList<>();
			List<Integer> r2n = new ArrayList<>();
			Double p = rnd.nextDouble();
			if (p < 0.5) {
				r1n.addAll(r1.subList(0, bn));
				r2n.addAll(r2.subList(0, bn));
			} else {
				r1n.addAll(r2.subList(0, bn));
				r2n.addAll(r2.subList(0, bn));
			}
			return new ChromosomePair(new IntegerChromosome<S>(r1n), new IntegerChromosome<S>(r2n));
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
