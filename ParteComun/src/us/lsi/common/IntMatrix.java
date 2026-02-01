package us.lsi.common;

public class IntMatrix {
    private final int filas;
    private final int columnas;
    private final int[] data;  // bloque contiguo

    public IntMatrix(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.data = new int[filas * columnas];
    }

    private int index(int fila, int columna) {
        return fila * columnas + columna;
    }

    public int get(int fila, int columna) {
        return data[index(fila, columna)];
    }

    public void set(int fila, int columna, int valor) {
        data[index(fila, columna)] = valor;
    }

    public int filas() {
        return filas;
    }

    public int columnas() {
        return columnas;
    }

    // Copia profunda con un solo bloque de memoria
    public IntMatrix deepCopy() {
        IntMatrix copia = new IntMatrix(filas, columnas);
        System.arraycopy(this.data, 0, copia.data, 0, this.data.length);
        return copia;
    }
}

