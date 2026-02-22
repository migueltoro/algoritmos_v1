package us.lsi.ag.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;

import us.lsi.ag.BlocksData;
import us.lsi.ag.Constraints;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.ACrossOverPolicy.CrossoverPolicyBlocks;
import us.lsi.ag.agchromosomes.Chromosomes;
//import us.lsi.ag.manual.Cromosoma;
//import us.lsi.alg.sudoku.SolucionSudoku;
import us.lsi.common.Files2;
import us.lsi.common.IntPair;
import us.lsi.common.IntegerSet;


public class Sudoku  implements BlocksData<Sudoku> {
	
	public static final int SIZE = 9;
	private int[][] grid;
	private Integer fit;
	public static IntegerSet allValues = IntegerSet.range(1,10);
	public static Random rand = new Random();
	public static Map<Integer,List<Integer>> casillasVaciasPorFilas = new HashMap<>();
    public static Map<Integer,IntegerSet> valoresUsadosPorFilas = new HashMap<>();
    public static Map<Integer,IntegerSet> valoresLibresPorFilas = new HashMap<>();
    public static List<Integer> blockslimits = new ArrayList<>();
    public static Integer blocksNumber;
    public static List<IntPair> casillasVacias = new ArrayList<>();
    public static List<Integer> initialValuesEnVacias = new ArrayList<>();
    public static Integer size;
    public static Sudoku initial;
	
    private Sudoku(int[][] grid) {
		super();
		int[][] copia = new int[grid.length][];
		for (int i = 0; i < grid.length; i++)
			copia[i] = Arrays.copyOf(grid[i], grid[i].length);
		this.grid = copia;
		this.fit = null;		
	}
	
	public static Sudoku of(int[][] grid) {
		return new Sudoku(grid);
	}
	
