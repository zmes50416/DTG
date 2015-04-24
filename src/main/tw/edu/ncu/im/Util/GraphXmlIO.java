package tw.edu.ncu.im.Util;

import java.io.IOException;
import java.nio.file.Path;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;

public class GraphXmlIO<V,E> extends GraphIO<V,E> {

	public GraphXmlIO(String _path) {
		super(_path);
	}



	@Override
	public void store(Graph<V, E> component) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(Graph<V, E> component) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
