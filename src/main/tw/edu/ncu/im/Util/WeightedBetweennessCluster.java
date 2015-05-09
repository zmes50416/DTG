package tw.edu.ncu.im.Util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
/**
 * Because JUNG dont support weighted betweennessCluster so we have to override one
 * @author TingWen
 *
 * @param <V>
 * @param <E>
 */
public class WeightedBetweennessCluster<V,E> extends EdgeBetweennessClusterer<V, E> {
	private Transformer<E,Double> edgeWeightTransformer;
	private int mNumEdgesToRemove;
    private Map<E, Pair<V>> edges_removed;
    
	public WeightedBetweennessCluster(int numEdgesToRemove,Transformer<E,Double> edgeTransformer) {
		super(numEdgesToRemove); //the private int will not be inher so reassign myself
		this.mNumEdgesToRemove = numEdgesToRemove;
		this.edgeWeightTransformer = edgeTransformer;
        this.edges_removed = new LinkedHashMap<E, Pair<V>>();
	}
	/**
	 * override jung betweenness cluster method to fit weight graph 
	 */
	@Override
	public Set<Set<V>> transform(Graph<V,E> graph){
		if (this.mNumEdgesToRemove <= 0 || mNumEdgesToRemove > graph.getEdgeCount()) {
            throw new IllegalArgumentException("Invalid number of edges passed in. EdgsToRemove:"+this.mNumEdgesToRemove+",Total Edges:"+graph.getEdgeCount());
        }
        

        for (int k=0;k<mNumEdgesToRemove;k++) {
            BetweennessCentrality<V,E> bc = new BetweennessCentrality<V,E>(graph,this.edgeWeightTransformer);
            E to_remove = null;
            double score = 0;
            for (E e : graph.getEdges()){
                if (bc.getEdgeScore(e) > score)
                {
                    to_remove = e;
                    score = bc.getEdgeScore(e);
                }
            }
            edges_removed.put(to_remove, graph.getEndpoints(to_remove));
            graph.removeEdge(to_remove);
        }

        WeakComponentClusterer<V,E> wcSearch = new WeakComponentClusterer<V,E>();
        Set<Set<V>> clusterSet = wcSearch.transform(graph);

        for (Map.Entry<E, Pair<V>> entry : edges_removed.entrySet())
        {
            Pair<V> endpoints = entry.getValue();
            graph.addEdge(entry.getKey(), endpoints.getFirst(), endpoints.getSecond());
        }
        return clusterSet;
	}

}
