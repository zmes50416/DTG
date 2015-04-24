package tw.edu.ncu.im.Util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.*;

public abstract class GraphIO<V,E> {
	Path filePath;
	
	public GraphIO(String _filePath) {
		this.filePath = Paths.get(_filePath);
	}
	public abstract void store(Graph<V,E> component) throws IOException;
	public abstract void load(Graph<V,E> component) throws IOException;

}
