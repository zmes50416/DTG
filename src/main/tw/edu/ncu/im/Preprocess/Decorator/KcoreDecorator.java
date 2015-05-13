package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Util.NgdEdgeSorter;
/**
 * 過濾掉小於門檻值的邊
 * 利用各點degree找出各點k-core值 
 * 排列k-core值
 * 只留下最大k-core值的圖形
 * @author chiang
 *
 * @param <V>
 * @param <E>
 */
public class KcoreDecorator<V, E> extends PreprocessDecorator<V, E> {
	Map<E, Double> ngdMap = new HashMap<E, Double>();
	Map<V, Integer> coreMap = new HashMap<V,Integer>();
	Double edgeThreshold;
	public KcoreDecorator(PreprocessComponent<V, E> _component, Map<E, Double> _edgeDistance,Double _edgeThreshold) {
		super(_component);
		this.ngdMap = _edgeDistance;
		this.edgeThreshold= _edgeThreshold;
	}

	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		Set<V> toRemoveVerteices = new HashSet<V>();
		Set<E> toRemoveEdges = new HashSet<E>();
		//將超過門檻值的邊加入toRemoveEdges後從圖形刪除
		for(E edge:originGraph.getEdges()){
			if(ngdMap.get(edge)>edgeThreshold){
				toRemoveEdges.add(edge);
			}
		}
		for(E edge:toRemoveEdges){
			originGraph.removeEdge(edge);
		}
		/**
		 * 找出各點k-core值後取得最大的k
		 */
		coreMap = getKcore(originGraph);
		List<Entry<?, Integer>> sortedKcore = sort(coreMap);
		int maxK;
		maxK=sortedKcore.get(0).getValue();
		for (V node : originGraph.getVertices()) {	//只留下K-core最大的圖形
			if(coreMap.get(node)!=maxK){
				toRemoveVerteices.add(node);
			}
		}
		for(V term:toRemoveVerteices){
			originGraph.removeVertex(term);
		}
		return originGraph;
	}

	private List<Entry<?, Integer>> sort(Map<V, Integer> unsortingMap) {
		List<Entry<? extends Object, Integer>> sortingList = new ArrayList<Entry<? extends Object, Integer>>(
				unsortingMap.entrySet());
		Collections.sort(sortingList, new Comparator<Map.Entry<?, Integer>>() {
			public int compare(Map.Entry<?, Integer> entry1,
					Map.Entry<?, Integer> entry2) {
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});
		return sortingList;
	}

	private Map<V, Integer> getKcore(Graph<V, E> originGraph) {
		int k = 0;
		Graph<V, E> tempGraph = originGraph;
		Map<V, Integer> outputMap = new HashMap<V, Integer>();
		Set<V> toRemoveVertexs = new HashSet<V>();
		while ( tempGraph.getVertexCount() > 0) {
			/**
			 * 刪除degree<k的node
			 */
			for (V node : tempGraph.getVertices()) { //先存入toRemoveVertexs
				if (tempGraph.degree(node) < k) {
					toRemoveVertexs.add(node);
				}
			}
			for(V node: toRemoveVertexs){	//圖形node從toRemoveVertexs中刪掉
				tempGraph.removeVertex(node);
			}
			/**
			 * 剩餘node,K-core值至少大於k
			 */
			for (V node : tempGraph.getVertices()) { 
				outputMap.put(node, k);
			}
			k++;
		}
		return outputMap;
	}



}
