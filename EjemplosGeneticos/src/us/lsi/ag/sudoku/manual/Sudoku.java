package us.lsi.ag.sudoku.manual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import us.lsi.ag.manual.Cromosoma;


public class Sudoku  implements Cromosoma<Sudoku> {
	
	public static final int SIZE = 9;
	private int[][] grid;
	private Integer fit;
	public static Random rand = new Random();

	
	public static Sudoku of(int[][] grid) {
		return new Sudoku(grid);
	}
	
	private Sudoku(int[][] grid) {
		super();
		this.grid = grid;
		this.fit = null;
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
    public Sudoku generarIndividuo() {
        int[][] ind = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            ind[i] = Sudoku.generarFilaValida(this.grid[i]);
        return Sudoku.of(ind);
    }
    
    // ============================
    // Generar fila válida
    // ============================
    private static int[] generarFilaValida(int[] filaOriginal) {
        int[] fila = filaOriginal.clone();

        List<Integer> vacias = new ArrayList<>();
        boolean[] usados = new boolean[10];

        for (int i = 0; i < SIZE; i++) {
            if (fila[i] == 0) vacias.add(i);
            else usados[fila[i]] = true;
        }

        List<Integer> faltantes = new ArrayList<>();
        for (int v = 1; v <= 9; v++)
            if (!usados[v]) faltantes.add(v);

        Collections.shuffle(faltantes);

        for (int i = 0; i < vacias.size(); i++)
            fila[vacias.get(i)] = faltantes.get(i);

        return fila;
    }

	@Override
	public void mutate() {
		int fila = rand.nextInt(SIZE);

        List<Integer> libres = new ArrayList<>();
        for (int col = 0; col < SIZE; col++)
            if (this.grid[fila][col] == 0)
                libres.add(col);

        if (libres.size() < 2) return;

        int i = libres.get(rand.nextInt(libres.size()));
        int j = libres.get(rand.nextInt(libres.size()));
        while (j == i) j = libres.get(rand.nextInt(libres.size()));

        int tmp = this.grid[fila][i];
        this.grid[fila][i] = this.grid[fila][j];
        this.grid[fila][j] = tmp;
	}

	@Override
	public void repair() {
		int intentos = 5;

        for (int t = 0; t < intentos; t++) {
            int fitAntes = this.fitness().intValue();

            int fila = rand.nextInt(SIZE);

            List<Integer> libres = new ArrayList<>();
            for (int col = 0; col < SIZE; col++)
                if (this.grid[fila][col] == 0)
                    libres.add(col);

            if (libres.size() < 2) continue;

            int i = libres.get(rand.nextInt(libres.size()));
            int j = libres.get(rand.nextInt(libres.size()));
            while (j == i) j = libres.get(rand.nextInt(libres.size()));

            int tmp = this.grid[fila][i];
            this.grid[fila][i] = this.grid[fila][j];
            this.grid[fila][j] = tmp;

            if (this.fitness() > fitAntes) {
            	this.grid[fila][j] = this.grid[fila][i];
            	this.grid[fila][i] = tmp;
            }
        }
		
	}

	@Override
	public Sudoku crossover(Sudoku other) {
		int[][] hijo = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            if (rand.nextDouble() < 0.5)
                hijo[i] = this.grid[i].clone();
            else
                hijo[i] = other.grid[i].clone();
        }
        return Sudoku.of(hijo);
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
    public Sudoku copy() {
		int[][] copia = new int[this.grid.length][];
		for (int i = 0; i < this.grid.length; i++)
			copia[i] = Arrays.copyOf(this.grid[i], this.grid.length);
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

	// ============================
    // Ejemplo de uso
    // ============================
    public static void main(String[] args) {

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

        Sudoku gs = Sudoku.of(puzzle);
        System.out.println(gs);
        Sudoku gs2 = gs.generarIndividuo();
        System.out.println(gs2);
        Sudoku gs2c = gs2.copy();
        gs2.repair();
        Sudoku gsm = gs2.copy(); 
        System.out.println(gsm);
        System.out.println(gs2c.equals(gsm));
    }


}
