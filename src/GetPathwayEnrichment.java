import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class GetPathwayEnrichment {
	
	HashMap<String, ArrayList<String>> pathway_gene_dict = null;
	
	public GetPathwayEnrichment(){
		pathway_gene_dict = new HashMap<String, ArrayList<String>>();
	}
	
	public void getEachPathwayGenes(){
		Connection conn = DatabaseConnection.connect();
		String query = "SELECT * FROM pathwayEnrichment";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = (PreparedStatement)conn.prepareStatement(query);
			rs = ps.executeQuery();
			ArrayList<String> genes = null;
			
			// may need to check again;
			while(rs.next()){
				String pathway = rs.getString("pathway").trim();
				if(!pathway_gene_dict.containsKey(pathway)){
					genes = new ArrayList<String>();
					String gene = rs.getString("gene").trim();
					genes.add(gene);
					pathway_gene_dict.put(pathway, genes);
				}
				else{
					genes.add(rs.getString("gene").trim());
				}
			
			}//
		}catch(SQLException e){
			System.out.println("SQLException");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public HashMap<String, ArrayList<String>> getPathway_gene_dict(){
		return this.pathway_gene_dict;
	}
}
