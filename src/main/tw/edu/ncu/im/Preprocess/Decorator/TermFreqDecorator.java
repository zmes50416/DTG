package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
		
	/**
	 * 節點與關鍵字之TF值的配對
	 */
	Map<String,Double> termFreqMap;
	/**
	 * termValue
	 */
	private Map<V,String> termContentMap;
	/**
	 * 
	 * @param _component :原始的元件
	 * @param content :vertex與term的配對
	 * @param contentValues :vertex與Term Frequency的配對
	 */
	public TermFreqDecorator(PreprocessComponent<V, E> _component, Map<V,String> content) {
		super(_component);		
		this.termContentMap = content;
		this.termFreqMap = new HashMap<String,Double>();
	}
	@Override
	public Graph<V, E> execute(File doc){
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		Set<V> removeTerms = new HashSet<V>();
		for(V node:originGraph.getVertices()){
			String term = this.termContentMap.get(node);
			Double tf = this.termFreqMap.get(term);
			if(tf != null){
				tf = tf+1;
				removeTerms.add(node);
			}else{
				tf = 1.0;								
			}
			this.termFreqMap.put(term, tf);
			
		}
		for(V removeTerm:removeTerms){
			originGraph.removeVertex(removeTerm);
			this.termContentMap.remove(removeTerm);
		}
		return originGraph;
	}
	/**
	 * @return the termFreqMap :關鍵字之TF值
	 */
	public HashMap<V, Double> getTermFreqMap() {
		
		HashMap<V, Double> termFreqNodes = new HashMap<V,Double>();
		
		for( Entry<V, String> node:this.termContentMap.entrySet()){
			String term = node.getValue();
			Double tf = this.termFreqMap.get(term);
			termFreqNodes.put(node.getKey(), tf);
		}
		return termFreqNodes;
	}
	
}
