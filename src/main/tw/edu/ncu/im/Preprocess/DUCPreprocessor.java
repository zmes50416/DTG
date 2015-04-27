package tw.edu.ncu.im.Preprocess;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections15.Factory;

import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public abstract class DUCPreprocessor<V,E> extends PreprocessComponent<V, E> {
	HashMap<V,String> vertexContent = new HashMap<>();
	public DUCPreprocessor(Factory<V> _vertexFactory, Factory<E> _edgeFactory){
		this.vertexFactory = _vertexFactory;
		this.edgeFactory = _edgeFactory;
	}
	
	public Graph<V,E> execute(File doc){
		Graph<V,E> documentGraph = new UndirectedSparseGraph<V,E>();
		if(!doc.isFile()){
			//TODO 判斷是否為可讀寫且為txt 否則throw Exception 
			//還沒寫
		}
		try(BufferedReader r = new BufferedReader(new FileReader(doc));){
			for(String line = r.readLine();line!=null;line = r.readLine()){
				
				line = line.replace("]", "");
				line = line.replace("[", "");
				line = line.replace("<", "");
				line = line.replace(">", "");
				line = line.replaceAll(("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]"), "");
				
				/*Matcher m = Pattern.compile("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]").matcher(line);
				line = m.replaceAll("");*/
				String[] tokens = line.split(",");
				 for(String token:tokens) {
					V node = this.vertexFactory.create();
					this.vertexContent.put(node, token);
					documentGraph.addVertex(node);
				 }
				
				
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return documentGraph;
	}
	
}
