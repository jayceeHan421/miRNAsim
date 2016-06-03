
public class Link {
	
	private int source = 1;
	private int target = -1;
	private double value = -1;
	
	public Link(int source, int target, double value){
		this.source = source;
		this.target = target;
		this.value = value;
	}
	
	public int getSource(){
		return this.source;
	}
	
	public int getTarget(){
		return this.target;
	}
	
	public double getValue(){
		return this.value;
	}

}
