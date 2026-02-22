package us.lsi.alg.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import us.lsi.common.Files2;
import us.lsi.common.IntPair;
import us.lsi.common.IntegerSet;
import us.lsi.common.Preconditions;


public class Sudoku  {
	
	public static record Casilla(Integer fila, Integer columna) {
		public static Casilla of(Integer fila, Integer columna) {
			return new Casilla(fila, columna);
		}
		@Override
		public String toString() {
			return String.format("(%d,%d,%d)", this.fila, this.columna,this.subTabla());
		}
		public IntPair toIntPair() {
			return IntPair.of(this.fila, this.columna);
		}
		public Integer subTabla() {			
            return  this.columna/Sudoku.SUBTABLE_SIZE + Sudoku.SUBTABLE_SIZE*(this.fila/Sudoku.SUBTABLE_SIZE);
        }	
	}
	
	public static final int SIZE = 9;
	public static final int SUBTABLE_SIZE = 3;
	private int[][] grid;
	public static IntegerSet allValues = IntegerSet.range(1,10);
	public static Random rand = new Random();

    
    public List<Casilla> casillasVacias = new ArrayList<>();
    public Casilla nextCasilla = null;
    public List<Integer> valoresLibresEnNextCasilla = null;
    public Integer numcasillasVacias;
    public Integer size;
    public Integer conflictos;
    public static Sudoku initial= null;
	
	private Sudoku(int[][] grid) {
		super();
		int[][] copia = new int[grid.length][];
		for (int i = 0; i < grid.length; i++)
			copia[i] = Arrays.copyOf(grid[i], grid[i].length);
		this.grid = copia;
		this.conflictos = null;
		Map<Integer,IntegerSet> valoresUsadosPorFila = new HashMap<>();
	    Map<Integer,IntegerSet> valoresUsadosPorColumna = new HashMap<>();
	    Map<Integer,IntegerSet> valoresUsadosPorSubTabla = new HashMap<>();
	    Map<Integer,IntegerSet> valoresLibresPorFila = new HashMap<>();   
	    Map<Integer,IntegerSet> valoresLibresPorColumna = new HashMap<>();   
	    Map<Integer,IntegerSet> valoresLibresPorSubTabla = new HashMap<>();
	    Map<Casilla,IntegerSet> valoresLibresPorCasilla = new HashMap<>();
		for (int i = 0; i < Sudoku.SIZE; i++) {
			for (int j = 0; j < Sudoku.SIZE; j++) {
				Casilla c = Casilla.of(i, j);
				Integer t = this.subTabla(i, j);
				Integer v = this.grid[i][j];
				if (v == 0)
					this.casillasVacias.add(c);
				else {
					if (!valoresUsadosPorFila.containsKey(i))
                        valoresUsadosPorFila.put(i, IntegerSet.empty());
					valoresUsadosPorFila.get(i).add(v);
					if (!valoresUsadosPorColumna.containsKey(j))
                        valoresUsadosPorColumna.put(j, IntegerSet.empty());
					valoresUsadosPorColumna.get(j).add(v);
					if (!valoresUsadosPorSubTabla.containsKey(t))
                        valoresUsadosPorSubTabla.put(t, IntegerSet.empty());
					valoresUsadosPorSubTabla.get(t).add(v);
				}
			}
		}
		for (int i = 0; i < Sudoku.SIZE; i++) {
			valoresLibresPorFila.put(i, Sudoku.allValues.difference(valoresUsadosPorFila.getOrDefault(i,IntegerSet.empty())));
			valoresLibresPorColumna.put(i, Sudoku.allValues.difference(valoresUsadosPorColumna.getOrDefault(i,IntegerSet.empty())));
			valoresLibresPorSubTabla.put(i, Sudoku.allValues.difference(valoresUsadosPorSubTabla.getOrDefault(i,IntegerSet.empty())));
		}
		for (int i = 0; i < Sudoku.SIZE; i++) {
				for (int j = 0; j < Sudoku.SIZE; j++) {
					Casilla c = Casilla.of(i, j);
					Integer t = this.subTabla(i, j);
					IntegerSet libresEnCasilla = 
							valoresLibresPorFila.get(i)
							.intersection(valoresLibresPorColumna.get(j))
							.intersection(valoresLibresPorSubTabla.get(t));				
					valoresLibresPorCasilla.put(c, libresEnCasilla);
				}
		}
		this.size = this.casillasVacias.size();
		this.conflictos = this.calculoDeConflictos(
				valoresUsadosPorFila,
				valoresUsadosPorColumna,
				valoresUsadosPorSubTabla);
		if (this.size > 0) {
			this.nextCasilla = this.casillasVacias.stream()
					.min(Comparator.comparing(c -> valoresLibresPorCasilla.get(c).size())).orElse(null);
			this.valoresLibresEnNextCasilla = valoresLibresPorCasilla.get(this.nextCasilla).stream().toList();
		}
		if (Sudoku.initial == null) {
			Sudoku.initial = this;
		}
	}
	
