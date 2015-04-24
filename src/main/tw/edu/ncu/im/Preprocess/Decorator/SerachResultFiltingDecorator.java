package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Util.IndexStatus;
import tw.edu.ncu.im.Util.ServerUtil;

public class SerachResultFiltingDecorator<V, E> extends PreprocessDecorator<V, E> {
	HashMap<V, String> vertexTerms = new HashMap<V, String>();

	public SerachResultFiltingDecorator(PreprocessComponent<V, E> _component,
			HashMap<V, String> _vertexTerms, int LowerBound, int UpperBound) {
		super(_component);
		// TODO Auto-generated constructor stub
		this.vertexTerms = _vertexTerms;
	}

	@Override
	public Graph<V, E> execute(File doc) {
		// TODO Auto-generated method stub
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		this.documentGraph = new UndirectedSparseGraph<V, E>();
		Collection<V> terms = originGraph.getVertices();
		HashMap<V, Long> TermsSearchResult = new HashMap<V, Long>();
		List<Integer> result = new ArrayList<>();

		for (V term : terms) {
			String content = this.vertexTerms.get(term);
			try {
				TermsSearchResult.put(term,IndexStatus.indexed(content));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(V termFilter:terms){
			String content = this.vertexTerms.get(termFilter);
			TermFilter(content);
		}
		
		return this.documentGraph;
	}

	private void TermFilter(String content) {
		// TODO Auto-generated method stub
		
	}

}
