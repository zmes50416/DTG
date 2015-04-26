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

public class TermFreqDecoratorTest {
	TermFreqDecorator<KeyTerm, TestEdge> testSubject;
	Graph<KeyTerm, TestEdge> graph;
	HashMap<KeyTerm, String> nodeContent;
	HashMap<KeyTerm, Double> nodeContentValues;
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
		this.nodeContentValues = new HashMap<>();
		testSubject = new TermFreqDecorator<>(mockComp, nodeContent,nodeContentValues);
	}

	@Test
	public void test() {
		KeyTerm term1 = new KeyTerm();
		KeyTerm term2 = new KeyTerm();
		KeyTerm term3 = new KeyTerm();
		KeyTerm term4 = new KeyTerm();
		KeyTerm term5 = new KeyTerm();
		this.nodeContent.put(term1, "Apple");
		this.nodeContent.put(term2, "Bpple");
		this.nodeContent.put(term3, "Cpple");
		this.nodeContent.put(term4, "Cpple");
		this.nodeContent.put(term5, "Apple");
		this.nodeContentValues.put(term1, 0.0);
		this.nodeContentValues.put(term2, 0.0);
		//this.nodeContentValues.put(term3, 1.0);
		//this.nodeContentValues.put(term4, 0.0);
		graph.addVertex(term1);
		graph.addVertex(term2);
		graph.addVertex(term3);
		graph.addVertex(term4);
		graph.addVertex(term5);
		testSubject.execute(null);
		
		assertEquals("Should only one node left",3,graph.getVertexCount());
		
	}

}
