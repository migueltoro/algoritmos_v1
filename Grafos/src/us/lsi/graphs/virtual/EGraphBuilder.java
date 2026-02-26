package us.lsi.graphs.virtual;

import java.util.function.Function;
import java.util.function.Predicate;

import us.lsi.common.TriFunction;
import us.lsi.path.EGraphPath.PathType;

public interface EGraphBuilder<V, E> {

	EGraphBuilder<V, E> edgeWeight(Function<E, Double> edgeWeight);

	EGraphBuilder<V, E> vertexWeight(Function<V, Double> vertexWeight);

	EGraphBuilder<V, E> vertexPassWeight(TriFunction<V, E, E, Double> vertexPassWeight);

	EGraphBuilder<V, E> startVertex(V startVertex);

	EGraphBuilder<V, E> endVertex(V endVertex);
	
	EGraphBuilder<V, E> goalVertex(Predicate<V> goal);

	EGraphBuilder<V, E> pathType(PathType pathType);

	EGraphBuilder<V, E> heuristic(TriFunction<V, Predicate<V>, V, Double> heuristic);

	EGraph<V, E> build();

}