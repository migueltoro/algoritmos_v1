package us.lsi.geneticos.inversioncapital.paco;

import java.util.List;

public class Datos {
	
	public static int TOTAL = 100;
	private static List<Integer> cantidades = 
	List.of(5,10,15,20,25,30,35,40,45,50,45,40,35,30,25,20,15,10,5);
	private static List<Double> valores = 
	List.of(1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,1.8,1.7,1.6,1.5,1.4,1.3,1.2,1.1,2.0,2.1);

	public static Integer getNumInversiones() {
		return cantidades.size();
	}
	
	public static double getValor(Integer i) {
		return cantidades.get(i)*valores.get(i);
	}
	
	public static double getCantidad(Integer i) {
		return cantidades.get(i);
	}

}