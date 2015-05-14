package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.uci.ics.jung.graph.Graph;

public class StandfordPartOfSpeechFiliter<V,E> extends PreprocessDecorator<V, E> {
	
	/** 
	 * 節點的字詞內容配對保持在此，在<code>execute()</code>以後可以從此得到POS過濾結果
	 */
	Map<V,String> vertexResultsTerms = new HashMap<V,String>();
	Map<V,List<HasWord>> vertextTerms;
	static MaxentTagger tagger = new MaxentTagger("tagger-models/english-bidirectional-distsim.tagger");// 單字過濾，根據Standford 的POS

	public StandfordPartOfSpeechFiliter(PreprocessComponent<V, E> _component,Map<V,List<HasWord>> _vertextTerms) {
		super(_component);
		this.vertextTerms = _vertextTerms;
	}
	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> docGraph = this.originComponent.execute(doc);
		List<String> pharses = new ArrayList<>();
		HashSet<V> nodesToRemove = new HashSet<>();

		for(Entry<V, List<HasWord>> sentencePair:this.vertextTerms.entrySet()){
	        List<HasWord> sentence = sentencePair.getValue();
			List<TaggedWord> taggedList = tagger.tagSentence(sentence);
			pharses.addAll(this.compoundPharse(taggedList));
			nodesToRemove.add(sentencePair.getKey());
		}
		for(V node:nodesToRemove){
			docGraph.removeVertex(node);
		}
		for(String pharse:pharses){
			V node = this.vertexFactory.create();
			this.vertexResultsTerms.put(node, pharse);
			docGraph.addVertex(node);
		}
		return docGraph;
	}
	/**
	 * compund words to combined pharse
	 * @param words words tagged by tagger
	 * @return all possible pharse
	 */
	private List<String> compoundPharse(List<TaggedWord> words) {
		List<String> pharses = new ArrayList<>();
		for (int i = 0; i < words.size(); i++) {
			String key3 = words.get(i).word();
			if ((words.get(i).tag().contains("NN"))) { 	//tag are different than old pos, have to included the countable noun
				pharses.add(key3);
				try {// 組合字過濾
					String key2 = words.get(i-1).word();
					if (words.get(i-1).tag().equals("JJ")) {
						String combinePharse = key2 + "+" + key3;
						pharses.add(combinePharse);
					} else if (words.get(i-1).tag().contains("NN")) {
						String key1 = words.get(i-2).word();
						if (words.get(i-2).tag().contains("NN")	|| words.get(i-2).tag().equals("JJ")) {
							String combineThirdPharse = key1 + "+" + key2
									+ "+" + key3;
							pharses.add(combineThirdPharse);
						}
					}
				} catch (IndexOutOfBoundsException e) {
					// 字詞找不到可以連接的更前面字詞,會發生在前1或2的單詞的地方 Do nothing
				}
			}

		
	}
		return pharses;

	}
	
	/**
	 * @return the vertexResultsTerms
	 */
	public Map<V, String> getVertexResultsTerms() {
		return vertexResultsTerms;
	}
		

}
