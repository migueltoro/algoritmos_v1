package us.lsi.ag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.common.List2;
import us.lsi.common.Multiset;
import us.lsi.common.Set2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.streams.Collectors2;

public class Distances {
	
	public static List<Integer> vertices(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){
		Integer n = edges.size();
		List<Integer> vertices = IntStream.range(0,n).boxed()
				.map(i->graph.getEdgeSource(edges.get(i)))
				.collect(Collectors.toList());
		vertices.add(graph.getEdgeTarget(edges.get(n-1)));
		return vertices;
	}
	
	
	public static Double distanceToBool(Boolean in) {
		return in?0.:1.;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &lt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double distanceToLeZero(Double in) {
		Double r = 0.;		
		if(in > 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &gt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double distanceToGeZero(Double in) {
		Double r = 0.;		
		if(in < 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in = 0.
	 * @return in*in
	 */
	public static Double distanceToEqZero(Double in) {
		return in*in;
	}
	
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrado de la diferencia entre el n&uacute;mero de el elementos diferentes y el de la lista
	 */
	public static <E> Double distanceToAllDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return (double)(n-m)*(n-m);
	}
	
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrado del cardinal de la diferencia simetrica entre ls1 y ls2
	 */
	public static <E> Double distanceToPermutation(List<E> ls1, List<E> ls2) {
		Multiset<E> m1 = ls1.stream().collect(Collectors2.toMultiset());
		Multiset<E> m2 = ls2.stream().collect(Collectors2.toMultiset());
		Integer n = Multiset.symmetricDifference(m1, m2).size();
		return (double) n*n;
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrado del número de de los elementos false
	 */
	
	public static <E> Double distanceToAllMatch(List<Boolean> ls) {
		Integer m = ls.stream().mapToInt(e->e?0:1).sum();
		return (double)m *m;
	}
	
	public static Double distanceToSimpleOpenPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		Double d1 = distanceToAllDifferents(vertices);
		Double d2 =	distanceToAllMatch(vertices.stream().map(v->graph.containsVertex(v)).toList());
		Double d3 =	distanceToAllMatch(IntStream.range(0,n-1).boxed()
				.map(i->graph.containsEdge(vertices.get(i),vertices.get((i+1)))).toList());
		return d1+d2+d3;
	}
	
	public static Double distanceToSimpleClosedPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return distanceToSimpleOpenPathVertices(graph,vertices)+
				10*distanceToBool(graph.containsEdge(vertices.get(n-1),vertices.get(0)));
	}
	
	public static Double distanceToSimpleOpenPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges) {
		List<Integer> vertices = vertices(graph,edges);
		Integer n = vertices.size();
		return distanceToSimpleOpenPathVertices(graph,vertices(graph,edges))
				+ 10*distanceToBool(graph.containsEdge(vertices.get(n-1),vertices.get(0)));
	}
	
	public static <E> Double distanceToEqualsList(List<E> ls1, List<E> ls2) {
		Integer n = List2.symmetricDifference(ls1, ls2).size();
		return (double) n*n;
	}
	
	public static <E> Double distanceToEqualsSet(Set<E> ls1, Set<E> ls2) {
		Integer n = Set2.symmetricDifference(ls1, ls2).size();
		return (double) n*n;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
