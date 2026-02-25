package us.lsi.common;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Arrays2 {
	
	@SafeVarargs
	public static <E> E[] newArray(E... elements) {
		return elements;
	}
	
	public  static double[] getArrayDouble(int n, double v){
		double[] r = new double[n];
		for(int i=0;i<r.length;i++){
			r[i]= v;
		}
		return r;
	}
		
	public static Integer[] copyArray(Integer d[]){
		return Arrays.copyOf(d, d.length);
	}
	
	public static int[] copyArray(int d[]){
		return Arrays.copyOf(d, d.length);
	}
		
	public static Double[] copyArray(Double d[]){
		return Arrays.copyOf(d, d.length);
	}
	
	public static double[] copyArray(double d[]){
		return Arrays.copyOf(d, d.length);
	}
		
	/**
	 * @param d Datos
	 * @param n N�mero de filas
	 * @param m N�mero de columnas
	 * @return El array bidimensional
	 */
	public static Integer[][] toMultiArray(Integer d[], Integer n, Integer m) {
		Integer[][] r = new Integer[n][m];
		IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.forEach(p -> {
					r[p.first()][p.second()] = d[p.first() * n + p.second()];
				});
		return r;
	}
	
	public static int[][] toMultiArrayInt(Integer d[], Integer n, Integer m) {
		int[][] r = new int[n][m];
		IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.forEach(p -> {
					r[p.first()][p.second()] = d[p.first() * n + p.second()];
				});
		return r;
	}

	public static IntPair findPosition(Integer d[][], Predicate<Integer> pd) {
		Integer n = d.length;
		Integer m = d[0].length;
		return IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.filter(p -> pd.test(d[p.first()][p.second()])).findFirst().orElse(null);
	}
	
	public static IntPair findPosition(int d[][], Predicate<Integer> pd) {
		Integer n = d.length;
		Integer m = d[0].length;
		return IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.filter(p -> pd.test(d[p.first()][p.second()])).findFirst().orElse(null);
	}

	public static Integer[][] copyArray(Integer d[][]) {
		int n = d.length;
		int m = d[0].length;
		Integer[][] copia = new Integer[n][m];
		for (int i = 0; i < n; i++)
			copia[i] = Arrays.copyOf(d[i], m);
		return copia;
	}
	
	public static int[][] copyArray(int d[][]) {
		int n = d.length;
		int m = d[0].length;
		int[][] copia = new int[n][m];
		for (int i = 0; i < n; i++)
			copia[i] = Arrays.copyOf(d[i], m);
		return copia;
	}

	public static Double[][] copyArray(Double d[][]) {
		Integer n = d.length;
		Integer m = d[0].length;
		Double[][] r = new Double[n][m];
		IntStream.range(0, n).boxed().flatMap(f -> IntStream.range(0, m).boxed().map(c -> IntPair.of(f, c)))
				.forEach(p -> {
					r[p.first()][p.second()] = d[p.first()][p.second()];
				});
		return r;
	}

}
