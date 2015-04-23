package tw.edu.ncu.im.Preprocess.Decorator;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.algorithms.cluster.*;
/**
 * NGD相似度容差過濾模組，將高於容差門檻值的字詞合併
 * @author TingWen
 *
 * @param <V>
 * @param <E>
 */
public class NgdToleranceFiliter<V,E> extends PreprocessDecorator<V, E> {
	double toleranceThreshold;
	HashMap<E,Double> edgeDistanceMap;
	HashMap<V,Double> termWeightMap;
	
	public NgdToleranceFiliter(PreprocessComponent<V, E> _component,double _tolThreshold, HashMap<E, Double> _edgeDistance,HashMap<V, Double> _termWeightMap) {
		super(_component);
		this.toleranceThreshold = _tolThreshold;
		this.edgeDistanceMap = _edgeDistance;
		this.termWeightMap = _termWeightMap;
	}

	@Override
	public Graph<V, E> execute(File doc) {
		this.documentGraph = this.originComponent.execute(doc);
		Set<E> toleranceEdges = new HashSet<E>();
		Set<E> removeEdges = new HashSet<E>();
		for(Iterator<E> iterator = this.documentGraph.getEdges().iterator();iterator.hasNext();){
			E edge = iterator.next();
			Double ngdScore = this.edgeDistanceMap.get(edge);
			if(ngdScore <= this.toleranceThreshold){
				this.edgeDistanceMap.remove(edge);
				toleranceEdges.add(edge);
			}
		}
		
		for(E edge:toleranceEdges){
			Pair<V> pair = this.documentGraph.getEndpoints(edge);
			double first = this.termWeightMap.get(pair.getFirst());
			double second = this.termWeightMap.get(pair.getSecond());
			V strongNode, weakNode;
			if(first>=second){
				strongNode = pair.getFirst();
				weakNode = pair.getSecond();
			}else{
				strongNode = pair.getSecond();
				weakNode = pair.getFirst();
			}
			for(V node:this.documentGraph.getNeighbors(weakNode)){
				E neighborEdge = this.documentGraph.findEdge(weakNode, node);
				Pair<V> nPair = this.documentGraph.getEndpoints(neighborEdge);
				Double neighborValue = this.edgeDistanceMap.get(neighborEdge);

				if(node.equals(strongNode)){
					removeEdges.add(edge);
				}else if(this.documentGraph.isNeighbor(node, strongNode)){//三者連通的情況 
					E anotherEdge = this.documentGraph.findEdge(strongNode, node);
					if(edgeDistanceMap.get(anotherEdge)>=edgeDistanceMap.get(neighborEdge)){
						this.edgeDistanceMap.put(anotherEdge, neighborValue);
					}
					removeEdges.add(neighborEdge);
				}else{
					
					Pair<V> anotherPair = new Pair<V>(strongNode,node);
					E newEdge = this.edgeFactory.create();
					this.documentGraph.addEdge(newEdge, anotherPair);
					removeEdges.add(neighborEdge);//Old one should be deleted
					this.edgeDistanceMap.put(newEdge, neighborValue);
				}//end of if else
				
			}
			this.documentGraph.removeVertex(weakNode);
			this.termWeightMap.remove(weakNode);
			
		}//for loop of edgeToRemove
		
		for(E edge:removeEdges){
			this.documentGraph.removeEdge(edge);
			this.edgeDistanceMap.remove(edge);
		}
		
		
		return this.documentGraph;
	}
	


}
