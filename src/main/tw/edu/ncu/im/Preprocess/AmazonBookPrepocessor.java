package tw.edu.ncu.im.Preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections15.Factory;

import qtag.Tagger;
import tw.edu.ncu.im.Preprocess.graph.Edge;
import tw.edu.ncu.im.Preprocess.graph.GoogleDistance;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.Node;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class AmazonBookPrepocessor<V, E> extends
		PreprocessComponent<V,E> {
	HashSet<KeyTerm> terms;
	HashSet<GoogleDistance> edges;
	HashMap<V, String> vertexContent = new HashMap<>();

	public AmazonBookPrepocessor(Factory<V> _vertexFactory,
			Factory<E> _edgeFactory) {
		this.vertexFactory = _vertexFactory;
		this.edgeFactory = _edgeFactory;
	}

	@Override
	public Graph<V, E> execute(File doc) {
		// TODO Auto-generated method stub
		try (FileReader FileStream = new FileReader("doc");) {
			BufferedReader in = new BufferedReader(FileStream);
			this.documentGraph = new UndirectedSparseGraph<V, E>();
			String line = in.readLine();
			int tokenId = 1;
			// Process line by line
			while (line != null) {
				String line2 = line.toString();
				line = line.replace("]", "");
				line = line.replace("[", "");
				Pattern p = Pattern.compile("[(),.\"\\?!:;]");
				Matcher m = p.matcher(line);

				line = m.replaceAll("");
				line2 = m.replaceAll(",");
				String[] inputArg = line2.split(",");
				V node = this.vertexFactory.create();
				this.vertexContent.put(node, line2);
				this.documentGraph.addVertex(node);
				line = in.readLine();				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.documentGraph;
	}
}
