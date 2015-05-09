package tw.edu.ncu.im.Util;

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
import tw.edu.ncu.im.Preprocess.Decorator.PreprocessDecorator;

/**
 * TODO Documentation needed!
 * @author chiang
 *
 * @param <V>
 * @param <E>
 */

public class NgdEdgeSorter{

	/**
	 * HashMap轉為Arraylist做排序，從最大到最小排序
	 * @param ngd to sort
	 */
	public static List<Entry<?, Double>> sort(HashMap<? , Double> unsortingMap){
		List<Entry<? extends Object, Double>> sortingList = new ArrayList<Entry<? extends Object, Double>>(unsortingMap.entrySet());
		Collections.sort(sortingList, new Comparator<Map.Entry<?, Double>>() {
			public int compare(Map.Entry<?, Double> entry1,
					Map.Entry<?, Double> entry2) {
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});
		return sortingList;
		//TODO return the LinkedHashMap is a better idea
	}

}
