package tw.edu.ncu.im.Preprocess;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.collections15.Factory;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;

public class RouterNewsPreprocessorTest {
	
	RouterNewsPreprocessor<KeyTerm,TestEdge> component ;
	File origin;
	Path tempFile;
	@Before
	public void setUp() throws Exception {
		tempFile = Files.createTempDirectory("routerTest_").resolve("test.txt");
		origin = tempFile.toFile();
		try(BufferedWriter b = new BufferedWriter(new FileWriter(origin))){
			b.append("news title");
			b.newLine();
			b.append("There is an <apple>.");
			b.append("National Central University!");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		component = new RouterNewsPreprocessor<>(new Factory<KeyTerm>(){

			@Override
			public KeyTerm create() {
				return new KeyTerm();
			}
			
		},new Factory<TestEdge>(){

			@Override
			public TestEdge create() {
				return new TestEdge(0);
			}
			
		});
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.deleteDirectory(tempFile.getParent().toFile());
	}

	@Test
	public void testExecute() {
		Graph<KeyTerm, TestEdge> g = this.component.execute(origin);
		
		assertNotNull("component should return something",g);
		assertEquals("should have 2 line term", 2,g.getVertexCount());
	}

}
