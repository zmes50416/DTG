package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;

public class FilteredTermLengthDecorator<V,E> extends PreprocessDecorator<V, E> {
	/**
	 * vertexContent :vertex與Term的配對
	 */
	Map<V, String> vertexContent;
	
	/**
	 * minLength :term之最小長度
	 */
	int minLength;
	
	/**
	 * 
	 * @param _component :原始的元件
	 * @param content :vertex與Term的配對
	 */
	public FilteredTermLengthDecorator(PreprocessComponent<V, E> _component, Map<V, String> content, int _minLength) {
		super(_component);
		this.vertexContent = content;
		this.minLength = _minLength;
	}
	
	@Override
	public Graph<V, E> execute(File doc) {
		
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		Set<V> removeTerms = new HashSet<V>();
		for(V node:originGraph.getVertices()){
			String term = this.vertexContent.get(node);
			if(term.length() < minLength){
				removeTerms.add(node);
			}
		}
		for(V removeTerm:removeTerms){
			originGraph.removeVertex(removeTerm);
			this.vertexContent.remove(removeTerm);
		}
		return originGraph;
	}

	/**
	 * @return the vertexContent :vertex與Term的配對
	 */
	public Map<V, String> getVertexContent() {
		return vertexContent;
	}

}
