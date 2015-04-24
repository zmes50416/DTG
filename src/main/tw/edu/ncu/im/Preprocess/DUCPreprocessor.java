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
		if(!doc.isFile()){
			//TODO 判斷是否為可讀寫且為txt 否則throw Exception 
			//還沒寫
		}
		try(BufferedReader r = new BufferedReader(new FileReader(doc));){
			this.documentGraph = new UndirectedSparseGraph<V,E>();
			for(String line = r.readLine();line!=null;line = r.readLine()){
				
				line = line.replace("]", "");
				line = line.replace("[", "");
				line = line.replace("<", "");
				line = line.replace(">", "");
				line = line.replaceAll(("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]"), "");
				
				/*Matcher m = Pattern.compile("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]").matcher(line);
				line = m.replaceAll("");*/
				
				V node = this.vertexFactory.create();
				this.vertexContent.put(node, line);
				this.documentGraph.addVertex(node);
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return this.documentGraph;
	}
	
		
		// 打開要做詞性標記的目錄 (開始)
		/*File[] DirectoryList = io.openDirectory(folderRoot + folderIn);
		for (File Directory : DirectoryList) {
			File[] fileList = io.openDirectory(Directory.getPath());
			List<String> inputText = new ArrayList<String>(); // 存放input
			for (File file : fileList) {
				System.out.println("處理檔案: " + file.getName());
				List<String> inputTextTemp = new ArrayList<String>(); // 存放input
				inputTextTemp = io.openFile(file.getPath()); // 打開子目錄內的檔案
				inputText.addAll(inputTextTemp);
			} // end of for 子目錄內的檔案
			HashSet<String> filteredTerm = new HashSet<String>(); // 存放經過過濾後的候選term
			filteredTerm = POS_and_Length_Filter(inputText);
			List<String> outputText = new ArrayList<String>(); // 存放output
			for (String term : filteredTerm) {
				outputText.add(term);
				System.out.println(term);
			} // end of for 將term放入outputText準備輸出
			io.saveFile(folderRoot + folderOut
					+ Directory.getName().toUpperCase() + ".txt", outputText);
		} // end of for 目錄內的子目錄*/
		
	

}
