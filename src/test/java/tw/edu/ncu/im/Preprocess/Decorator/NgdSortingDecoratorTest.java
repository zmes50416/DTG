package tw.edu.ncu.im.Preprocess.Decorator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;

/**
 * 
 * @author chiang
 *
 */
public class NgdSortingDecoratorTest {
	/**
	 * @param termContent
	 *            node 與term
	 * @param edgeDistance
	 *            edge 與 距離
	 * 
	 */
	NgdSortingDecorator<KeyTerm, TestEdge> testSubject;
	HashMap<KeyTerm, String> termContent;
	HashMap<TestEdge, Double> edgeDistance;
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
						return null;
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
		edgeDistance = new HashMap<>();
		testSubject = new NgdSortingDecorator<KeyTerm, TestEdge>(mockComponent,
				termContent, edgeDistance);
	}

	@Test
	public void test() {

		KeyTerm term1 = new KeyTerm();
		KeyTerm term2 = new KeyTerm();
		KeyTerm term3 = new KeyTerm();
		this.termContent.put(term1, "apple");
		this.termContent.put(term2, "banana");
		this.termContent.put(term3, "orange");
		this.graph.addVertex(term1);
		this.graph.addVertex(term2);
		this.graph.addVertex(term3);
		TestEdge edge1 = new TestEdge();
		TestEdge edge2 = new TestEdge();
		TestEdge edge3 = new TestEdge();
		this.edgeDistance.put(edge1, 0.5);
		this.edgeDistance.put(edge2, 0.6);
		this.edgeDistance.put(edge3, 0.4);
		this.graph.addEdge(edge1, term1, term2);
		this.graph.addEdge(edge2, term1, term3);
		this.graph.addEdge(edge3, term3, term2);

		this.testSubject.execute(null);
		
		assertEquals(3, this.testSubject.vertexTerms.size());
		assertEquals(3, this.testSubject.ngdMap.size());
		assertEquals(3, this.testSubject.getNgdList().size());
		assertEquals("0.6", this.testSubject.ngdList.get(0).getValue().toString());
		assertEquals("0.5", this.testSubject.ngdList.get(1).getValue().toString());
		assertEquals("0.4", this.testSubject.ngdList.get(2).getValue().toString());
	}

}
