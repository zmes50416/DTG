/**
 * 
 */
package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import qtag.Tagger;
import tw.edu.ncu.im.Preprocess.*;
import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;

/**
 * Part of speech
 * @author Dean
 *
 */
public class PartOfSpeechDecorator<V extends Node<?>, E extends Edge> extends PreprocessDecorator<V, E> {
	HashMap<V,String> PosDictionary = new HashMap<V,String>();
	public PartOfSpeechDecorator(PreprocessComponent<V,E> _component){
		this.originComponent = _component;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Graph<V, E> execute(File doc) {
		Tagger tagger = null;
		try {// Create a Tagger
			tagger = new Tagger("qtag-eng");
		} catch (IOException e) {
			e.printStackTrace();
		}				

		this.documentGraph = this.originComponent.execute(doc);
		Collection<V> terms = this.documentGraph.getVertices();
		Object[] termArray = terms.toArray();
		List<String> termlist = new ArrayList<String>();

		for(Object term:termArray){
			termlist.add(term.toString());
		}
		String[] tags = tagger.tag(termlist);
		for(int i = 0;i<=tags.length;i++){
			this.PosDictionary.put((V)termArray[i], tags[i]);
		}
		return this.documentGraph;
	}

}
