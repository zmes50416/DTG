package tw.edu.ncu.im.Util;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import org.apache.commons.collections15.Transformer;

public class WeightedBetweennessClusterTest {

	private UndirectedSparseGraph<KeyTerm, TestEdge> graph;

	@Before
	public void setUp() throws Exception {
		graph = new UndirectedSparseGraph<>();

	}

	@After
	public void tearDown() throws Exception {
	}

	//Simple cluster test
	@Test
	public void testTransformGraphOfVE() {
		KeyTerm k1 = new KeyTerm("k1");
		KeyTerm k2 = new KeyTerm("k2");
		KeyTerm k3 = new KeyTerm("k3");
		KeyTerm k4 = new KeyTerm("k4");
		TestEdge e = new TestEdge(1);
		TestEdge e1 = new TestEdge(1);
		TestEdge e2 = new TestEdge(1);
		graph.addEdge(e, k1, k2);
		graph.addEdge(e1, k2,k3);
		graph.addEdge(e2,k3,k4);
		WeightedBetweennessCluster<KeyTerm,TestEdge> bc = new WeightedBetweennessCluster<>(1,new Transformer<TestEdge,Double>(){

			@Override
			public Double transform(TestEdge input) {
				return input.getDistance();
			}
			
		});
		
		Set<Set<KeyTerm>> clusters = bc.transform(graph);
		assertEquals("should cluster correctly",clusters.size(),2);
	}
	//Caution: This is not a test! it just doing for understanding the way how BetweennessCentrality work in algo.
	@Test
	public void testBetweenessWeightFunction(){
		KeyTerm k1 = new KeyTerm();
		KeyTerm k2 = new KeyTerm();
		KeyTerm k3 = new KeyTerm();
		KeyTerm k4 = new KeyTerm();
		TestEdge e = new TestEdge(1);
		TestEdge e1 = new TestEdge(1);
		TestEdge e2 = new TestEdge(1);
		graph.addEdge(e, k1, k2);
		graph.addEdge(e1, k2,k3);
		graph.addEdge(e2,k3,k4);
		BetweennessCentrality<KeyTerm,TestEdge> bc = new BetweennessCentrality<>(graph,new Transformer<TestEdge,Double>(){

			@Override
			public Double transform(TestEdge input) {
				return input.getDistance();
			}
			
		});
		
		System.out.println(bc.getEdgeScore(e));
		System.out.println(bc.getEdgeScore(e1));
		System.out.println(bc.getEdgeScore(e2));
		
	}

}
