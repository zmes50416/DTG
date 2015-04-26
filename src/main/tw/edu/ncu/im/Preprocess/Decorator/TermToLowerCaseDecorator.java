package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
/**
 * Term To Lower-Case
 * @author A-Lian
 *
 * @param <V>
 * @param <E>
 */

public class TermToLowerCaseDecorator<V,E> extends PreprocessDecorator<V, E> {
	HashMap<V, String> vertexContent;
	/**
	 * 
	 * @param _component 原始的元件
	 * @param content vertex 與 term的配對 
	 */
	public TermToLowerCaseDecorator(PreprocessComponent<V, E> _component, HashMap<V,String> content) {
		super(_component);
		this.vertexContent = content;
	}
	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		for(V node:originGraph.getVertices()){
			
			String termLowerCase = (this.vertexContent.get(node)).toLowerCase();
			this.vertexContent.put(node, termLowerCase);
		}
		return originGraph;
	}
	
	public HashMap<V, String> getVertexContent() {
		return vertexContent;
	}
	

}
