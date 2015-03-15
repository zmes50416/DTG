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
	IODelegater writer;
	Graph<V,E> docGraph;
	public abstract Graph<V,E> execute(File doc);
}
