package tw.edu.ncu.im.Preprocess.graph;

public class TestEdge{

	double distance;
	
	public TestEdge(){
		distance = 1;
	}
	public TestEdge(double dist){
		this.distance = dist;
	}
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
