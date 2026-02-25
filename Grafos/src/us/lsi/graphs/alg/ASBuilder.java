package us.lsi.graphs.alg;

import us.lsi.graphs.virtual.EGraph;
import java.util.function.Function;
import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.AStar.Type;

public class ASBuilder<V, E, S> {
	
	public static <V, E, S> ASBuilder<V, E, S> of() {
		return new ASBuilder<V, E, S>();
	}
	
    private EGraph<V, E> graph = null;
    private Type type = Type.Min;
    private Function<GraphPath<V, E>, S> fsolution = null;
    private Double bestValue= null;
    private GraphPath<V, E> optimalPath = null;
    private Boolean withGraph = false;

    public ASBuilder<V, E, S> graph(EGraph<V, E> graph) {
        this.graph = graph;
        return this;
    }

    public ASBuilder<V, E, S> type(Type type) {
        this.type = type;
        return this;
    }

    public ASBuilder<V, E, S> fsolution(Function<GraphPath<V, E>, S> fsolution) {
        this.fsolution = fsolution;
        return this;
    }

    public ASBuilder<V, E, S> bestValue(Double bestValue) {
        this.bestValue = bestValue;
        return this;
    }

    public ASBuilder<V, E, S> optimalPath(GraphPath<V, E> optimalPath) {
        this.optimalPath = optimalPath;
        return this;
    }

    public ASBuilder<V, E, S> withGraph(Boolean withGraph) {
        this.withGraph = withGraph;
        return this;
    }

    public AStar<V, E, S> build() {
        return new AStar<V, E, S>(graph, type, fsolution, bestValue, optimalPath, withGraph);
    }
    
    public static void main(String[] args) {
    	
	}
}

