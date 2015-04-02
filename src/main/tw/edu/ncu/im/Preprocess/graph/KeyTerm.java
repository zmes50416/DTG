package tw.edu.ncu.im.Preprocess.graph;

public class KeyTerm implements Node<Double>{
	String name;
	Double Freq;
	public KeyTerm(String _name) {
		this.name = _name;
		this.Freq = 1;
	}

	@Override
	public boolean equals(Object o){
		if(o.getClass()!=KeyTerm.class){
			throw new ClassCastException();
		}else{
			KeyTerm otherTerm = (KeyTerm)o;
			if(otherTerm.name.equals(this.name)){
				return true;
			}else{
				return false;
			}
			
		}
		
	}

	@Override
	public Double getValue() {
		return this.Freq;
	}
	@Override
	public String toString(){
		return name;
		
	}
}
