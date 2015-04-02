package tw.edu.ncu.im.Preprocess;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.edu.ncu.im.Preprocess.graph.*;

import edu.uci.ics.jung.graph.Graph;

public class RouterNewsPreprocessorTest {
	
	RouterNewsPreprocessor component ;
	File origin;
	Path tempFile;
	Path txtResource = Paths.get(System.getProperty("user.dir"),"/src/test/java/resources/txt/");
	@Before
	public void setUp() throws Exception {
		//origin = txtResource.resolve("acq_0000005.txt").toFile();
		tempFile = Files.createTempDirectory("routerTest_").resolve("test.txt");
		origin = tempFile.toFile();
		try(BufferedWriter b = new BufferedWriter(new FileWriter(origin))){
			b.append("<There> is an apple.");
		}catch(Exception e){
			e.printStackTrace();
		}
		component = new RouterNewsPreprocessor();
	}

	@After
	public void tearDown() throws Exception {
		Files.delete(tempFile);
	}

	@Test
	public void testExecute() {
		Graph<KeyTerm, GoogleDistance> g = this.component.execute(origin);
		
		assertNotNull("component should return something",g);
		assertEquals(g.getVertexCount(), 4);
	}

}
