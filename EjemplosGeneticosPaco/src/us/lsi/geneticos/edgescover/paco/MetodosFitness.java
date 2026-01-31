package us.lsi.geneticos.edgescover.paco;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.grafos.datos.Ciudad;

public class MetodosFitness {

	public static double totalAristas(List<Integer> cr) {
		int n = cr.size();
		return IntStream.range(0, n).filter(i->cr.get(i)>0).count();
	}
	
	public static double totalOtraComponente(List<Integer> cr) {
		double r = Datos.getNumCiudades();
		Integer e = IntStream.range(0, cr.size())
			.filter(i->cr.get(i)>0).findFirst().orElse(-1);
		if(e>=0) {
			List<Ciudad> in = Datos.getVertices(e);
			List<Ciudad> out = List2.copy(in);
			while(!in.isEmpty() && out.size()<Datos.getNumCiudades()) {
				Ciudad v = in.removeFirst();
				List<Ciudad> vecinos = vecinos(v, cr, out);
				in.addAll(vecinos);
				out.addAll(vecinos);
			}
			r -= out.size();
		} 
		return r;
	}

	private static List<Ciudad> vecinos(Ciudad v, List<Integer> cr, List<Ciudad> out) {
		return IntStream.range(0, cr.size())
			.filter(i->cr.get(i)>0 && Datos.getVertices(i).contains(v))
			.mapToObj(i->Datos.getOpuesto(v, i)).filter(e->!out.contains(e)).toList();
	}

}