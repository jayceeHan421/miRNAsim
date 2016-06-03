import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class getMiRNAsimValue {
	private double value = -1;
	private String miRNA1 = null;
	private String miRNA2 = null;
	private String table = null;
	
	public getMiRNAsimValue(String miRNA1, String miRNA2, String table){
		this.miRNA1 = miRNA1;
		this.miRNA2 = miRNA2;
		
		if(table.toLowerCase().contains("miRTarBase".toLowerCase())){
			this.table = "miRTarBase_miRsim_GO";
		}
		else if(table.toLowerCase().contains("targetScan".toLowerCase())){
			this.table = "targetScan_miRsim_GO";
		}
		else if(table.toLowerCase().contains("miRDB".toLowerCase())){
			this.table = "mirDB_miRsim_GO";
		}
		
	}
	
	public void getMiRNAgoSimValue(){
		Connection conn = DatabaseConnection.connect();
		String query = "SELECT value FROM "+ this.table + " WHERE (miRNA1 = ? AND miRNA2 = ?) OR (miRNA1 = ? AND miRNA2 = ?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(query);
			ps.setString(1, this.miRNA1);
			ps.setString(2, this.miRNA2);
			ps.setString(3, this.miRNA2);
			ps.setString(4, this.miRNA1);
			rs = ps.executeQuery();
			if(rs.next()){
				double val = rs.getFloat("value");
				setValue(val);
			}
		}
		catch(SQLException e){
			System.out.println("SQLExceptoin: ");
			e.printStackTrace();
			throw new RuntimeException(e);		
		}
	}
	
	public void setValue(double value){
		this.value = value;
	}
	
	public double getValue(){
		return this.value;
	}

}
