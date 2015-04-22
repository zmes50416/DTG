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

	NgdToleranceFiliter<KeyTerm,GoogleDistance> testSubject;
	private PreprocessComponent<KeyTerm,GoogleDistance> mockComp;
	private Graph<KeyTerm,GoogleDistance> graph;
	private HashMap<GoogleDistance, Double> edgeMaps;
	private HashMap<KeyTerm, Double> termMaps;
	@Before
	public void setUp() throws Exception {
		graph = new UndirectedSparseGraph<>();
		mockComp = createMock(PreprocessComponent.class);
		edgeMaps = new HashMap<GoogleDistance,Double>();
		termMaps = new HashMap<KeyTerm, Double>();
		expect(mockComp.execute(null)).andReturn(graph);
		expect(mockComp.getEdgeFactory()).andReturn(new Factory<GoogleDistance>(){

			@Override
			public GoogleDistance create() {
				return null;
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
		GoogleDistance edge1 = new GoogleDistance(0.01);
		GoogleDistance edge2 = new GoogleDistance(0.5);
		GoogleDistance edge3 = new GoogleDistance(0.4);
		graph.addEdge(edge1, term1, term2);
		graph.addEdge(edge2, term1, term3);
		graph.addEdge(edge3, term2, term3);
		this.edgeMaps.put(edge1, 0.01);
		this.edgeMaps.put(edge2, 0.5);
		this.edgeMaps.put(edge3, 0.4);
		termMaps.put(term1, 3.0);
		termMaps.put(term2, 1.0);
		termMaps.put(term3, 1.0);
		
		Graph<KeyTerm, GoogleDistance> testGraph = this.testSubject.execute(null);
		
		
		
		assertNull(this.graph.findEdge(term1, term2));
		assert
		assertEquals(1, testGraph.getEdgeCount());
	}

}
