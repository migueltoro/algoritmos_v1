package us.lsi.ag.sudoku.manual;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import us.lsi.ag.Constraints;
import us.lsi.ag.manual.Cromosoma;
import us.lsi.common.Files2;
import us.lsi.common.IntegerSet;


public class Sudoku  implements Cromosoma<Sudoku> {
	
	public static final int SIZE = 9;
	private int[][] grid;
	private Integer fit;
	public static IntegerSet allValues = IntegerSet.range(1,10);
	public static Random rand = new Random();
	public static Map<Integer,IntegerSet> casillasVacias = new HashMap<>();
    public static Map<Integer,IntegerSet> valoresUsados = new HashMap<>();
    public static Map<Integer,IntegerSet> valoresLibres = new HashMap<>();
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
			IntegerSet vaciasEnFila = IntegerSet.empty();
			IntegerSet valoresUsadosEnFila = IntegerSet.empty();
			for (int j = 0; j < SIZE; j++) {
				if (fila[j] == 0)
					vaciasEnFila.add(j);
				else
					valoresUsadosEnFila.add(fila[j]);
			}
			Sudoku.casillasVacias.put(i, vaciasEnFila);
			Sudoku.valoresUsados.put(i, valoresUsadosEnFila);
			Sudoku.valoresLibres.put(i, allValues.difference(valoresUsadosEnFila));
		}
		return sudoku;
	}
	
	
	public int[][] grid() {
		return grid;
	}

	// ============================
    // Fitness: duplicados en columnas y bloques
    // ============================
	@Override
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
        int[][] ind = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            ind[i] = Sudoku.generarFilaValida(i);
        return Sudoku.of(ind);
    }
    
    // ============================
    // Generar fila válida
    // ============================
    private static int[] generarFilaValida(int f) { 
        int[] fila = Sudoku.initial.grid[f].clone();
        List<Integer> vcFila = Sudoku.casillasVacias.get(f).stream().toList();
        List<Integer> ftFila = Sudoku.valoresLibres.get(f).stream().collect(Collectors.toList());       
        Collections.shuffle(ftFila);
        for (int i = 0; i < vcFila.size(); i++)
            fila[vcFila.get(i)] = ftFila.get(i);
        return fila;
    }

	@Override
	public Sudoku mutate() {
		Sudoku copia = this.deepCopy();
		int fila = rand.nextInt(SIZE);
//		System.out.println("Mutando fila: " + fila);
        List<Integer> libres = Sudoku.casillasVacias.get(fila).stream().toList();
        if (libres.size() < 2) return copia;

        int i = libres.get(rand.nextInt(libres.size()));
        int j = libres.get(rand.nextInt(libres.size()));
        while (j == i) j = libres.get(rand.nextInt(libres.size()));

        int tmp = copia.grid[fila][i];
        copia.grid[fila][i] = copia.grid[fila][j];
        copia.grid[fila][j] = tmp;
        copia.fit = copia.fitness().intValue();
        return copia;
	}

	@Override
	public Sudoku repair() {
		Sudoku copiaMejor = this.deepCopy();
		Double fitMejor = copiaMejor.fitness();

		int intentos = 5;

        for (int t = 0; t < intentos; t++) {
        	Sudoku copia = copiaMejor.mutate();	
        	Double fitCopia = copia.fitness();
			
        	if (fitCopia < fitMejor) {
				copiaMejor = copia;
				fitMejor = fitCopia;
			}	         
        }
        return copiaMejor;
		
	}
	
	
	
	public Sudoku[] crossover2(Sudoku other) {
		int cut = Sudoku.rand.nextInt(SIZE - 1) + 1;
		int[][] h1 = new int[SIZE][SIZE];
		int[][] h2 = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			if (i < cut) {
				h1[i] = this.grid[i].clone();
				h2[i] = other.grid[i].clone();
			} else {
				h1[i] = this.grid[i].clone();
				h2[i] = other.grid[i].clone();
			}
		}
		Sudoku[] r = {Sudoku.of(h1), Sudoku.of(h2) };
		return r;
	}
	
	@Override
	public Sudoku[] crossover(Sudoku other) {
		int[][] h1 = new int[SIZE][SIZE];
		int[][] h2 = new int[SIZE][SIZE];
		boolean igualesH1 = true;
		boolean igualesH2 = true;
		for (int i = 0; i < SIZE; i++) {
			boolean tomaDeP1 = Sudoku.rand.nextBoolean();
			if (tomaDeP1) {
				h1[i] = this.grid[i].clone();
				h2[i] = other.grid[i].clone();
			} else {
				h1[i] = other.grid[i].clone();
				h2[i] = this.grid[i].clone();
			}
			// Comprobamos si siguen siendo idénticos a los padres
			if (!Arrays.equals(h1[i], this.grid[i]))
				igualesH1 = false;
			if (!Arrays.equals(h2[i], other.grid[i]))
				igualesH2 = false;
		}
		// Si por casualidad un hijo salió idéntico al padre, forzamos un cambio
		if (igualesH1) {
			int fila = rand.nextInt(SIZE);
			h1[fila] = other.grid[fila].clone();
		}
		if (igualesH2) {
			int fila = rand.nextInt(SIZE);
			h2[fila] = this.grid[fila].clone();
		}
		Sudoku[] r = {Sudoku.of(h1), Sudoku.of(h2) };	
		return r;
	}
	
	Sudoku[] crossover3(Sudoku p1, Sudoku p2) {
		Random rand = new Random();
		int cut = rand.nextInt(SIZE - 1) + 1;
		int[][] h1 = new int[SIZE][SIZE];
		int[][] h2 = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			if (i < cut) {
				h1[i] = p1.grid[i].clone();
				h2[i] = p2.grid[i].clone();
			} else {
				h1[i] = p2.grid[i].clone();
				h2[i] = p1.grid[i].clone();
			}
		}
		Sudoku[] r = {Sudoku.of(h1), Sudoku.of(h2) };
		return r;
	}

	// ============================
    // Utilidad para imprimir
    // ============================
    public String toString() {
        if (grid == null) {
            return "Sin solución";
        }
        String r = String.format("Fitness = %.0f\n",this.fitness());
        for (int[] fila : this.grid) {
            for (int v : fila)
            	r += v + " ";
            r += "\n";
        }
        return r;
    }
    @Override
    public Sudoku deepCopy() {
		int[][] copia = new int[this.grid.length][];
		for (int i = 0; i < this.grid.length; i++)
			copia[i] = Arrays.copyOf(this.grid[i], this.grid[i].length);
		return Sudoku.of(copia);
	}

	@Override
	public PoblacionSudoku emptyPoblacion() {
		return PoblacionSudoku.of();
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
	
	@Override
	public Boolean isValid() {
		Boolean esValido = true;
		for (int i = 0; i < SIZE; i++) {
			List<Integer> f = Arrays.stream(this.grid[i]).boxed().toList();
			esValido = Constraints.allDifferents(f);
			if(!esValido) break;
		}
		return esValido;
	}
	
	public static void test1() {
		
		 int[][] puzzle = {
		            {5,3,0,0,7,0,0,0,0},
		            {6,0,0,1,9,5,0,0,0},
		            {0,9,8,0,0,0,0,6,0},
		            {8,0,0,0,6,0,0,0,3},
		            {4,0,0,8,0,3,0,0,1},
		            {7,0,0,0,2,0,0,0,6},
		            {0,6,0,0,0,0,2,8,0},
		            {0,0,0,4,1,9,0,0,5},
		            {0,0,0,0,8,0,0,7,9}
		        };

		        Sudoku g = Sudoku.initial(puzzle);
		  
		        Sudoku g1 = g.generateIndividual();
		        System.out.println("Individuo generado:"+"\n" + g1);
		        System.out.println("Individuo generado:"+"\n" + g1.isValid());
		        Sudoku g2 = g1.mutate();
		        System.out.println("Mutate:"+"\n" + g2);
		        System.out.println("Mutate:"+"\n" + g2.isValid());
		        Sudoku g3 = g1.repair();
		        System.out.println("Repair:"+"\n" + g3);
		        System.out.println("Repair:"+"\n" + g3.isValid());

		        Sudoku g4 = g.generateIndividual();
		        Sudoku g5 = g.generateIndividual();
		        Sudoku[] r = g4.crossover(g5);
//		        System.out.println("Crossover Padres:"+"\n" + g4 + "\n" + g5);
//		        System.out.println("Crossover Padres:"+"\n" + g4.isValid() + " " + g5.isValid());
		        System.out.println("Crossover Hijos:"+"\n" + r[0] + "\n" + r[1]);
		        System.out.println("Crossover Hijos:"+"\n" + r[0].isValid() + " " + r[1].isValid());              
        
    }
	

	// ============================
    // Ejemplo de uso
    // ============================
	 public static void main(String[] args) {
		 test1();
	 }

	
}
