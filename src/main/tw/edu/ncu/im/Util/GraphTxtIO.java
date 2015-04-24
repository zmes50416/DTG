package tw.edu.ncu.im.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
import tw.edu.ncu.im.Preprocess.PreprocessComponent;

public class GraphTxtIO<V,E> extends GraphIO<V,E> {
	
	File txtFile;
	
	public GraphTxtIO(String _filePath) throws IOException {
		super(_filePath);
		txtFile = new File(this.filePath.toString()+".txt");
//		if(!txtFile.canWrite()&&!txtFile.canRead()){
//			throw new IOException();
//			
//		}
	}

	@Override
	public void store(Graph<V, E> component) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile));
			Graph<V,E> g = component;
			Collection<E> edges = g.getEdges();
			Collection<V> nodes = g.getVertices();
			writer.write("Edge List::"+System.getProperty("line.separator"));
			if(edges.size()!=0){
				for(E e:edges){
					writer.write(e.toString()+System.getProperty("line.separator"));
				}
			}else{
				writer.append("Edge N/A"+System.getProperty("line.separator"));
			}
			writer.write("Node List::"+System.getProperty("line.separator"));
			if(nodes.size()!= 0){
				for(V v:nodes){
					writer.write(v.toString()+System.getProperty("line.separator"));
				}
			}
		writer.close();
	}

	@Override
	public void load(Graph<V, E> component) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
