package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.apache.solr.client.solrj.SolrServerException;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Util.IndexSearcher;
import tw.edu.ncu.im.Util.NGD_calculate;

/**
 * 
 * @author chiang
 *
 * @param <V>
 * @param <E>
 */
public class NGDistanceDecorator<V, E> extends PreprocessDecorator<V, E> {
	HashMap<V, String> vertexTerms = new HashMap<V, String>();
	HashMap<V, Long> termsSearchResult = new HashMap<V, Long>();
	HashMap<E, Double> edgeDistance = new HashMap<E, Double>();
	IndexSearcher searcher;

	/**
	 * 若無提供term搜尋結果數的hashmap 則用以下建構子
	 * 
	 * @param _component
	 * @param _vertexTerms
	 *            存放node與term的string
	 * @param termsSearchResult
	 *            term和對應的搜尋結果數
	 * @param serverURL
	 *            要進行連線搜尋的SolrURL
	 */
	public NGDistanceDecorator(PreprocessComponent<V, E> _component,
			HashMap<V, String> _vertexTerms, String serverURL) {
		super(_component);
		this.vertexTerms = _vertexTerms;
		this.searcher = new IndexSearcher(serverURL);
	}

	/**
	 * 若有提供term搜尋結果數的hashmap 則用以下建構子
	 */
	public NGDistanceDecorator(PreprocessComponent<V, E> _component,
			HashMap<V, String> _vertexTerms,
			HashMap<V, Long> termsSearchResult, String serverURL) {
		this( _component, _vertexTerms,  serverURL);
		this.termsSearchResult = termsSearchResult;
	}




	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		Collection<V> terms = originGraph.getVertices();
		/**
		 * 若termsSearchResult 為空 則先行計算
		 */
		if (this.termsSearchResult.isEmpty() ) {
			for (V term : terms) {
				long SearchResult = 0;
				try {
					SearchResult = this.searcher.searchTermSize(vertexTerms.get(term));
				} catch (SolrServerException e) {// Retry
					try {
						SearchResult = this.searcher.searchTermSize(vertexTerms.get(term));
					} catch (SolrServerException e1) {
						e1.printStackTrace();
					}
				}
				this.termsSearchResult.put(term, SearchResult);
			}
		}

		long term1term2Result = 0;
		for (V term1 : terms) {
			for (V term2 : terms) {
				if (!term1.equals(term2)
						&& originGraph.findEdge(term1, term2) == null) {
					String term1Content = this.vertexTerms.get(term1);
					long term1SearchResult = this.termsSearchResult.get(term1);
					String term2Content = this.vertexTerms.get(term2);
					long term2SearchResult = this.termsSearchResult.get(term2);
					try {
						term1term2Result = this.searcher
								.searchTermSize(term1Content + "+"
										+ term2Content);
					} catch (SolrServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double NGDistance = NGD_calculate.NGD_cal(
							term1SearchResult, term2SearchResult,
							term1term2Result);
					E edge = this.edgeFactory.create();
					edgeDistance.put(edge, NGDistance);
					originGraph.addEdge(edge, term1, term2);
				}
			}
		}
		return originGraph;
	}

}