package tw.edu.ncu.im.Preprocess.Decorator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Factory;
import org.junit.Before;
import org.junit.Test;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class NgdFilterTest {
	NgdFilter<KeyTerm, TestEdge> testSubject;
	HashMap<KeyTerm, String> termContent;
	HashMap<TestEdge, Double> edgeDistance;
	List<Entry<TestEdge, Double>> distanceList;
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
		distanceList= new ArrayList<Entry<TestEdge, Double>>();
		testSubject = new NgdFilter<KeyTerm, TestEdge>(mockComponent,
				termContent, edgeDistance,distanceList,0.5);
	}

	@Test
	public void test() {
		KeyTerm term1 = new KeyTerm();
		KeyTerm term2 = new KeyTerm();
		KeyTerm term3 = new KeyTerm();
		KeyTerm term4 = new KeyTerm();
		KeyTerm term5 = new KeyTerm();
		
		this.termContent.put(term1, "John");
		this.termContent.put(term2, "Mary");
		this.termContent.put(term3, "Bob");
		this.termContent.put(term4, "Amy");
		this.termContent.put(term5, "Lucy");
		
		TestEdge edge1 = new TestEdge();
		TestEdge edge2 = new TestEdge();
		TestEdge edge3 = new TestEdge();
		TestEdge edge4 = new TestEdge();
		TestEdge edge5 = new TestEdge();
		TestEdge edge6 = new TestEdge();
		TestEdge edge7 = new TestEdge();
		
		this.edgeDistance.put(edge1, 0.5);
		this.edgeDistance.put(edge2, 0.6);
		this.edgeDistance.put(edge3, 0.4);
		this.edgeDistance.put(edge4, 0.1);
		this.edgeDistance.put(edge5, 0.01);
		this.edgeDistance.put(edge6, 0.8);
		this.edgeDistance.put(edge7, 0.9);
		
		this.graph.addEdge(edge1, term1, term2);
		this.graph.addEdge(edge2, term1, term3);
		this.graph.addEdge(edge3, term3, term2);
		this.graph.addEdge(edge4, term4, term2);
		this.graph.addEdge(edge5, term5, term2);
		this.graph.addEdge(edge6, term5, term1);
		this.graph.addEdge(edge7, term5, term3);
		
		this.distanceList.addAll(this.edgeDistance.entrySet());
		Collections.sort(this.distanceList, new Comparator<Map.Entry<TestEdge, Double>>() {
			public int compare(Map.Entry<TestEdge, Double> entry1,
					Map.Entry<TestEdge, Double> entry2) {
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});
		
		this.testSubject.execute(null);
		
		assertEquals(this.termContent.size(),graph.getVertexCount());
		assertEquals( 7,this.testSubject.getNgdList().size());
		assertEquals("0.5",this.testSubject.rankThresholdPercent.toString());
		assertEquals("0.9",this.distanceList.get(0).getValue().toString());
		assertEquals(3,this.graph.getEdgeCount());
		assertEquals(null,this.testSubject.ngdMap.get(edge1));
		assertEquals(null,this.testSubject.ngdMap.get(edge3));
		assertEquals(null,this.testSubject.ngdMap.get(edge4));
		assertEquals(null,this.testSubject.ngdMap.get(edge5));
		assertEquals("0.9",this.testSubject.ngdMap.get(edge7).toString());
		assertEquals(false,this.graph.containsEdge(edge1));
		assertEquals(true,this.graph.containsEdge(edge2));
		assertEquals(true,this.graph.containsEdge(edge6));
		assertEquals(true,this.graph.containsEdge(edge7));
		
	}

}