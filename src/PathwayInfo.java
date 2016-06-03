
public class PathwayInfo {
	private String pathwayName = "";
	private double pvalue = -1;
	private double occurrence = -1;
	public PathwayInfo(String pathwayName, double pvalue, double occurrence){
		this.pathwayName = pathwayName;
		this.pvalue = pvalue;
		this.occurrence = occurrence;	
	}
	
	public String getPathwayName(){
		return this.pathwayName;
	}
	public double getPvalue(){
		return this.pvalue;
	}
	public double getOccurrence(){
		return this.occurrence;
	}
}
