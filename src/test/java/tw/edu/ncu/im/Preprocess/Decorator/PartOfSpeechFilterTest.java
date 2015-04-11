package tw.edu.ncu.im.Preprocess.Decorator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tw.edu.ncu.im.Preprocess.MockComponent;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.Edge;
import tw.edu.ncu.im.Preprocess.graph.GoogleDistance;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.Node;

public class PartOfSpeechFilterTest{

	MockComponent mockGraph;
	@Before
	public void setUp() throws Exception {
		 mockGraph = new MockComponent();
		
	}

	@Test
	public void test() {
		PartOfSpeechFilter<Node<?>, Edge> p = new PartOfSpeechFilter<Node<?>, Edge>(mockGraph);
		p.execute(null);
		p.getDocumentGraph();
	}

}
