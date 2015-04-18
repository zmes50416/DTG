package tw.edu.ncu.im.Preprocess.Decorator;

import java.util.HashMap;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.*;

public abstract class PreprocessDecorator<V,E> extends PreprocessComponent<V, E>{
	public PreprocessDecorator(PreprocessComponent<V,E> _component){
		this.originComponent = _component;
		if((this.vertexFactory = this.originComponent.getVertexFactory())==null || (this.edgeFactory = this.originComponent.getEdgeFactory())==null){
			throw new IllegalArgumentException("there are no factory, maybe you forget initalized factory on the origin Component");
		}
		
	}
	public PreprocessComponent<V,E> originComponent;
}
