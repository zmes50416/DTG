package tw.edu.ncu.im.Preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections15.Factory;

import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RouterNewsPreprocessor<V,E> extends PreprocessComponent<V,E> {
	HashSet<KeyTerm> terms;
	HashSet<GoogleDistance> edges;
	HashMap<V,String> vertexContent = new HashMap<>();
	public RouterNewsPreprocessor(Factory<V> _vertexFactory){
		this.vertexFactory = _vertexFactory;
	}
	@Override
	public Graph<V,E> execute(File doc) {
		if(!doc.isFile()){
			//TODO 判斷是否為可讀寫且為txt 否則throw Exception
		}
		try(BufferedReader r = new BufferedReader(new FileReader(doc));){
			this.documentGraph = new UndirectedSparseGraph<V,E>();
			for(String line=r.readLine();line!=null;line = r.readLine()){
				line=line.replace("]", "");
				line=line.replace("[", "");
				line=line.replace("<", "");
				line=line.replace(">", "");

		        Matcher m = Pattern.compile("[(),.\"\\?!:;]").matcher(line);
		        line = m.replaceAll("");
				V node = this.vertexFactory.create();
				this.vertexContent.put(node, line);
				this.documentGraph.addVertex(node);

		        
				//List<String> tokens = this.tokenize(line);
				
				//for(String token:tokens){
				//	KeyTerm k = new KeyTerm(token);
					
				//}
				
				
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return this.documentGraph;
		
	}
	
	
	
}