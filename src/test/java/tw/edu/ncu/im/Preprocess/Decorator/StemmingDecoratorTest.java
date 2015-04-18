package tw.edu.ncu.im.Preprocess.Decorator;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.commons.collections15.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import static org.easymock.EasyMock.*;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.GoogleDistance;

public class StemmingDecoratorTest {
	StemmingDecorator<String,GoogleDistance> testSubject;
	PreprocessComponent<String,GoogleDistance> comp;
	HashMap<String,String> content;
	private Graph<String,GoogleDistance> graph;
	@Before
	public void setUp() throws Exception {
		this.comp = createMock(PreprocessComponent.class);
		expect(comp.getVertexFactory()).andReturn(new Factory<String>(){

			@Override
			public String create() {
				return null;
			}
			
		}).anyTimes();
		expect(comp.getEdgeFactory()).andReturn(new Factory<GoogleDistance>(){

			@Override
			public GoogleDistance create() {
				return null;
			}
			
		}).anyTimes();
		graph = new UndirectedSparseGraph<String,GoogleDistance>();
		expect(comp.execute(null)).andReturn(graph);
		replay(this.comp);
		content = new HashMap<>();
		this.testSubject = new StemmingDecorator<>(this.comp,this.content);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		
		
		String text1 = "moving";
		graph.addVertex(text1);
		this.content.put(text1, text1);
		
		
		this.testSubject.execute(null);
		assertEquals("stemmed failed","move",this.testSubject.vertexContent.get(text1));
	}

}
