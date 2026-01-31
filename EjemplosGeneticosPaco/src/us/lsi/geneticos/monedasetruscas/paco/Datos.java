package us.lsi.geneticos.monedasetruscas.paco;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class Datos {
	public record Moneda(Integer id, Double valor, Double peso) {
		public static int cont = 1;
		public static Moneda parse(String s) {
			String[] v = s.split(":");
			return new Moneda(cont++, 
				Double.parseDouble(v[0].trim()), Double.parseDouble(v[1].trim()));
		}
		
		@Override
		public String toString() {
			return String.format("M%d: Valor = %.1f; Peso = %.1f", id, valor, peso);
		}	
	}
	public static Double VALOR;	
	private static List<Moneda> monedas; 
	
	public static void iniDatos(String file) {
		iniDatos(file, false);
	}
	private static void iniDatos(String fichero, boolean show) {
		Moneda.cont=1;
		List<String> lineas = Files2.linesFromFile(fichero);
		VALOR = Double.parseDouble(lineas.get(0).split("=")[1].trim());
		monedas = List2.empty();
		for(int i=1;i<lineas.size();i++) {
			monedas.add(Moneda.parse(lineas.get(i)));
		}
		if(show)
			toConsole();
	}

	public static Integer getNumMonedas() {
		return monedas.size();
	}
	
	public static Moneda getMoneda(Integer i) {
		return monedas.get(i);
	}
	
	public static Double getValor(Integer i) {
		return monedas.get(i).valor();
	}
	
	public static Double getPeso(Integer i) {
		return monedas.get(i).peso();
	}
	
	public static Integer getMultiplicidad(Integer i){
		return VALOR.intValue() / getValor(i).intValue();
	}
	
	public static void toConsole() {
		String2.toConsole(monedas, "Conjunto de monedas");
		String2.toConsole("Valor objetivo: %.1f", VALOR);	
	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/geneticos/monedasetruscas/monedasetruscas.txt", true);
	}	
}