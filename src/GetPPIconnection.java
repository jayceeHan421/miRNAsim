import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.math3.util.CombinatoricsUtils;


public class GetPPIconnection {
	
	HashMap<String, Double> genePairs = null;
	private ArrayList<String> nodeNames = null;
	private ArrayList<String> validatedGeneNames = null;
	
	public GetPPIconnection(ArrayList<String> nodeNames){
		genePairs = new HashMap<String, Double>();
		this.nodeNames = nodeNames;
		this.validatedGeneNames = new ArrayList<String>();
	}
	
	public void getGenePairs(){
		Connection conn = DatabaseConnection.connect();
		String query = "SELECT * FROM GENEsim_RSS0502 WHERE(gene1 = ? AND trim(gene2) = ?) OR (gene1 = ? AND trim(gene2) = ?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Iterator<int[]> combinationsIterator = CombinatoricsUtils.combinationsIterator(nodeNames.size(),2);
		
		while(combinationsIterator.hasNext()){
			int[] genePair = combinationsIterator.next();
			
			try{
				ps = conn.prepareStatement(query);
				ps.setString(1, nodeNames.get(genePair[0]));
				ps.setString(2, nodeNames.get(genePair[1]));
				ps.setString(3, nodeNames.get(genePair[1]));
				ps.setString(4, nodeNames.get(genePair[0]));
				
				rs = ps.executeQuery();
				if(rs.next()){
					String gene1 = rs.getString("gene1").trim();
					String gene2 = rs.getString("gene2").trim();
					float value = rs.getFloat("value");
					genePairs.put(gene1 + ','+ gene2, new Double(value));
					
					boolean gene1HasIt = false;
					boolean gene2HasIt = false;
					
					for(String item:validatedGeneNames){
						if(gene1.equalsIgnoreCase(item)){
							gene1HasIt = true;
						}
						if(gene2.equalsIgnoreCase(item)){
							gene2HasIt = true;
						}					
					}
					
					if(!gene1HasIt){
						validatedGeneNames.add(gene1);
					}
					if(!gene2HasIt){
						validatedGeneNames.add(gene2);
					}					
				}
			}catch(SQLException e){
				System.out.println("SQLException");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public ArrayList<String> getValidatedGenes(){
		return this.validatedGeneNames;
	}
	
	public HashMap<String, Double> getHashGenePairs(){
		return this.genePairs;
	}
}
