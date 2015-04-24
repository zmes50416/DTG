package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;

/**
 * 字詞之TF加總
 * @author A-Lian
 *
 * @param <V>
 * @param <E>
 */

public class TermFreqDecorator<V, E> extends PreprocessDecorator<V, E>{
	HashMap<V,String> keyWord; // 篩選後的關鍵字
	
	/**
	 * 關鍵字之TF值
	 */
	HashMap<V,Double> termFreqMap; 
	/**
	 * 
	 * @param _component :原始的元件
	 * @param content :vertex與term的配對
	 * @param contentValues :vertex與Term Frequency的配對
	 */
	public TermFreqDecorator(PreprocessComponent<V, E> _component, HashMap<V,String> content, HashMap<V,Double> contentValues) {
		super(_component);
		this.keyWord = content;
		this.termFreqMap = contentValues;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Graph<V, E> execute(File doc){
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		for(V node:originGraph.getVertices()){
			Double tf = this.termFreqMap.get(node);
			if(tf != null){
				tf = tf+1;
			}else{
				tf = 1.0;
			}
			this.termFreqMap.put(node, tf);
		}
		return originGraph;
	}
	/**
	 * @return the termFreqMap :關鍵字之TF值
	 */
	public HashMap<V, Double> getTermFreqMap() {
		return termFreqMap;
	}
	
}
