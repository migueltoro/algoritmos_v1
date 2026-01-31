package us.lsi.ag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;

import us.lsi.common.Multiset;
import us.lsi.graphs.SimpleEdge;
import us.lsi.streams.Collectors2;

public class Constraints {
	
	
	public static <E> Boolean equals(List<E> ls1, List<E> ls2) {
		return ls1.equals(ls2);
	}
	
	public static <E> Boolean equals(Set<E> ls1, Set<E> ls2) {
		return ls1.equals(ls2);
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrdao de la diferencia entre el n&uacute;mero de el elementos diferentes y el de la lista
	 */
	public static <E> Boolean allDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return n.equals(m);
	}
	
	/**
	 * @param ls1 Una lista 
	 * @param ls2 Una lista 
	 * @return true si ls1 es una permta
	 */
	public static <E> Boolean isPermutation(List<E> ls1, List<E> ls2) {
		Multiset<E> m1 = ls1.stream().collect(Collectors2.toMultiset());
		Multiset<E> m2 = ls2.stream().collect(Collectors2.toMultiset());
		return m1.equals(m2);
	}
	
	public static Boolean isSimpleOpenPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return allDifferents(vertices) &&
				vertices.stream().allMatch(v->graph.containsVertex(v)) &&
				IntStream.range(0,n-1).boxed().allMatch(i->graph.containsEdge(vertices.get(i),vertices.get(i+1)));
	}
	
	public static Boolean isSimpleOpenPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){		
		return isSimpleOpenPathVertices(graph,Distances.vertices(graph,edges));
	}
	
	public static Boolean isSimpleClosedPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return isSimpleOpenPathVertices(graph,vertices) &&
			   graph.containsEdge(vertices.get(n-1),vertices.get(0));
	}
	
	public static Boolean isSimpleClosedPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){		
		return isSimpleClosedPathVertices(graph,Distances.vertices(graph,edges));
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
