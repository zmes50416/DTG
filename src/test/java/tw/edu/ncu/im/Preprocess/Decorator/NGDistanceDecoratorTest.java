package tw.edu.ncu.im.Preprocess.Decorator;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

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
		mockSearcher = createMock(IndexSearchable.class);
		testSubject = new NGDistanceDecorator<KeyTerm, TestEdge>(mockComponent, termContent,
				mockSearcher);
	}

	@Test
	public void testExecute() throws SolrServerException {
		testSubject.searcher = mockSearcher;
		
		KeyTerm term1 = new KeyTerm("apple");
		KeyTerm term2 = new KeyTerm("banana");
		KeyTerm term3 = new KeyTerm("orange");
		this.termContent.put(term1, "apple");
		this.termContent.put(term2, "banana");
		this.termContent.put(term3, "orange");		
		expect(mockSearcher.searchTermSize(termContent.get(term1))).andReturn(5000L);
		expect(mockSearcher.searchTermSize(termContent.get(term2))).andReturn(8000L);
		expect(mockSearcher.searchTermSize(termContent.get(term3))).andReturn(2000L);
		expect(mockSearcher.searchMultipleTerm(isA(String[].class))).andReturn(1000L).times(3);
		
		replay(mockSearcher);

		/*
		 * this.termSearchResult.put(term1,(long)2000);
		 * this.termSearchResult.put(term2,(long)6000);
		 * this.termSearchResult.put(term3,(long)12000);
		 */
		this.graph.addVertex(term1);
		this.graph.addVertex(term2);
		this.graph.addVertex(term3);
		Graph<KeyTerm, TestEdge> docGraph = this.testSubject.execute(null);
		Map<TestEdge, Double> distances = this.testSubject.getEdgeDistance();
		Double term1_term2 = (Math.max(Math.log10(5000L), Math.log10(8000L)) - Math.log10(1000L))
				/ (6.4966 - Math.min(Math.log10(5000L), Math.log10(8000L)));
		Double term2_term3 = (Math.max(Math.log10(8000L), Math.log10(2000L)) - Math.log10(1000L))
				/ (6.4966 - Math.min(Math.log10(8000L), Math.log10(2000L)));
		Double term1_term3 = (Math.max(Math.log10(5000L), Math.log10(2000L)) - Math.log10(1000L))
				/ (6.4966 - Math.min(Math.log10(5000L), Math.log10(2000L)));
		assertEquals(3,docGraph.getEdgeCount());
		assertEquals(term1_term2,this.testSubject.edgeNGDistance.get(docGraph.findEdge(term1, term2)));
		assertEquals(term2_term3,this.testSubject.edgeNGDistance.get(docGraph.findEdge(term2, term3)));
		assertEquals(term1_term3,this.testSubject.edgeNGDistance.get(docGraph.findEdge(term1, term3)));
	}
}
