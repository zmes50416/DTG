package tw.edu.ncu.im.Preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Factory;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class BBCNewsPreprocessor<V,E> extends PreprocessComponent<V, E> {

	private Map<V, List<HasWord>> vertexContent = new HashMap<>();
	private Map<V, String> strings = new HashMap<>();

	public BBCNewsPreprocessor(Factory<V> _vertexFactory,Factory<E>_edgeFactory){
		this.vertexFactory = _vertexFactory;
		this.edgeFactory = _edgeFactory;
	}
	@Override
	public Graph<V, E> execute(File doc) {
		UndirectedSparseGraph<V,E> documentGraph = new UndirectedSparseGraph<>();
		try (BufferedReader docReader = new BufferedReader(new FileReader(doc))){
			List<List<HasWord>> contents = MaxentTagger.tokenizeText(new FileReader(doc));
			for (List<HasWord> sentence : contents) {
				V node = this.vertexFactory.create();
				this.vertexContent.put(node, sentence);
				documentGraph.addVertex(node);
			}
			
			for(Entry<V, List<HasWord>> sentencePair:vertexContent.entrySet()){
				StringBuilder sentence = new StringBuilder();
				for(HasWord word:sentencePair.getValue()){
					sentence.append(word+" ");
				}
				
				strings.put(sentencePair.getKey(), sentence.toString());
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return documentGraph;
	}
	
	public Map<V,List<HasWord>> getVertexContent() {
		return vertexContent;
	}
	
	public Map<V,String> getStringOfVertex(){
		return strings;
	}
	

}
