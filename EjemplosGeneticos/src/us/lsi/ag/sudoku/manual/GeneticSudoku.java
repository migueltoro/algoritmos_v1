package us.lsi.ag.sudoku.manual;

import java.util.*;

public class GeneticSudoku {

    static final int SIZE = 9;
    static final int POP_SIZE = 200;
    static final int MAX_GEN = 5000;
    static final double PROB_CROSS = 0.8;
    static final double PROB_MUT = 0.3;

    int[][] puzzle;
    Random rand = new Random();

    public GeneticSudoku(int[][] puzzle) {
        this.puzzle = puzzle;
    }

    // ============================
    // Generar fila válida
    // ============================
    int[] generarFilaValida(int[] filaOriginal) {
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

    // ============================
    // Generar individuo completo
    // ============================
    int[][] generarIndividuo() {
        int[][] ind = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            ind[i] = generarFilaValida(puzzle[i]);
        return ind;
    }

    // ============================
    // Fitness: duplicados en columnas y bloques
    // ============================
    int fitness(int[][] ind) {
        int conflictos = 0;

        // Columnas
        for (int col = 0; col < SIZE; col++) {
            int[] count = new int[10];
            for (int row = 0; row < SIZE; row++)
                count[ind[row][col]]++;
            for (int v = 1; v <= 9; v++)
                if (count[v] > 1) conflictos += (count[v] - 1);
        }

        // Bloques 3x3
        for (int br = 0; br < SIZE; br += 3) {
            for (int bc = 0; bc < SIZE; bc += 3) {
                int[] count = new int[10];
                for (int r = 0; r < 3; r++)
                    for (int c = 0; c < 3; c++)
                        count[ind[br + r][bc + c]]++;
                for (int v = 1; v <= 9; v++)
                    if (count[v] > 1) conflictos += (count[v] - 1);
            }
        }

        return conflictos;
    }

    // ============================
    // Mutación: swap dentro de una fila
    // ============================
    void mutar(int[][] ind) {
        int fila = rand.nextInt(SIZE);

        List<Integer> libres = new ArrayList<>();
        for (int col = 0; col < SIZE; col++)
            if (puzzle[fila][col] == 0)
                libres.add(col);

        if (libres.size() < 2) return;

        int i = libres.get(rand.nextInt(libres.size()));
        int j = libres.get(rand.nextInt(libres.size()));
        while (j == i) j = libres.get(rand.nextInt(libres.size()));

        int tmp = ind[fila][i];
        ind[fila][i] = ind[fila][j];
        ind[fila][j] = tmp;
    }

    // ============================
    // Reparación local (hill climbing suave)
    // ============================
    void reparar(int[][] ind) {
        int intentos = 5;

        for (int t = 0; t < intentos; t++) {
            int fitAntes = fitness(ind);

            int fila = rand.nextInt(SIZE);

            List<Integer> libres = new ArrayList<>();
            for (int col = 0; col < SIZE; col++)
                if (puzzle[fila][col] == 0)
                    libres.add(col);

            if (libres.size() < 2) continue;

            int i = libres.get(rand.nextInt(libres.size()));
            int j = libres.get(rand.nextInt(libres.size()));
            while (j == i) j = libres.get(rand.nextInt(libres.size()));

            int tmp = ind[fila][i];
            ind[fila][i] = ind[fila][j];
            ind[fila][j] = tmp;

            if (fitness(ind) > fitAntes) {
                ind[fila][j] = ind[fila][i];
                ind[fila][i] = tmp;
            }
        }
    }

    // ============================
    // Cruce por filas
    // ============================
    int[][] crossover(int[][] p1, int[][] p2) {
        int[][] hijo = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            if (rand.nextDouble() < 0.5)
                hijo[i] = p1[i].clone();
            else
                hijo[i] = p2[i].clone();
        }
        return hijo;
    }

    // ============================
    // Selección por torneo
    // ============================
    int[][] torneo(List<int[][]> poblacion) {
        int k = 3;
        int[][] mejor = null;
        int bestFit = Integer.MAX_VALUE;

        for (int i = 0; i < k; i++) {
            int[][] ind = poblacion.get(rand.nextInt(poblacion.size()));
            int fit = fitness(ind);
            if (fit < bestFit) {
                bestFit = fit;
                mejor = ind;
            }
        }
        return mejor;
    }

    // ============================
    // Algoritmo Genético principal
    // ============================
    int[][] resolver() {

        List<int[][]> poblacion = new ArrayList<>();
        for (int i = 0; i < POP_SIZE; i++)
            poblacion.add(generarIndividuo());

        for (int gen = 0; gen < MAX_GEN; gen++) {

            // Evaluar mejor individuo (elitismo)
            int[][] mejor = null;
            int bestFit = Integer.MAX_VALUE;

            for (int[][] ind : poblacion) {
                int fit = fitness(ind);
                if (fit < bestFit) {
                    bestFit = fit;
                    mejor = ind;
                }
            }

            if (bestFit == 0) {
                System.out.println("Solución encontrada en generación " + gen);
                return mejor;
            }

            // Nueva población
            List<int[][]> nueva = new ArrayList<>();

            // ELITISMO: conservar el mejor individuo
            nueva.add(Arrays.stream(mejor)
                            .map(int[]::clone)
                            .toArray(int[][]::new));

            // Rellenar el resto
            while (nueva.size() < POP_SIZE) {
                int[][] p1 = torneo(poblacion);
                int[][] p2 = torneo(poblacion);

                int[][] hijo;
                if (rand.nextDouble() < PROB_CROSS)
                    hijo = crossover(p1, p2);
                else
                    hijo = Arrays.stream(p1).map(int[]::clone).toArray(int[][]::new);

                if (rand.nextDouble() < PROB_MUT)
                    mutar(hijo);

                reparar(hijo);

                nueva.add(hijo);
            }

            poblacion = nueva;
        }

        System.out.println("No se encontró solución");
        return null;
    }

    // ============================
    // Utilidad para imprimir
    // ============================
    static void imprimir(int[][] grid) {
        if (grid == null) {
            System.out.println("Sin solución");
            return;
        }
        for (int[] fila : grid) {
            for (int v : fila)
                System.out.print(v + " ");
            System.out.println();
        }
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

        GeneticSudoku gs = new GeneticSudoku(puzzle);
        imprimir(gs.puzzle);
        int[][] solucion = gs.resolver();
        imprimir(solucion);
    }
}

