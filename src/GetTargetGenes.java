import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetTargetGenes {

	private String miRNA1;
	private String miRNA2;
	ArrayList<String> genes1 = null;
	ArrayList<String> genes2 = null;
	
	public GetTargetGenes(String miRNA1, String miRNA2){
		this.miRNA1 = miRNA1;
		this.miRNA2 = miRNA2;
		genes1 = new ArrayList<>();
		genes2 = new ArrayList<>();
	}
	
	
	public boolean[] checkMiRNAexist(String miRNA1, String miRNA2, String table){
		boolean miRNAexist1 = false;
		boolean miRNAexist2 = false;
		
		System.out.println("before here");
		Connection conn = DatabaseConnection.connect();
		System.out.println("after connection");
		String query = "SELECT * FROM " + table + " WHERE miRNA  = ?";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try{
			ps1 = conn.prepareStatement(query);
			ps1.setString(1,miRNA1);
			rs1 = ps1.executeQuery();
			ps2 = conn.prepareStatement(query);
			ps2.setString(1,miRNA2);
			rs2 = ps2.executeQuery();
			
			//System.out.println("go my check is here " + rs1.getInt("COUNT(1)"));
			if(rs1.next()){
				miRNAexist1 = true;
	
			}
			else {
				miRNAexist1 = false;
			}
			
			if(rs2.next()){
				miRNAexist2 = true;
			}
			else {
				miRNAexist2 = false;
			}
			
				
			
		}catch(SQLException e){
			miRNAexist1 = false;
			miRNAexist2 = false;
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		
		boolean[] checkExist = new boolean[2];
		checkExist[0] = miRNAexist1;
		checkExist[1] = miRNAexist2;
		
		
		miRNAexist1 = true;
		miRNAexist2 = true;
		
		DatabaseConnection.closeDatabase(ps1,rs1, ps2, rs2, conn);
		return checkExist;
	}
	
	public void SetBothGenesFromDatabase(String miRNA1, String miRNA2, String table){
		Connection conn = DatabaseConnection.connect();
		String query = "SELECT gene from " + table + " WHERE miRNA = ?";
		PreparedStatement ps1 = null; 
		ResultSet rs1 = null;
		PreparedStatement ps2 = null; 
		ResultSet rs2 = null;
		try{
			ps1 = conn.prepareStatement(query);
			ps1.setString(1, miRNA1);
			rs1 = ps1.executeQuery();
			ps2 = conn.prepareStatement(query);
			ps2.setString(1, miRNA2);
			rs2 = ps2.executeQuery();
			while(rs1.next()){
				setGenes1(rs1.getString("gene").trim());
			}
			while(rs2.next()){
				setGenes2(rs2.getString("gene").trim());
			}
		}
		catch(SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		DatabaseConnection.closeDatabase(ps1,rs1, ps2, rs2, conn);
	}
	public void setGenes1(String gene){
		this.genes1.add(gene);
	}
	public void setGenes2(String gene){
		this.genes2.add(gene);
	}
	public ArrayList<String> getGenes1(){
		return this.genes1;
	}
	public ArrayList<String> getGenes2(){
		return this.genes2;
	}

}
