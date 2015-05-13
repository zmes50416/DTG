package tw.edu.ncu.im.Util;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections15.Factory;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;
import tw.edu.ncu.im.Util.NgdEdgeSorter;

/**
 * 
 * @author chiang
 *
 */
public class NgdSorterTest {
	/**
	 * @param termContent
	 *            node 與term
	 * @param edgeDistance
	 *            edge 與 距離
	 * 
	 */

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() {
		HashMap<TestEdge, Double> edgeDistance = new HashMap<>();


		TestEdge edge1 = new TestEdge();
		TestEdge edge2 = new TestEdge();
		TestEdge edge3 = new TestEdge();
		edgeDistance.put(edge1, 0.5);
		edgeDistance.put(edge2, 0.6);
		edgeDistance.put(edge3, 0.4);

		List<Entry<?, Double>> sortedEdges = NgdEdgeSorter.sort(edgeDistance);
		assertEquals(3, sortedEdges.size());
		assertSame("smallest edge should be edge2",edge3, sortedEdges.get(0).getKey());
		assertSame("Biggest edge should be edge3",edge2, sortedEdges.get(2).getKey());
	}

}
