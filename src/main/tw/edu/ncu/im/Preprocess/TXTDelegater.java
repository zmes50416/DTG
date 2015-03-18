package tw.edu.ncu.im.Preprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.graph.Edge;
import tw.edu.ncu.im.Preprocess.graph.Node;

public class TXTDelegater extends IODelegater {
	
	File txtFile;
	
	public TXTDelegater(String _filePath) throws IOException {
		super(_filePath);
		txtFile = new File(this.filePath.toString()+".txt");
//		if(!txtFile.canWrite()&&!txtFile.canRead()){
//			throw new IOException();
//			
//		}
	}

	@Override
	public void store(PreprocessComponent<Node<?>, Edge> component) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile));
			Graph<Node<?>,Edge> g = component.getDocumentGraph();
			Collection<Edge> edges = g.getEdges();
			Collection<Node<?>> nodes = g.getVertices();
			writer.write("Edge List::"+System.getProperty("line.separator"));
			if(edges.size()!=0){
				for(Edge e:edges){
					writer.write(e.toString()+System.getProperty("line.separator"));
				}
			}else{
				writer.append("Edge N/A"+System.getProperty("line.separator"));
			}
			writer.write("Node List::"+System.getProperty("line.separator"));
			if(nodes.size()!= 0){
				for(Node<?> e:nodes){
					writer.write(e.toString()+System.getProperty("line.separator"));
				}
			}
		writer.close();
	}

	@Override
	public void load(PreprocessComponent<Node<?>, Edge> component) {
		// TODO Auto-generated method stub
		
	}

}
