package tw.edu.ncu.im.Preprocess.graph;

public class KeyTerm implements Node<Integer>{
	String name;
	Integer Freq;
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
			if(otherTerm.name.endsWith(this.name)){
				return true;
			}else{
				return false;
			}
			
		}
		
	}

	@Override
	public Integer getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
