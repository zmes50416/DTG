package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class NgdSortingDecorator<V, E> extends PreprocessDecorator<V, E> {

	HashMap<V, String> vertexTerms = new HashMap<V, String>();
	HashMap<E, Double> ngdMap = new HashMap<E, Double>();
	List<Entry<E, Double>> ngdList = new ArrayList<Entry<E, Double>>();
	/**
	 * list getter
	 * @return
	 */
	public List<Entry<E, Double>> getNgdList(){
		return ngdList;
	}

	/**
	 * 
	 * @param _component
	 * @param _vertexTerms
	 *            放term與node
	 * @param _edgeDistance
	 *            放edge與distance
	 */
	public NgdSortingDecorator(PreprocessComponent<V, E> _component,
			HashMap<V, String> _vertexTerms, HashMap<E, Double> _edgeDistance) {
		super(_component);
		this.vertexTerms = _vertexTerms;
		this.ngdMap = _edgeDistance;
		/**
		 * HashMap轉為Arraylist做排序，自定義排序法
		 */
	}
	
	public List<Entry<E, Double>> sortingList(HashMap<E, Double> unsortingMap){
		List<Entry<E, Double>> sortingList = new ArrayList<Entry<E, Double>>(unsortingMap.entrySet());
		Collections.sort(sortingList, new Comparator<Map.Entry<E, Double>>() {
			public int compare(Map.Entry<E, Double> entry1,
					Map.Entry<E, Double> entry2) {
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});
		return sortingList;
	}
	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		this.ngdList=sortingList(this.ngdMap);
		return originGraph;
	}

}
