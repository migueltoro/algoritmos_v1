package us.lsi.ag;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.jgrapht.alg.util.Pair;
import us.lsi.common.Preconditions;

public class AuxiliaryAg {
	
	
	public static Double convert(Double d, Double min, Double max) {
		Preconditions.checkArgument(min < max, 
				String.format("E valor de min = %.2f debe ser inferior a max = %.2f", min, max));
		return min + (max-min)*d;
	}
	
	public static Integer convert(Double d, Integer min, Integer max) {
		Preconditions.checkArgument(min < max, 
				String.format("E valor de min = %d debe ser inferior a max = %d", min, max));
		return (int) (min + (max-min)*d);
	}
	
	public static <E> List<E> convert(List<Double> d, List<E> normalSequence) {		
		Preconditions.checkArgument(d.size() == normalSequence.size(), 
				String.format("Los tamaños %d, %d debe ser iguales",d.size(), normalSequence.size()));
		Integer n = d.size();
		return IntStream.range(0, n).boxed()
				.map(i->Pair.of(d.get(i),normalSequence.get(i)))
				.sorted(Comparator.comparing(p->p.getFirst()))
				.map(p->p.getSecond())
				.toList();			
	}
	
	public static Integer convert(Double d, List<Integer> values) {
		Integer index = (int) (values.size()*d);
		return values.get(index);
	}
	
	
	public static void test1() {
		List<Integer> sn = List.of(0,1,1,1,2,3,4,5,6);
		Integer n = sn.size();
		Random r = new Random();
		List<Double> d = r.doubles().limit(n).boxed().toList();
		System.out.println(convert(d,sn));
	}
	
	public static void main(String[] args) {
		
	}

}
