package tw.edu.ncu.im.Preprocess.Decorator;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;
import tw.edu.ncu.im.Util.IndexSearchable;

public class NGDistanceDecoratorTest {
	NGDistanceDecorator<KeyTerm, TestEdge> testSubject;
	HashMap<KeyTerm, String> termContent;
	HashMap<KeyTerm, Long> termSearchResult;
	IndexSearchable mockSearcher;
	Graph<KeyTerm, TestEdge> graph;

	@Before
	public void setUp() throws Exception {
		this.graph = new UndirectedSparseGraph<KeyTerm, TestEdge>();
		PreprocessComponent<KeyTerm, TestEdge> mockComponent = createMock(PreprocessComponent.class);
		expect(mockComponent.execute(null)).andReturn(graph);
		expect(mockComponent.getEdgeFactory()).andReturn(
				new Factory<TestEdge>() {

					@Override
					public TestEdge create() {
						return new TestEdge();
					}

				});
		expect(mockComponent.getVertexFactory()).andReturn(
				new Factory<KeyTerm>() {

					@Override
					public KeyTerm create() {
						return null;
					}

				});
		replay(mockComponent);
		termContent = new HashMap<>();
		termSearchResult = new HashMap<>();
		/*testSubject = new NGDistanceDecorator<>(mockComponent, termContent,
				termSearchResult, "http://TEST");*/
		mockSearcher = createMock(IndexSearchable.class);
		testSubject = new NGDistanceDecorator<KeyTerm, TestEdge>(mockComponent, termContent,
				mockSearcher);
	}

	@Test
	public void testExecute() throws SolrServerException {
		testSubject.searcher = mockSearcher;
		expect(mockSearcher.searchTermSize(notNull(String.class)))
				.andReturn((long) 1000).andReturn((long) 100)
				.andReturn((long) 20).andReturn((long) 800).andReturn((long) 900)
				.andReturn((long) 2000);
		replay(mockSearcher);
		KeyTerm term1 = new KeyTerm();
		KeyTerm term2 = new KeyTerm();
		KeyTerm term3 = new KeyTerm();
		this.termContent.put(term1, "apple");
		this.termContent.put(term2, "banana");
		this.termContent.put(term3, "orange");
		/*
		 * this.termSearchResult.put(term1,(long)2000);
		 * this.termSearchResult.put(term2,(long)6000);
		 * this.termSearchResult.put(term3,(long)12000);
		 */
		this.graph.addVertex(term1);
		this.graph.addVertex(term2);
		this.graph.addVertex(term3);

		assertEquals(3, this.testSubject.execute(null).getEdgeCount());
	}
}
