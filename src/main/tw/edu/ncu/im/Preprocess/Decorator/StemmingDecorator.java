package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Util.Stemmer;
import edu.uci.ics.jung.graph.Graph;

public class StemmingDecorator<V,E> extends PreprocessDecorator<V, E> {
	HashMap<V,String> vertexContent;
	PorterStemmer stemmer = new PorterStemmer();
	public StemmingDecorator(PreprocessComponent<V, E> _component,HashMap<V,String> content) {
		super(_component);
		this.vertexContent = content;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Graph<V, E> execute(File doc) {
		Graph<V, E> originGraph = this.originComponent.execute(doc);
		for(V node:originGraph.getVertices()){
			String content = this.vertexContent.get(node);
			String stemmedContent = this.stemmer.stemming(content);
			this.vertexContent.put(node, stemmedContent);
		}
		return originGraph;
	}
	private class PorterStemmer {

		public String stemming(String s) {
			Scanner scan = new Scanner(s);
			StringBuffer sb = new StringBuffer();
			Stemmer stemmer = new Stemmer();
			while (scan.hasNext()) {
				String s2 = scan.next();
				char[] ary = s2.toCharArray();
				int count = 0;
				for (char c : ary) {
					if (Character.isLetter(c))
						count++;
				}
				stemmer.add(ary, count);
				stemmer.stem();
				sb.append(stemmer.toString());
				sb.append(s2.substring(count));
			}
			scan.close();
			return sb.toString();
			
		}  // end of class stemming
	}  // end of class PorterStemmer
}


