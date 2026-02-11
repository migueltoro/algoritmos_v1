package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.BlocksData;

public class BlocksValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {

	public static Random rnd = new Random(System.currentTimeMillis());
	
	public static <S> BlocksValues<S> of(BlocksData<S> data) {
        return new BlocksValues<S>(data);
    }

    private BlocksData<S> data;

    private BlocksValues(BlocksData<S> data) {
        this.data = data;
    }
    
    @Override
    public BlocksData<S> data() {
        return data;
    }

    @Override
    public List<Integer> decodeValues(List<Double> r) {
    	List<Integer> s = new ArrayList<>();
		List<Integer> p = data.blocksLimits();
		Integer pn = p.size();
		for(int i=0; i<pn-1;i++) {
			List<Double> rp = r.subList(p.get(i),p.get(i+1));
			List<Integer> values = data.initialValues().subList(p.get(i),p.get(i+1));
			List<Integer> v = AuxiliaryAg.convert(rp,values);			
			s.addAll(v);			
		}
		return s;
    }

    @Override
    public Integer dimension() {
        return data.size();
    }
    
    public static <S> ChromosomePair crossover​(Chromosome first, Chromosome second) {
    	@SuppressWarnings("unchecked")
		AChromosome<List<Integer>,List<Double>,S> c1 = (AChromosome<List<Integer>, List<Double>, S>) first;
    	@SuppressWarnings("unchecked")
    	AChromosome<List<Integer>,List<Double>,S> c2 = (AChromosome<List<Integer>, List<Double>, S>) second;
    	List<Double> r1 = c1.representation();
    	List<Double> r2 = c2.representation();
    	BlocksData<S> d = (BlocksData<S>) c1.data();
    	Integer bn = d.bloksNumber();
    	List<Integer> limits = d.blocksLimits();
    	List<Double> r1n = new ArrayList<>();
        List<Double> r2n = new ArrayList<>();
    	for(int i=0;i<bn;i++) {
    		Double p = rnd.nextDouble();
			if (p < 0.5) {
				r1n.addAll(r1.subList(limits.get(i), limits.get(i + 1)));
				r2n.addAll(r2.subList(limits.get(i), limits.get(i + 1)));
			} else {
				r1n.addAll(r2.subList(limits.get(i), limits.get(i + 1)));
				r2n.addAll(r1.subList(limits.get(i), limits.get(i + 1)));
			}
    	} 
    	return new ChromosomePair(new ARandomKey<>(r1n), new ARandomKey<>(r2n));
	}
    
    public static <S> Chromosome mutate​(Chromosome original) {
    	@SuppressWarnings("unchecked")
		AChromosome<List<Integer>,List<Double>,S> c1 = (AChromosome<List<Integer>, List<Double>, S>) original;
		List<Double> r1 = c1.representation();
		BlocksData<S> d = (BlocksData<S>) c1.data();
		List<Integer> limits = d.blocksLimits();
    	Integer n = r1.size();
		List<Double> r1n = new ArrayList<>();
		for (int k = 0; k < n; k++) {
			r1n.add(r1.get(k));
		}		
    	Integer b = rnd.nextInt(n);
    	Integer bs = limits.get(b+1)-limits.get(b);
    	int i = rnd.nextInt(bs);
        int j = rnd.nextInt(bs);
        while (j == i) {
        	i = rnd.nextInt(bs);
        	j = rnd.nextInt(bs);	
        }		
		Integer ia = limits.get(b)+i;
		Integer ja = limits.get(b)+j;
		Double temp = r1n.get(ia);
		r1n.set(ia, r1n.get(ja));
		r1n.set(ja, temp);		
		return new ARandomKey<>(r1n);
	}
}
