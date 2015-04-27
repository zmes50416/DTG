package tw.edu.ncu.im.Preprocess.Decorator;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import static org.easymock.EasyMock.*;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.*;

public class NgdToleranceFiliterTest{

	NgdToleranceFiliter<KeyTerm,TestEdge> testSubject;
	private PreprocessComponent<KeyTerm,TestEdge> mockComp;
	private Graph<KeyTerm,TestEdge> graph;
	private HashMap<TestEdge, Double> edgeMaps;
	private HashMap<KeyTerm, Double> termMaps;
	@Before
	public void setUp() throws Exception {
		graph = new UndirectedSparseGraph<>();
		mockComp = createMock(PreprocessComponent.class);
		edgeMaps = new HashMap<TestEdge,Double>();
		termMaps = new HashMap<KeyTerm, Double>();
		expect(mockComp.execute(null)).andReturn(graph);
		expect(mockComp.getEdgeFactory()).andReturn(new Factory<TestEdge>(){

			@Override
			public TestEdge create() {
				return new TestEdge(0);
			}
			
		});
		expect(mockComp.getVertexFactory()).andReturn(new Factory<KeyTerm>(){

			@Override
			public KeyTerm create() {
				return null;
			}
			
		});
		replay(mockComp);
		

		
		this.testSubject = new NgdToleranceFiliter<>(mockComp,0.1,edgeMaps,termMaps);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		KeyTerm term1 = new KeyTerm("Google");
		KeyTerm term2 = new KeyTerm("Samsung");
		KeyTerm term3 = new KeyTerm("Apple");
		KeyTerm term4 = new KeyTerm("Microsoft");
		TestEdge edge1 = new TestEdge(0.01);
		TestEdge edge2 = new TestEdge(0.5);
		TestEdge edge3 = new TestEdge(0.4);
		TestEdge edge4 = new TestEdge(0.8);
		graph.addEdge(edge1, term1, term2);
		graph.addEdge(edge2, term1, term3);
		graph.addEdge(edge3, term2, term3);
		graph.addEdge(edge4, term4, term2);
		this.edgeMaps.put(edge1, 0.01);
		this.edgeMaps.put(edge2, 0.5);
		this.edgeMaps.put(edge3, 0.4);
		this.edgeMaps.put(edge4, 0.8);
		termMaps.put(term1, 3.0);
		termMaps.put(term2, 1.0);
		termMaps.put(term3, 1.0);
		termMaps.put(term4, 1.5);
		
		Graph<KeyTerm, TestEdge> testGraph = this.testSubject.execute(null);
		
		
		assertEquals("Should have right number of vertex",this.termMaps.size(),graph.getVertexCount());
		assertNull(this.graph.findEdge(term1, term2));
		assertEquals("the edge should be change to 0.4 instead of 0.5",0.4,this.edgeMaps.get(graph.findEdge(term1, term3)),0);
		
		assertNotNull("term 4 should connect to term1 instead of term2",this.graph.findEdge(term4, term1));
		assertEquals(this.edgeMaps.size(), testGraph.getEdgeCount());
		
	}

}
