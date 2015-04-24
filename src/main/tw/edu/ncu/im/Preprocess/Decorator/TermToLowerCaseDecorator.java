package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.graph.Edge;
import tw.edu.ncu.im.Preprocess.graph.Node;
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
	public TermToLowerCaseDecorator(PreprocessComponent<V, E> _component, HashMap<V,String> content) {
		super(_component);
		this.vertexContent = content;
		// TODO Auto-generated constructor stub
	}
	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		for(V node:originGraph.getVertices()){
			
			String termLowerCase = (this.vertexContent.get(node)).toLowerCase();
			this.vertexContent.put(node, termLowerCase);
		}
		// TODO Auto-generated method stub
		return originGraph;
	}
	

}
