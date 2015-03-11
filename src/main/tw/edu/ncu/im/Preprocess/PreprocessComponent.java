package tw.edu.ncu.im.Preprocess;

import edu.uci.ics.jung.graph.Graph;

public abstract class PreprocessComponent<V,E> {
	IODelegater writer;
	public abstract Graph<V,E> execute();
}
