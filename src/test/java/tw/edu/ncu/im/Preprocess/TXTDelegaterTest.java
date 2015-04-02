package tw.edu.ncu.im.Preprocess;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import tw.edu.ncu.im.Preprocess.graph.*;

public class TXTDelegaterTest {
	TXTDelegater d;
	static Path testDir;
	Path testPath;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		testDir = Files.createTempDirectory("testTXTDelegater");
	}
	@Before
	public void setUp() throws Exception {
		this.testPath = testDir.resolve("test");
		d = new TXTDelegater(testPath.toString());
	}

	@After
	public void tearDown() throws Exception {
		Files.delete(testPath);
	}
	@AfterClass
	public static void over() throws Exception{
		Files.delete(testDir);
	}

	
	@Test
	public void testStore() {
		PreprocessComponent<Node<?>,Edge> c = new MockComponent();
		try {
			d.store(c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fail("Not yet implemented");
		
	}

	@Test
	public void testLoad() {
		fail("Not yet implemented");
	}

	@Test
	public void testTXTDelegater() {
		fail("Not yet implemented");
	}

}
