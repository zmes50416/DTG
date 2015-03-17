package tw.edu.ncu.im.Preprocess;

import java.io.File;
import tw.edu.ncu.im.Preprocess.graph.*;
import edu.uci.ics.jung.graph.Graph;
/**
 * 實踐此者須實際將文件轉換成節點，可多個相同字詞
 * @author TingWen
 *
 * @param <V> 
 * @param <E>
 */
public abstract class PreprocessComponent<V extends Node<?>,E extends Edge> {
	private IODelegater docuemtIO;
	public IODelegater getDocuemtIO() {
		return docuemtIO;
	}
	public void setDocuemtIO(IODelegater docuemtIO) {
		this.docuemtIO = docuemtIO;
	}
	public Graph<V, E> getDocumentGraph() {
		return documentGraph;
	}
	public void setDocumentGraph(Graph<V, E> documentGraph) {
		this.documentGraph = documentGraph;
	}
	protected Graph<V,E> documentGraph;
	public abstract Graph<V,E> execute(File doc);
}
