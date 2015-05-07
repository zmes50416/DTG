package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServerException;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Util.IndexSearcher;
import edu.uci.ics.jung.graph.Graph;

public class SearchResultFiltingDecorator<V, E> extends PreprocessDecorator<V, E> {
	HashMap<V, String> vertexTerms = new HashMap<V, String>();
	HashMap<V, Long> termsSearchResult = new HashMap<V, Long>();
	IndexSearcher searcher;
	int upperBound,lowerBound;
	public SearchResultFiltingDecorator(PreprocessComponent<V, E> _component,
			HashMap<V, String> _vertexTerms, int LowerBound, int UpperBound,String serverURL) {
		super(_component);
		if(upperBound<lowerBound){
			throw new IllegalArgumentException("UpperBound should not lower than lowerBound");
		}
		this.vertexTerms = _vertexTerms;
		this.searcher = new IndexSearcher();
		this.upperBound = UpperBound;
		this.lowerBound=LowerBound;
		
	}

	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		Collection<V> terms = originGraph.getVertices();
		for (V term : terms) {
			String content = this.vertexTerms.get(term);
			long SearchResult=0;
			try {
				SearchResult=this.searcher.searchTermSize(content);
			} catch (SolrServerException e) {//Retry
				try {
					SearchResult=this.searcher.searchTermSize(content);
				} catch (SolrServerException e1) {
					e1.printStackTrace();
				}
			}
			termsSearchResult.put(term,SearchResult);
		}
		Set<V> toRemoveVerteices = new HashSet<V>();
		for(V term:terms){
			long TermsResult=termsSearchResult.get(term);
			if (TermsResult>this.upperBound || TermsResult<this.lowerBound){
				toRemoveVerteices.add(term);
			}
		}
		
		for(V term:toRemoveVerteices){
			originGraph.removeVertex(term);
			termsSearchResult.remove(term);
		}
		

		return originGraph;
	}

}