	public static Sudoku of(String file) {
		int[][] grid = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[i][j] = 0;
			}
		}
		List<String> linesFromFile = Files2.linesFromFile(file);
		for (String line : linesFromFile) {
			String[] parts = line.split(",");
			Integer i = Integer.parseInt(parts[0]);
			Integer j = Integer.parseInt(parts[1]);
			Integer valor = Integer.parseInt(parts[2]);
			grid[i][j] = valor;
		}
		return Sudoku.initial(grid);
	}
	
	public static Sudoku ofFilas(String file) {
		int[][] copia = new int[SIZE][SIZE];
		List<String> linesFromFile = Files2.linesFromFile(file);
		for (int i = 0; i < SIZE; i++) {
			String line = linesFromFile.get(i);
			String[] parts = line.split(",");
			if (parts.length != SIZE)
				throw new IllegalArgumentException("Cada fila debe tener 9 valores separados por comas");
			for (int j = 0; j < SIZE; j++) {
				copia[i][j] = Integer.parseInt(parts[j]);
			}
		}
        return Sudoku.initial(copia);
	}
	
	public static Sudoku initial(int[][] grid) {
		Sudoku sudoku = new Sudoku(grid);
		Sudoku.initial = sudoku;
		for (int i = 0; i < SIZE; i++) {
			int[] fila = Sudoku.initial.grid[i].clone();;
			List<Integer> vaciasEnFila = new ArrayList<>();
			IntegerSet valoresUsadosEnFila = IntegerSet.empty();
			for (int j = 0; j < SIZE; j++) {
				if (fila[j] == 0)
					vaciasEnFila.add(j);
				else
					valoresUsadosEnFila.add(fila[j]);
			}
			Sudoku.casillasVaciasPorFilas.put(i, vaciasEnFila);
			Sudoku.valoresUsadosPorFilas.put(i, valoresUsadosEnFila);
			Sudoku.valoresLibresPorFilas.put(i, allValues.difference(valoresUsadosEnFila));
		}
		Sudoku.blockslimits.add(0);
		Integer sum = 0;
		for(int i=0; i<SIZE;i++) {
			final int fi = i;
			List<IntPair> ls = Sudoku.casillasVaciasPorFilas.get(fi).stream().map(j->IntPair.of(fi,j)).toList();
			Sudoku.casillasVacias.addAll(ls);
			sum += ls.size();
			Sudoku.blockslimits.add(sum);
			Sudoku.initialValuesEnVacias.addAll(Sudoku.valoresLibresPorFilas.get(i).stream().toList());			
		}
		Sudoku.blocksNumber = Sudoku.blockslimits.size() - 1;
		Sudoku.size = Sudoku.casillasVacias.size();
		return sudoku;
	}
	
	
	public int[][] grid() {
		return grid;
	}

	// ============================
    // Fitness: duplicados en columnas y bloques
    // ============================
	
	public Double fitness() {
		if (this.fit != null) return this.fit.doubleValue();
		int conflictos = 0;
		// Columnas
		for (int col = 0; col < SIZE; col++) {
			int[] count = new int[10];
			for (int row = 0; row < SIZE; row++)
				count[this.grid[row][col]]++;
			for (int v = 1; v <= 9; v++)
				if (count[v] > 1)
					conflictos += (count[v] - 1);
		}

		// Bloques 3x3
		for (int br = 0; br < SIZE; br += 3) {
			for (int bc = 0; bc < SIZE; bc += 3) {
				int[] count = new int[10];
				for (int r = 0; r < 3; r++)
					for (int c = 0; c < 3; c++)
						count[this.grid[br + r][bc + c]]++;
				for (int v = 1; v <= 9; v++)
					if (count[v] > 1)
						conflictos += (count[v] - 1);
			}
		}
		this.fit = conflictos;
		return this.fit.doubleValue();
	}
	
	// ============================
    // Generar individuo completo
    // ============================
	 public Sudoku generateIndividual() {
			for (int i = 0; i < Sudoku.size; i++) {
				IntPair p = Sudoku.casillasVacias.get(i);
				this.grid[p.first()][p.second()] = Sudoku.initialValuesEnVacias.get(i);
			}
			return this;
	 }
	
    
	@Override
	public Integer size() {
		return Sudoku.size;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		Sudoku sd = solution(value);
		sd.fit = null;
		return -sd.fitness();
	}

	@Override
	public Sudoku solution(List<Integer> value) {
		for (int i = 0; i < value.size(); i++) {
			IntPair p = Sudoku.casillasVacias.get(i);
			this.grid[p.first()][p.second()] = value.get(i);
		}
		return this;
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Blocks;
	}

	@Override
	public List<Integer> blocksLimits() {
		return Sudoku.blockslimits;
	}

	@Override
	public List<Integer> initialValues() {
		return Sudoku.initialValuesEnVacias;
	}

	@Override
	public Integer bloksNumber() {
		return Sudoku.blocksNumber;
	}

	// ============================
	// Utilidad para imprimir
	// ============================
	@Override
	public String toString() {
		if (grid == null) {
			return "Sin solución";
		}
		String r = String.format("Fitness = %.0f\n", this.fitness());
		r += String.format("Es válido = %s\n", this.isValid());
		for (int[] fila : this.grid) {
			for (int v : fila)
				r += v + " ";
			r += "\n";
		}
		return r;
	}

	public Sudoku deepCopy() {
		int[][] copia = new int[this.grid.length][];
		for (int i = 0; i < this.grid.length; i++)
			copia[i] = Arrays.copyOf(this.grid[i], this.grid[i].length);
		return Sudoku.of(copia);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(grid);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sudoku other = (Sudoku) obj;
		return Arrays.deepEquals(grid, other.grid);
	}

	public Boolean isValid() {
		Boolean esValido = true;
		for (int i = 0; i < SIZE; i++) {
			List<Integer> f = Arrays.stream(this.grid[i]).boxed().toList();
			esValido = Constraints.allDifferents(f);
			if (!esValido)
				break;
		}
		return esValido;
	}

	public static void test1() {

		int[][] puzzle = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
				{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

		Sudoku g = Sudoku.initial(puzzle);
		System.out.println("Individuo initial:" + "\n" + g);
		System.out.println(Sudoku.casillasVacias);
		System.out.println(Sudoku.initialValuesEnVacias);
		System.out.println(Sudoku.casillasVacias.size());
		System.out.println(Sudoku.initialValuesEnVacias.size());
		System.out.println(Sudoku.blockslimits);
		System.out.println(Sudoku.blocksNumber);
		Sudoku g1 = g.generateIndividual();
		System.out.println("Individuo generado:" + "\n" + g1);
		System.out.println("Individuo generado:" + "\n" + g1.isValid());
//		        Sudoku g2 = g1.mutate();
		

		Sudoku g4 = g.generateIndividual();
		Sudoku g5 = g.generateIndividual();
		AChromosome<List<Integer>, List<Integer>, Sudoku> g4v = Chromosomes.ofBlocks(g4);
		AChromosome<List<Integer>, List<Integer>, Sudoku> g5v = Chromosomes.ofBlocks(g5);
		
		CrossoverPolicyBlocks c = new CrossoverPolicyBlocks();
		ChromosomePair g6 = c.crossover(((Chromosome)g4v), ((Chromosome)g5v));
//		SudokuFilas g6h1 = ((SudokuFilas)g6.getFirst()).solution();
		System.out.println("Crossover Padres:"+"\n" + g4 + "\n" + g5);
		System.out.println("Crossover Padres:"+"\n" + g4.isValid() + " " + g5.isValid());
		System.out.println("Crossover Hijos:" + "\n" + g6.getFirst() + "\n" + g6.getSecond());
//		System.out.println("Crossover Hijos:" + "\n" + ((Sudoku)g6.getFirst()).isValid() + " " + ((Sudoku)g6.getSecond()).isValid());
	}

	// ============================
	// Ejemplo de uso
	// ============================
	public static void main(String[] args) {
		test1();
	}

}
