package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.BlocksData;

public class AMutatePolicy {

	public static Random rnd = new Random(System.currentTimeMillis());

	public static class MutatePolicyBlocks implements MutationPolicy {

		@Override
		public Chromosome mutate(Chromosome original) throws MathIllegalArgumentException {
			return MutatePolicyBlocks.mutate​PolicyBlocks(original);
		}

		public static <S> Chromosome mutate​PolicyBlocks(Chromosome original) {
			@SuppressWarnings("unchecked")
			AChromosome<List<Integer>, List<Integer>, S> c1 = (AChromosome<List<Integer>, List<Integer>, S>) original;
			List<Integer> r1 = c1.representation();
			BlocksData<S> d = (BlocksData<S>) c1.data();
			List<Integer> limits = d.blocksLimits();
			Integer n = r1.size();
			List<Integer> r1n = new ArrayList<>();
			for (int k = 0; k < n; k++) {
				r1n.add(r1.get(k));
			}
			Integer b = rnd.nextInt(n);
			Integer bs = limits.get(b + 1) - limits.get(b);
			int i = rnd.nextInt(bs);
			int j = rnd.nextInt(bs);
			while (j == i) {
				i = rnd.nextInt(bs);
				j = rnd.nextInt(bs);
			}
			Integer ia = limits.get(b) + i;
			Integer ja = limits.get(b) + j;
			Integer temp = r1n.get(ia);
			r1n.set(ia, r1n.get(ja));
			r1n.set(ja, temp);
			return new IntegerChromosome<S>(r1n);
		}
	}
	
	public static class MutatePolicyInteger implements MutationPolicy {

		@Override
		public Chromosome mutate(Chromosome original) throws MathIllegalArgumentException {
			return MutatePolicyInteger.mutate​PolicyInteger(original);
		}

		public static <S> Chromosome mutate​PolicyInteger(Chromosome original) {
			@SuppressWarnings("unchecked")
			AChromosome<List<Integer>, List<Integer>, S> c1 = (AChromosome<List<Integer>, List<Integer>, S>) original;
			List<Integer> r1 = c1.representation();
			BlocksData<S> d = (BlocksData<S>) c1.data();
			List<Integer> limits = d.blocksLimits();
			Integer n = r1.size();
			List<Integer> r1n = new ArrayList<>();
			for (int k = 0; k < n; k++) {
				r1n.add(r1.get(k));
			}
			Integer b = rnd.nextInt(n);
			Integer bs = limits.get(b + 1) - limits.get(b);
			int i = rnd.nextInt(bs);
			int j = rnd.nextInt(bs);
			while (j == i) {
				i = rnd.nextInt(bs);
				j = rnd.nextInt(bs);
			}
			Integer ia = limits.get(b) + i;
			Integer ja = limits.get(b) + j;
			Integer temp = r1n.get(ia);
			r1n.set(ia, r1n.get(ja));
			r1n.set(ja, temp);
			return new IntegerChromosome<S>(r1n);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
