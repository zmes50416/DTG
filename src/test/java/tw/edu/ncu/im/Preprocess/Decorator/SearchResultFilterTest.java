package tw.edu.ncu.im.Preprocess.Decorator;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import static org.easymock.EasyMock.*;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;
import tw.edu.ncu.im.Util.IndexSearchable;

public class SearchResultFilterTest {
	SearchResultFilter<KeyTerm,TestEdge> testSubject;
	HashMap<KeyTerm,String> termContent;
	IndexSearchable mockSearcher;
	Graph<KeyTerm,TestEdge> graph;
	@Before
	public void setUp() throws Exception {
		this.graph = new UndirectedSparseGraph<KeyTerm,TestEdge>();
		PreprocessComponent<KeyTerm,TestEdge> mockComponent = createMock(PreprocessComponent.class);
		expect(mockComponent.execute(null)).andReturn(graph);
		expect(mockComponent.getEdgeFactory()).andReturn(new Factory<TestEdge>(){

			@Override
			public TestEdge create() {
				return null;
			}
			
		});
		expect(mockComponent.getVertexFactory()).andReturn(new Factory<KeyTerm>(){

			@Override
			public KeyTerm create() {
				return null;
			}
			
		});
		replay(mockComponent);
		termContent = new HashMap<>();
		mockSearcher = createMock(IndexSearchable.class);
		testSubject = new SearchResultFilter<>(mockComponent, termContent,10, 10000, mockSearcher);
		
	}

	@Test
	public void testExecute() throws SolrServerException {
		expect(mockSearcher.searchTermSize(notNull(String.class))).andReturn((long) 100).andReturn((long) 1000000).andReturn((long) 1);
		replay(mockSearcher);
		KeyTerm term1 = new KeyTerm();
		KeyTerm term2 = new KeyTerm();
		KeyTerm term3 = new KeyTerm();
		this.termContent.put(term1, "");
		this.termContent.put(term2, "");
		this.termContent.put(term3, "");
		this.graph.addVertex(term1);
		this.graph.addVertex(term2);
		this.graph.addVertex(term3);
		assertEquals(1,this.testSubject.execute(null).getVertexCount());
	}

}
