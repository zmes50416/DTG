package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.Edge;
import tw.edu.ncu.im.Preprocess.graph.Node;

/**
 * 字詞之TF加總
 * @author A-Lian
 *
 * @param <V>
 * @param <E>
 */

public class TermFreqDecorator<V, E> extends PreprocessDecorator<V, E>{
	HashMap<V,String> keyWord = new HashMap<V,String>(); // 篩選後的關鍵字
	HashMap<V,Double> termFreqMap = new HashMap<V,Double>(); //關鍵字之TF值
	
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
			
//			for(V node:originGraph.getVertices()){
//				double termFreq = 0.0;
//				if(node_tmp.equals(node)){
//					termFreq = termFreq + 1;
//				}else if(originGraph == null){
//					for(V nodeValue:originGraph.getVertices()){
//						nodeValue = termFreq;
//					}
//				}
//			}
			
			
			
				
			
		}
		// TODO Auto-generated method stub
		return originGraph;
	}
	
}
