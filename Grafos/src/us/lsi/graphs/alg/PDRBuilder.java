package us.lsi.graphs.alg;

import us.lsi.graphs.virtual.EGraph;
import java.util.function.Function;
import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.PDR.Type;

public class PDRBuilder<V, E, S> {
	
	public static <V, E, S> PDRBuilder<V, E, S> of() {
		return new PDRBuilder<V, E, S>();
	}
	
    private EGraph<V, E> graph = null;
    private Type type = Type.Min;
    private Function<GraphPath<V, E>, S> fsolution = null;
    private Boolean withGraph = false;

    public PDRBuilder<V, E, S> graph(EGraph<V, E> graph) {
        this.graph = graph;
        return this;
    }

    public PDRBuilder<V, E, S> type(Type type) {
        this.type = type;
        return this;
    }

    public PDRBuilder<V, E, S> fsolution(Function<GraphPath<V, E>, S> fsolution) {
        this.fsolution = fsolution;
        return this;
    }

    public PDRBuilder<V, E, S> withGraph(Boolean withGraph) {
        this.withGraph = withGraph;
        return this;
    }

    public PDR<V, E, S> build() {
        return new PDR<V, E, S>(graph, type, fsolution, withGraph);
    }
    
    public static void main(String[] args) {
    	
	}
}

