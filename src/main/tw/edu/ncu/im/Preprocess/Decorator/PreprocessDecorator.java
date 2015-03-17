package tw.edu.ncu.im.Preprocess.Decorator;

import tw.edu.ncu.im.Preprocess.PreprocessComponent;
import tw.edu.ncu.im.Preprocess.graph.*;

public abstract class PreprocessDecorator<V extends Node<?>,E extends Edge> extends PreprocessComponent<V, E>{
	public PreprocessComponent<V,E> originComponent;
}
