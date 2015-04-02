package tw.edu.ncu.im.Preprocess;

import java.io.File;

import tw.edu.ncu.im.Preprocess.graph.Edge;
import tw.edu.ncu.im.Preprocess.graph.GoogleDistance;
import tw.edu.ncu.im.Preprocess.graph.KeyTerm;
import tw.edu.ncu.im.Preprocess.graph.Node;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class MockComponent extends PreprocessComponent<Node<?>,Edge> {
		public MockComponent(){
			this.documentGraph = new UndirectedSparseGraph<Node<?>,Edge>();
			KeyTerm t = new KeyTerm("Steve");
			KeyTerm t2 = new KeyTerm("Jump");
			KeyTerm t3 = new KeyTerm("Beautifully");
			KeyTerm t4 = new KeyTerm("Beautifully");

			GoogleDistance e = new GoogleDistance(1);
			this.documentGraph.addVertex(t);
			this.documentGraph.addVertex(t2);
			this.documentGraph.addVertex(t3);
			this.documentGraph.addVertex(t4);
			this.documentGraph.addEdge(e, t, t2);
		}
		@Override
		public Graph<Node<?>, Edge> execute(File doc) {
			return this.documentGraph;
		}
		
}
