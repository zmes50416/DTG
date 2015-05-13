package tw.edu.ncu.im.Preprocess.Decorator;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import static org.easymock.EasyMock.*;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;

public class FilteredTermLengthDecoratorTest {
	FilteredTermLengthDecorator<KeyTerm, TestEdge> testSubject;
	Graph<KeyTerm,TestEdge> graph;
	HashMap<KeyTerm,String> nodeContent;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		graph = new UndirectedSparseGraph<>();
		PreprocessComponent<KeyTerm,TestEdge> mockComp = createMock(PreprocessComponent.class);
		expect(mockComp.getVertexFactory()).andReturn(new Factory<KeyTerm>(){

			@Override
			public KeyTerm create() {
				return null;
			}
			
		});
		expect(mockComp.getEdgeFactory()).andReturn(new Factory<TestEdge>(){

			@Override
			public TestEdge create() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		expect(mockComp.execute(null)).andReturn(graph);
		replay(mockComp);
		this.nodeContent = new HashMap<>();
		
		testSubject = new FilteredTermLengthDecorator<>(mockComp, nodeContent,3);
		
		
	}

	@Test
	public void test() {
		KeyTerm term1 = new KeyTerm();
		KeyTerm term2 = new KeyTerm();
		
		this.nodeContent.put(term1, "AA");
		this.nodeContent.put(term2, "App");
		graph.addVertex(term1);
		graph.addVertex(term2);
		
		testSubject.execute(null);
		
		assertEquals("Should only one node left",1,graph.getVertexCount());
		assertTrue(graph.containsVertex(term2));
	}

}
