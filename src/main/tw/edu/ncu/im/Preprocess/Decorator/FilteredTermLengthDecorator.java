package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;

public class FilteredTermLengthDecorator<V,E> extends PreprocessDecorator<V, E> {
	/**
	 * vertexContent :vertex與Term的配對
	 */
	HashMap<V, String> vertexContent;
	/**
	 * 
	 * @param _component :原始的元件
	 * @param content :vertex與Term的配對
	 */
	public FilteredTermLengthDecorator(PreprocessComponent<V, E> _component, HashMap<V, String> content) {
		super(_component);
		this.vertexContent = content;
	}
	
	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		for(V node:originGraph.getVertices()){
			String term = this.vertexContent.get(node);
			if(term.length() < 3){
				this.vertexContent.remove(node);
			}
		}
		return originGraph;
	}

	/**
	 * @return the vertexContent :vertex與Term的配對
	 */
	public HashMap<V, String> getVertexContent() {
		return vertexContent;
	}

}
