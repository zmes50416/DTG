/**
 * 
 */
package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.Collection;

import tw.edu.ncu.im.Preprocess.*;
import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;

/**
 * Part of speech
 * @author Dean
 *
 */
public class PartOfSpeechDecorator<V extends Node<?>, E extends Edge> extends PreprocessDecorator<V, E> {

	public PartOfSpeechDecorator(PreprocessComponent<V,E> _component){
		this.originComponent = _component;
	}
	@Override
	public Graph<V, E> execute(File doc) {
		
		this.documentGraph = this.originComponent.execute(doc);
		Collection<V> terms = this.documentGraph.getVertices();
		for(V term: terms){
			String termName = term.toString();
			if(term.toString()){
				
			}
		}
		return this.documentGraph;
	}

}
