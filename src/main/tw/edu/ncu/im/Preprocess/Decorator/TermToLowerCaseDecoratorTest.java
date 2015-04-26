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

public class TermToLowerCaseDecoratorTest{
	TermToLowerCaseDecorator<KeyTerm, TestEdge> testSubject;
	Graph<KeyTerm, TestEdge> graph;
	HashMap<KeyTerm, String> nodeContent;
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
		testSubject = new TermToLowerCaseDecorator<>(mockComp, nodeContent);
	}

	@Test
	public void test() {
		KeyTerm term1 = new KeyTerm();
		//KeyTerm term2 = new KeyTerm();
		
		this.nodeContent.put(term1, "Apple");
		//this.nodeContent.put(term2, "Bpple");
		graph.addVertex(term1);
		//graph.addVertex(term2);
		testSubject.execute(null);
		
		assertEquals("Should only one node left","apple",this.nodeContent.get(term1));
	}

}
