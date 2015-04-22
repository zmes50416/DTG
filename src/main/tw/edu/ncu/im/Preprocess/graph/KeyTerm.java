package tw.edu.ncu.im.Preprocess.graph;
/**
 * For testing only, to prove the graph can work in normal condition
 * @author TingWen
 *
 */
public class KeyTerm{
	String name;
	Double Freq;
	public KeyTerm() {
		this.Freq = 1.0;
	}
	public KeyTerm(String _name){
		this();
		this.name = _name;
	}
	public KeyTerm(String _name,double _freq){
		this.name = _name;
		this.Freq = _freq;
	}

	

	public Double getValue() {
		return this.Freq;
	}
	
	@Override
	public String toString(){
		return name;
		
	}
}
