package us.lsi.graphs.alg;

import us.lsi.graphs.virtual.EGraph;
import java.util.function.Function;
import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.PDRB.Type;

public class PDRBBuilder<V, E, S> {
	
	public static <V, E, S> PDRBBuilder<V, E, S> of() {
		return new PDRBBuilder<V, E, S>();
	}
	
    private EGraph<V, E> graph = null;
    private Type type = Type.Min;
    private Function<GraphPath<V, E>, S> fsolution = null;
    private Boolean withGraph = false;
    private Double bestValue= null;
    private GraphPath<V, E> optimalPath = null;

    public PDRBBuilder<V, E, S> graph(EGraph<V, E> graph) {
        this.graph = graph;
        return this;
    }

    public PDRBBuilder<V, E, S> type(Type type) {
        this.type = type;
        return this;
    }

    public PDRBBuilder<V, E, S> fsolution(Function<GraphPath<V, E>, S> fsolution) {
        this.fsolution = fsolution;
        return this;
    }

    public PDRBBuilder<V, E, S> withGraph(Boolean withGraph) {
        this.withGraph = withGraph;
        return this;
    }
    
    public PDRBBuilder<V, E, S> bestValue(Double bestValue) {
        this.bestValue = bestValue;
        return this;
    }

    public PDRBBuilder<V, E, S> optimalPath(GraphPath<V, E> optimalPath) {
        this.optimalPath = optimalPath;
        return this;
    }

    public PDRB<V, E, S> build() {
        return new PDRB<V, E, S>(graph, type, fsolution,  bestValue, optimalPath,withGraph);
    }
    
    public static void main(String[] args) {
    	
	}
}


