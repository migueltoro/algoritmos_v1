package us.lsi.geneticos.sudoku.paco;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class Datos {
	
	private record Casilla(int x, int y, int v) {
		static Casilla parse(String s) {
			String[] v = s.split(":");
			String[] w = v[0].split(",");
			return new Casilla(Integer.parseInt(w[0].trim()), 
				Integer.parseInt(w[1].trim()), Integer.parseInt(v[1].trim())-1);
		}
	}
	
	static int DIM;
	private static List<List<Integer>> valores;
	private static SortedMap<Integer, Integer> inicial;
	
	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	
	private static void iniDatos(String file, boolean show) {
		List<String> lineas = Files2.linesFromFile(file);
		DIM = Integer.parseInt(lineas.get(0).split("=")[1].trim());
		inicial = new TreeMap<>();
		for(int i=1; i<lineas.size(); i++) {
			Casilla c = Casilla.parse(lineas.get(i));
			inicial.put(DIM*c.x() + c.y(), c.v());
		}
		valores = List2.nCopies(List.of(), DIM*DIM);
		IntStream.range(0, DIM*DIM).forEach(i->setValores(i));
		if(show)
			toConsole();
	}
	
	private static void setValores(Integer i) {
		Integer v = inicial.get(i);
		if(v!=null)
			valores.set(i, List.of(v));
		else
			valores.set(i, posibles(i));
	}

	private static List<Integer> posibles(Integer i) {
		List<Integer> ls = List2.empty();
		ls.addAll(mismaFila(i));
		ls.addAll(mismaColumna(i));
		ls.addAll(mismoSubCuadrado(i));
		return List2.difference(List2.rangeList(0, DIM), ls);
	}

	private static List<Integer> mismaFila(Integer i) {
		Integer fila = i/DIM;
		return inicial.keySet().stream().filter(n->n/DIM==fila).map(n->inicial.get(n)).toList();
	}

	private static List<Integer> mismaColumna(Integer i) {
		Integer columna = i%DIM;
		return inicial.keySet().stream().filter(n->n%DIM==columna).map(n->inicial.get(n)).toList();
	}

	private static List<Integer> mismoSubCuadrado(Integer i) {
		Integer sc = subCuadrado(i);
		return inicial.keySet().stream().filter(n->subCuadrado(n)==sc).map(n->inicial.get(n)).toList();
	}

	public static Integer subCuadrado(Integer i) {
		Integer r = Double.valueOf(Math.sqrt(DIM)).intValue();
		Integer r2 = DIM*r, c = i%DIM;
		return c/r+ r*(i/r2);
	}

	public static int getNumGenes() {
		return DIM*DIM - inicial.size();
	}
	
	public static List<Integer> getValoresGen(int i) {
		int p=-1, j=0, k=0;
		while(p<=i) {
			if(inicial.containsKey(j))
				k++;
			j++;
			p = j-k;
		}
		return valores.get(j-1);
	}
	
	public static int addValorGen(int i, int p, List<List<Integer>> ls, List<Integer> cr) {
		int f = i/DIM, c = i%DIM, r = 0;
		if(inicial.containsKey(i)) {
			ls.get(f).set(c, inicial.get(i));
			r = 1;
		} else {
			ls.get(f).set(c, cr.get(i-p));
		}
		return r;
	}
	
	public static List<List<Integer>> montaMatriz(List<Integer> cr) {
		List<List<Integer>> r = new ArrayList<>(DIM);
		IntStream.range(0, DIM).forEach(i -> r.add(fill(-1, DIM)));
		int p = 0;
		for(int i=0; i<DIM*DIM; i++) {
			p += addValorGen(i, p, r, cr);
		}
		return r;
	}
	
	public static <T> List<T> fill(T e, Integer n){
	    List<T> v = new ArrayList<T>(n);
	    for(int i=0;i<n;i++){
	       v.add(e);
	    }
	    return v;
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/geneticos/sudoku/sudoku2.txt", true);
	}
	
	public static void toConsole() {
		String2.toConsole("DIM %dx%d:\n%d Valores Iniciales:", DIM, DIM, inicial.size());
		inicial.entrySet().forEach(e -> String2.toConsole("\t%d -> %d", e.getKey(), e.getValue()+1));
		String2.toConsole("Tam del cromosoma: %d\nValores posibles para cada casilla:", getNumGenes());
		for(int i=0; i<valores.size(); i++)
			String2.toConsole("\tCasilla-%2d: %s", i,valores.get(i).stream().map(e->e+1).toList()); 
		
		System.out.println("Valores para el gen de indice 2: "+getValoresGen(2).stream().map(e->e+1).toList());
		System.out.println("Valores para el gen de indice 65:"+getValoresGen(65).stream().map(e->e+1).toList());
		System.out.println("SubCuadrados:");
		IntStream.range(0, DIM*DIM).forEach(i -> String2.toConsole("(%2d,%2d) -> %d", i/DIM, i%DIM, subCuadrado(i)));
	}	
}