package tw.edu.ncu.im.Preprocess;

import java.io.File;

import org.apache.commons.collections15.Factory;

import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;
/**
 * 實踐此者須實際將文件轉換成節點，可多個相同字詞
 * @author TingWen
 *
 * @param <V> 
 * @param <E>
 */
public abstract class PreprocessComponent<V,E> {

	protected Graph<V,E> documentGraph;
	protected Factory<V> vertexFactory;
	protected Factory<E> edgeFactory;
	
	public Factory<V> getVertexFactory() {
		return vertexFactory;
	}
	public Factory<E> getEdgeFactory() {
		return edgeFactory;
	}
	public Graph<V, E> getDocumentGraph() {
		return documentGraph;
	}
	public void setDocumentGraph(Graph<V, E> documentGraph) {
		this.documentGraph = documentGraph;
	}
	public abstract Graph<V,E> execute(File doc);
}
