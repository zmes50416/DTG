package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;

/**
 * 
 * @author chiang
 *
 * @param <V>
 * @param <E>
 */
public class NgdFilter<V, E> extends PreprocessDecorator<V, E> {
	HashMap<V, String> vertexTerms = new HashMap<V, String>();
	HashMap<E, Double> Ngd = new HashMap<E, Double>();
	List<Entry<E, Double>> NgdList;
	Double rankThresholdPercent;

	/**
	 * @param _component
	 * @param _vertexTerms
	 *            放node與term
	 * @param _edgeDistance
	 *            放edge與distance
	 * @param _NgdList
	 *            放來序過的ngd list
	 * @param rankThreshold
	 *            放要過濾剩前％的NGD
	 */
	public NgdFilter(PreprocessComponent<V, E> _component,
			HashMap<V, String> _vertexTerms, HashMap<E, Double> _edgeDistance,
			List<Entry<E, Double>> _NgdList, double _rankThreshold) {
		super(_component);
		this.vertexTerms = _vertexTerms;
		this.Ngd = _edgeDistance;
		this.NgdList = _NgdList;
		this.rankThresholdPercent = _rankThreshold;

	}

	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		int rankThreshold = (int) (rankThresholdPercent * NgdList.size());
		for (int index = 0; index < NgdList.size(); index++) {
			if (index >= rankThreshold) {
				originGraph.removeEdge(NgdList.get(index).getKey());
			}
		}
		return originGraph;
	}

}
