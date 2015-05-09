package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Util.NgdEdgeSorter;

/**
 * 
 * @author chiang
 *
 * @param <V>
 * @param <E>
 */
public class NgdEdgeFilter<V, E> extends PreprocessDecorator<V, E> {
	HashMap<E, Double> ngdMap = new HashMap<E, Double>();
	Double rankThresholdPercent;

	/**
	 * @param _component
	 * @param _edgeDistance
	 *            放edge與distance
	 * @param rankThreshold
	 *            要過濾剩x％的NGD ex:0.3 70%的都會過濾
	 */
	public NgdEdgeFilter(PreprocessComponent<V, E> _component, HashMap<E, Double> _edgeDistance, double _rankThreshold) {
		super(_component);
		this.ngdMap = _edgeDistance;
		this.rankThresholdPercent = _rankThreshold;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		if(ngdMap.size()!=originGraph.getEdgeCount()){
			throw new IllegalStateException("the map should sync with the graph");
		}
		int rankThreshold = (int) (rankThresholdPercent * originGraph.getEdgeCount());
		
		List<Entry<?, Double>> sortedEdge = NgdEdgeSorter.sort(ngdMap);
		
		for (int index = ngdMap.size(); index > rankThreshold; index=index-1) {
			Object edgeUnderThreshold = sortedEdge.get(index-1).getKey();
			ngdMap.remove((E) edgeUnderThreshold);
			originGraph.removeEdge((E) edgeUnderThreshold);
		}
		return originGraph;
	}

}
