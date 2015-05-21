package tw.edu.ncu.im.Preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections15.Factory;

import tw.edu.ncu.im.Preprocess.graph.*;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RouterNewsPreprocessor<V,E> extends PreprocessComponent<V,E> {
	HashSet<KeyTerm> terms;
	HashSet<TestEdge> edges;
	Map<V,List<HasWord>> vertexContent = new HashMap<>();
	Map<V,String> strings = new HashMap<>();
	/**
	 * @return the vertexContent
	 */
	public Map<V,List<HasWord>> getVertexContent() {
		return vertexContent;
	}
	
	public Map<V,String> getStringOfVertex(){
		return strings;
	}
	
	public RouterNewsPreprocessor(Factory<V> _vertexFactory,Factory<E>_edgeFactory){
		this.vertexFactory = _vertexFactory;
		this.edgeFactory = _edgeFactory;
	}

	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> documentGraph = new UndirectedSparseGraph<V, E>();
		try (BufferedReader docReader = new BufferedReader(new FileReader(doc))){
			//First Line of Router data is title and Second Line is content
			List<List<HasWord>> title =  MaxentTagger.tokenizeText(new StringReader(docReader.readLine()));
			V titleNode = this.vertexFactory.create();
			this.vertexContent.put(titleNode, title.get(0));
			documentGraph.addVertex(titleNode);
			List<List<HasWord>> contents = MaxentTagger.tokenizeText(new StringReader(docReader.readLine()));
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
	
	private Set<String> sentence(String line){
		line=line.replace("]", "");
		line=line.replace("[", "");
		line=line.replace("<", "");
		line=line.replace(">", "");
		return null;
		
	}
	
	
	
}