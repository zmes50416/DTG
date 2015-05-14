/**
 * 
 */
package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.collections15.Factory;

import qtag.Tagger;
import tw.edu.ncu.im.Preprocess.*;
import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Part of speech filter,提供節點內的詞性資訊，並且過濾非規定的詞性
 * POS的作法會被字詞內容所影響，務必使用完整的字句以得到正確的詞性結果
 * @author Dean
 *
 */
public class PartOfSpeechFilter<V, E> extends PreprocessDecorator<V, E> {
	/** 
	 * 節點的字詞內容配對保持在此，在<code>execute()</code>以後可以從此得到POS過濾結果
	 */
	Map<V,String> vertexResultsTerms = new HashMap<V,String>();
	Map<V,String> vertextTerms;
	/**
	 * Constructor, 連結元件
	 * @param _component latest document graph
	 * @param sentenceMap mapping between String content and Vertex
	 */
	public PartOfSpeechFilter(PreprocessComponent<V,E> _component,Map<V, String> sentenceMap){
		super(_component);		
		this.vertextTerms = sentenceMap;
	}
	/**
	 * 過濾非NN,NP的詞性，並且尋找可能的複合單字
	 * P.S. 過去的圖形會被丟棄， 依據節點內容來重新建立新的節點，無連線
	 */
	@Override
	public Graph<V, E> execute(File doc) {
				
		 
		Graph<V,E> originGraph = this.originComponent.execute(doc);
		Collection<V> terms = originGraph.getVertices();
		HashSet<V> termsToRemove = new HashSet<>();
		List<String> pharses = new ArrayList<>();//all possible terms
		
		for(V term:terms){
			String content = this.vertextTerms.get(term);
			List<String> tokens = this.tokenize(content);
			String[] tokensArray = new String[tokens.size()];
			tokensArray = tokens.toArray(tokensArray);
			termsToRemove.add(term);
			pharses.addAll(this.compoundPharse(tokensArray));
		}
		for(V term:termsToRemove){
			originGraph.removeVertex(term);
		}
		for(String pharse:pharses){
			V node = this.vertexFactory.create();
			this.vertexResultsTerms.put(node, pharse);
			originGraph.addVertex(node);
		}

		return originGraph;
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
			for (int i = 0; i < sentence.length; i++) {
				String key3 = sentence[i];
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
					} catch (ArrayIndexOutOfBoundsException e) {
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
	/**
	 * @return the vertexResultsTerms
	 */
	public Map<V, String> getVertexResultsTerms() {
		return vertexResultsTerms;
	}
	
}
