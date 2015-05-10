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
	//Caution: This is not a test of WeightedBetweennessCluster! it just doing for understanding the way how BetweennessCentrality work in algo.
	//This simulate the case in : http://med.bioinf.mpi-inf.mpg.de/netanalyzer/help/2.6.1/#nodeBetween
	@Test
	public void testBetweenessWeightFunction(){
		KeyTerm kA = new KeyTerm();
		KeyTerm kB = new KeyTerm();
		KeyTerm kC = new KeyTerm();
		KeyTerm kD = new KeyTerm();
		KeyTerm kE = new KeyTerm();
		TestEdge e = new TestEdge(1);
		TestEdge e1 = new TestEdge(1);
		TestEdge e2 = new TestEdge(1);
		TestEdge e3 = new TestEdge(1);
		TestEdge e4 = new TestEdge(1);
		
		graph.addEdge(e, kA, kB);
		graph.addEdge(e1, kB,kC);
		graph.addEdge(e2,kB,kD);
		graph.addEdge(e3, kC,kE);
		graph.addEdge(e4, kD,kE);
		BetweennessCentrality<KeyTerm,TestEdge> bc = new BetweennessCentrality<>(graph,new Transformer<TestEdge,Double>(){

			@Override
			public Double transform(TestEdge input) {
				return input.getDistance();
			}
			
		});
		
		assertEquals("the node B should expect ",3.5,bc.getVertexScore(kB),0.01);
		
	}

}
