package tw.edu.ncu.im.Preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	/**
	 * @return the vertexContent
	 */
	public Map<V,List<HasWord>> getVertexContent() {
		return vertexContent;
	}
	public RouterNewsPreprocessor(Factory<V> _vertexFactory,Factory<E>_edgeFactory){
		this.vertexFactory = _vertexFactory;
		this.edgeFactory = _edgeFactory;
	}

	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> documentGraph = new UndirectedSparseGraph<V, E>();
		try {
			List<List<HasWord>> sentences = null;
			sentences = MaxentTagger.tokenizeText(new FileReader(doc));

			for (List<HasWord> sentence : sentences) {
				V node = this.vertexFactory.create();
				this.vertexContent.put(node, sentence);
				documentGraph.addVertex(node);
			}

		} catch (FileNotFoundException e) {
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