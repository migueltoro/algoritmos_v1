package us.lsi.graphs.alg;

import us.lsi.graphs.virtual.EGraph;
import java.util.function.Function;
import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.BT.Type;

public class BTBuilder<V, E, S> {
	
    private EGraph<V, E> graph = null;
    private Type type = Type.Min;
    private Function<GraphPath<V, E>, S> fsolution = null;
    private Double bestValue= null;
    private GraphPath<V, E> optimalPath = null;
    private Integer solutionNumber = 1;
    private Boolean withGraph = false;

    public BTBuilder<V, E, S> graph(EGraph<V, E> graph) {
        this.graph = graph;
        return this;
    }

    public BTBuilder<V, E, S> type(Type type) {
        this.type = type;
        return this;
    }

    public BTBuilder<V, E, S> fsolution(Function<GraphPath<V, E>, S> fsolution) {
        this.fsolution = fsolution;
        return this;
    }

    public BTBuilder<V, E, S> bestValue(Double bestValue) {
        this.bestValue = bestValue;
        return this;
    }

    public BTBuilder<V, E, S> optimalPath(GraphPath<V, E> optimalPath) {
        this.optimalPath = optimalPath;
        return this;
    }

    public BTBuilder<V, E, S> solutionNumber(Integer solutionNumber) {
        this.solutionNumber = solutionNumber;
        return this;
    }

    public BTBuilder<V, E, S> withGraph(Boolean withGraph) {
        this.withGraph = withGraph;
        return this;
    }

    public BT<V, E, S> build() {
        return new BT<>(graph, type, fsolution, bestValue, optimalPath, solutionNumber, withGraph);
    }
    
    public static void main(String[] args) {
    	
	}
}
