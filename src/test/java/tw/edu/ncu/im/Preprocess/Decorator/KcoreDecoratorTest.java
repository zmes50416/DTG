package tw.edu.ncu.im.Preprocess.Decorator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.junit.Before;
import org.junit.Test;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class KcoreDecoratorTest {
	KcoreDecorator<KeyTerm, TestEdge> testSubject;
	Graph<KeyTerm, TestEdge> graph;
	HashMap<TestEdge, Double> edgeDistance;
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
		edgeDistance = new HashMap<>();
		testSubject = new KcoreDecorator<KeyTerm,TestEdge>(mockComponent, edgeDistance, 0.4);
	}

	@Test
	public void test() {
/**
 * node1: 2,3,4,6,7
 * node2: 1,3,4,6
 * node3: 1,2,4
 * node4: 1,2,3
 * node5: 7
 * node6: 1,2,7
 * node7: 1,5,6
 * k=3: 1,2,3,4,6
 * k=2: 7
 * k=1: 5
 */
		KeyTerm term1 = new KeyTerm();
		KeyTerm term2 = new KeyTerm();
		KeyTerm term3 = new KeyTerm();
		KeyTerm term4 = new KeyTerm();
		KeyTerm term5 = new KeyTerm();
		KeyTerm term6 = new KeyTerm();
		KeyTerm term7 = new KeyTerm();
		
		this.graph.addVertex(term1);
		this.graph.addVertex(term2);
		this.graph.addVertex(term3);
		this.graph.addVertex(term4);
		this.graph.addVertex(term5);
		this.graph.addVertex(term6);
		this.graph.addVertex(term7);
		TestEdge edge1 = new TestEdge();
		TestEdge edge2 = new TestEdge();
		TestEdge edge3 = new TestEdge();
		TestEdge edge4 = new TestEdge();
		TestEdge edge5 = new TestEdge();
		TestEdge edge6 = new TestEdge();
		TestEdge edge7 = new TestEdge();
		TestEdge edge8 = new TestEdge();
		TestEdge edge9 = new TestEdge();
		TestEdge edge10 = new TestEdge();
		TestEdge edge11 =  new TestEdge();
		TestEdge edge12 =  new TestEdge();
		TestEdge edge13 =  new TestEdge();
		
		this.edgeDistance.put(edge1, 0.1);
		this.edgeDistance.put(edge2, 0.2);
		this.edgeDistance.put(edge3, 0.3);
		this.edgeDistance.put(edge4, 0.4);
		this.edgeDistance.put(edge5, 0.01);
		this.edgeDistance.put(edge6, 0.4);
		this.edgeDistance.put(edge7, 0.35);
		this.edgeDistance.put(edge8, 0.2);
		this.edgeDistance.put(edge9, 0.3);
		this.edgeDistance.put(edge10, 0.4);
		this.edgeDistance.put(edge11, 0.01);
		this.edgeDistance.put(edge12, 0.5);
		this.edgeDistance.put(edge13, 1.0);
		
		this.graph.addEdge(edge1, term1, term2);
		this.graph.addEdge(edge2, term1, term3);
		this.graph.addEdge(edge3, term1, term4);
		this.graph.addEdge(edge4, term1, term6);
		this.graph.addEdge(edge5, term1, term7);
		this.graph.addEdge(edge6, term2, term3);
		this.graph.addEdge(edge7, term2, term4);
		this.graph.addEdge(edge8, term2, term6);
		this.graph.addEdge(edge9, term3, term4);
		this.graph.addEdge(edge10, term5, term7);
		this.graph.addEdge(edge11, term6, term7);
		/**
		 * 12,13超過門檻
		 */
		this.graph.addEdge(edge12, term1, term5);
		this.graph.addEdge(edge13, term5, term6);
		
		this.testSubject.execute(null);
		assertEquals("0.4",testSubject.edgeThreshold.toString());
		assertEquals(false,testSubject.ngdMap.get(edge1)>0.4);
		assertEquals(7,testSubject.coreMap.size());
		assertEquals(7,this.graph.getVertexCount());
		assertEquals(11,this.graph.getEdgeCount());
	}

}
