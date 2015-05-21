package tw.edu.ncu.im.Preprocess;

import static org.junit.Assert.*;

import org.apache.commons.collections15.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.TestEdge;

public class BBCNewsPreprocessorTest {
	BBCNewsPreprocessor<KeyTerm,TestEdge> testSubject;
	@Before
	public void setUp() throws Exception {
		testSubject = new BBCNewsPreprocessor<KeyTerm,TestEdge>(new Factory<KeyTerm>(){

			@Override
			public KeyTerm create() {
				return new KeyTerm();
			}
			
		}, new Factory<TestEdge>(){

			@Override
			public TestEdge create() {
				return new TestEdge();
			}
			
		});
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
	}

	@Test
	public void testGetStringOfVertex() {
		fail("Not yet implemented");
	}

}
