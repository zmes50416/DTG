package tw.edu.ncu.im.Preprocess.Decorator;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.*;
import static org.easymock.EasyMock.*;
public class PartOfSpeechFilterTest{

	PreprocessComponent<Term, TestEdge> mockGraph;
	Graph<Term, TestEdge> graph = new UndirectedSparseGraph<>();
	HashMap<Term,String> content = new HashMap<>();
	PartOfSpeechFilter<Term, TestEdge> p;
	@Before
	public void setUp() throws Exception {
		 mockGraph = createMock(PreprocessComponent.class);
		 expect(mockGraph.execute(notNull(File.class))).andReturn(graph);
		 expect(mockGraph.getVertexFactory()).andReturn(new Factory<Term>(){

			@Override
			public Term create() {
				return new Term();
			}
			 
		 }).anyTimes();
		 expect(mockGraph.getEdgeFactory()).andReturn(new Factory<TestEdge>(){

			@Override
			public TestEdge create() {
				return null;
			}
			 
		 }).anyTimes();
		replay(mockGraph);
	}

	@Test
	public void testExecute() {
		String text1 = "Peter have a Beautiful bear";
		Term key1 = new Term();
		graph.addVertex(key1);
		this.content.put(key1, text1);
		String text2 = "COMPUTER TERMINAL SYSTEMS";
		Term key2 = new Term();
		graph.addVertex(key2);
		content.put(key2, text2);
		
		p = new PartOfSpeechFilter<>(mockGraph, content);
		p.execute(createMock(File.class));
		
		assertSame("should have 5 words",5,p.vertexResultsTerms.size());
	}
	private class Term {

		
	}
}
