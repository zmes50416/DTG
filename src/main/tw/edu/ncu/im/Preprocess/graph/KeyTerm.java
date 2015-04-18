package tw.edu.ncu.im.Preprocess.graph;

public class KeyTerm{
	String name;
	Double Freq;
	public KeyTerm() {
		this.Freq = 1.0;
	}

	

	public Double getValue() {
		return this.Freq;
	}
	
	@Override
	public String toString(){
		return name;
		
	}
}
