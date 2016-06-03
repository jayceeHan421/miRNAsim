
public class Node {
	private String name = null;
	private int group = 0;
	
	public Node(String name, int group){
		this.name = name;
		this.group = group;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getGroup(){
		return this.group;
	}
}