	public static Sudoku of(int[][] grid) {		
		return new Sudoku(grid);
	}
	
	public static Sudoku of(String file) {
		int[][] grid = new int[Sudoku.SIZE][Sudoku.SIZE];
		for (int i = 0; i < Sudoku.SIZE; i++) {
			for (int j = 0; j < Sudoku.SIZE; j++) {
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
		return Sudoku.of(grid);
	}
	
	public static Sudoku ofFilas(String file) {
		int[][] copia = new int[Sudoku.SIZE][Sudoku.SIZE];
		List<String> linesFromFile = Files2.linesFromFile(file);
		for (int i = 0; i < Sudoku.SIZE; i++) {
			String line = linesFromFile.get(i);
			String[] parts = line.split(",");
			if (parts.length != Sudoku.SIZE)
				throw new IllegalArgumentException("Cada fila debe tener 9 valores separados por comas");
			for (int j = 0; j < Sudoku.SIZE; j++) {
				copia[i][j] = Integer.parseInt(parts[j]);
			}
		}
        return Sudoku.of(copia);
	}
	
	public Sudoku setValor(Casilla c, Integer v) {
		Preconditions.checkArgument(this.size > 0, "No se pueden asignar valores a un Sudoku completo");
		Preconditions.checkNotNull(c, "La casilla no puede ser null");
		Preconditions.checkNotNull(v, "El valor no puede ser null");
		Sudoku copia = this.deepCopy();
		copia.grid[c.fila()][c.columna()] = v;
		return new Sudoku(copia.grid);
	}
	
	public Casilla nextCasilla() {
		return this.nextCasilla;
	}

	public Integer size() {
		return size;
	}

	public List<Integer> valoresLibresEnNextCasilla() {
		return valoresLibresEnNextCasilla;
	}
	
	public Integer conflictos() {
		return conflictos;
	}
	
	public Integer subTabla(Integer fila, Integer columna) {
		return  columna/Sudoku.SUBTABLE_SIZE + Sudoku.SUBTABLE_SIZE*(fila/Sudoku.SUBTABLE_SIZE);
	}
	
	public int[][] grid() {
		return grid;
	}

	// ============================
    // Conflictos: duplicados en columnas y bloques
    // ============================
	
	public Integer calculoDeConflictos(
			Map<Integer,IntegerSet> valoresUsadosPorFila,
			Map<Integer,IntegerSet> valoresUsadosPorColumna,
			Map<Integer,IntegerSet> valoresUsadosPorSubTabla) {
		Integer n = 0;
		for (int i = 0; i < Sudoku.SIZE; i++) {
			IntegerSet usadosEnFila = valoresUsadosPorFila.getOrDefault(i,IntegerSet.empty());
			IntegerSet usadosEnColumna = valoresUsadosPorColumna.getOrDefault(i,IntegerSet.empty());
			IntegerSet usadosEnSubTabla = valoresUsadosPorSubTabla.getOrDefault(i,IntegerSet.empty());
			n += usadosEnFila.size() + usadosEnColumna.size() + usadosEnSubTabla.size();			
		}
		return 3*Sudoku.SIZE*Sudoku.SIZE - n;
	}

	public Sudoku solution(List<Integer> value) {
		Sudoku sd = Sudoku.initial.deepCopy();
		for (int i = 0; i < value.size(); i++) {
			Casilla p = sd.casillasVacias.get(i);
			this.grid[p.fila()][p.columna()] = value.get(i);
		}
		return this;
	}

	// ============================
	// Utilidad para imprimir
	// ============================
	@Override
	public String toString() {
		if (grid == null) {
			return "Sin solución";
		}
		String r = String.format("Conflictos = %d\n", this.conflictos());
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
		return this.conflictos() == 0;
	}

	public static void test1() {

		int[][] puzzle = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
				{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 }, { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
				{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

		System.out.println(Casilla.of(3, 0));
//		Sudoku g = Sudoku.of(puzzle);
		Sudoku g = Sudoku.of("ficheros/sudoku/sudoku5.txt");
//		Sudoku g = Sudoku.ofFilas("ficheros/sudoku/sudoku_filas.txt");
		System.out.println("Individuo inicial:" + "\n" + g);
	}

	// ============================
	// Ejemplo de uso
	// ============================
	public static void main(String[] args) {
		test1();
	}

}
