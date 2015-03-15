package tw.edu.ncu.im.Preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RouterNewsPreprocessor extends PreprocessComponent<KeyTerm,GoogleDistance> {
	HashSet<KeyTerm> terms;
	HashSet<GoogleDistance> edges;
	
	@Override
	public Graph<KeyTerm,GoogleDistance> execute(File doc) {
		if(!doc.isFile()){
			//TODO 判斷是否為可讀寫且為txt 否則throw Exception
		}
		try(BufferedReader r = new BufferedReader(new FileReader(doc));){
			this.docGraph = new UndirectedSparseGraph<KeyTerm,GoogleDistance>();
			for(String line=r.readLine();line!=null;line = r.readLine()){
				String line2=line.toString();
				line=line.replace("]", "");
				line=line.replace("[", "");
				line=line.replace("<", "");
				line=line.replace(">", "");

		        Matcher m = Pattern.compile("[(),.\"\\?!:;]").matcher(line);

		        line = m.replaceAll("");
		        line2 = m.replaceAll(",");
				List<String> tokens = this.tokenize(line);
				for(String token:tokens){
					KeyTerm k = new KeyTerm(token);
					this.docGraph.addVertex(k);
				}
				
				
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return this.docGraph;
		
	}
	private List<String> tokenize(String line){
		StringTokenizer st = new StringTokenizer(line);
		ArrayList<String> result = new ArrayList<>();
		while(st.hasMoreTokens()){
			result.add(st.nextToken());
		}

		return result;
	}
	
	
}
class GoogleDistance implements Edge{

	double distance;
	
	public GoogleDistance(double dist){
		this.distance = dist;
	}
	@Override
	public double getDistance() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}