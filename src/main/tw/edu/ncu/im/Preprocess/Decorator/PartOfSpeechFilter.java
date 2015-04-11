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
import java.util.StringTokenizer;

import org.apache.commons.collections15.Factory;

import qtag.Tagger;
import tw.edu.ncu.im.Preprocess.*;
import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Part of speech filter,提供節點內的詞性資訊，並且過濾非規定的詞性
 * @author Dean
 *
 */
public class PartOfSpeechFilter<V, E> extends PreprocessDecorator<V, E> {
	HashMap<V,String> vertexTerms = new HashMap<V,String>();//Transform
	public PartOfSpeechFilter(PreprocessComponent<V,E> _component,HashMap<V,String> _vertextTerms){
		this.originComponent = _component;
		this.vertexTerms = _vertextTerms;
	}
	/**
	 * 過濾非NN,NP的詞性
	 */
	@Override
	public Graph<V, E> execute(File doc) {
				
		 
		Graph<V,E> originGraph = this.originComponent.execute(doc);
		this.documentGraph = new UndirectedSparseGraph<V,E>();
		Collection<V> terms = originGraph.getVertices();
		HashMap<V,String> newVertexTerms = new HashMap<>();
		List<String> pharses = new ArrayList<>();
		for(V term:terms){
			String content = this.vertexTerms.get(term);
			List<String> tokens = this.tokenize(content);
			pharses.addAll(this.compoundPharse((String[]) tokens.toArray()));
		}
		Factory<V> vertexFactory = this.originComponent.getVertexFactory();
		for(String pharse:pharses){
			V node = vertexFactory.create();
			newVertexTerms.put(node, pharse);
			this.documentGraph.addVertex(node);
		}
		
		return this.documentGraph;
	}
	private List<String> tokenize(String line){
		StringTokenizer st = new StringTokenizer(line);
		ArrayList<String> result = new ArrayList<>();
		while(st.hasMoreTokens()){
			result.add(st.nextToken());
		}
		return result;
	}
	
	private List<String> compoundPharse(String[] sentence) {
		try {
			Tagger tagger = new Tagger("qtag-eng");
			List<String> pharses = new ArrayList<>();
			String[] tags = tagger.tag(sentence);
			for (int i = 0; i <= sentence.length; i++) {
				String key3 = sentence[i];
				String word;
				if ((tags[i].equals("NN") || tags[i].equals("NP"))) { 	// 單字過濾，根據D. Tufis and O. Mason於1998提出的Qtag
					pharses.add(key3);
					try {// 組合字過濾
						String key2 = sentence[i - 1];
						if (tags[i - 1].equals("JJ")) {
							String combinePharse = key2 + "+" + key3;
							pharses.add(combinePharse);
						} else if (tags[i - 1].equals("NN")
								|| tags[i - 1].equals("NP")) {
							String key1 = sentence[i - 2];
							if (tags[i - 2].equals("NN")
									|| tags[i - 2].equals("NP")
									|| tags[i - 2].equals("JJ")) {
								String combineThirdPharse = key1 + "+" + key2
										+ "+" + key3;
								pharses.add(combineThirdPharse);
							}
						}
					} catch (IndexOutOfBoundsException e) {
						// 字詞找不到可以連接的更前面字詞,會發生在前1或2的單詞的地方 Do nothing
					}

				}

			}// end of for

			return pharses;
		} catch (IOException e) {
			System.err.println("Can't find Qtag corpus");
			e.printStackTrace();
			return null;
		}
	}
	
}
