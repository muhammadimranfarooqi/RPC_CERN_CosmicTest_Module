package Controller;
import Database.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {
	public ResultSet getOldPassword() {
		ResultSet rs=null;
		SqlServerConnector sql = new SqlServerConnector();
		try {
			String sqlQuery = "select password as pass from rpc";
		    PreparedStatement stmt = sql.con.prepareStatement(sqlQuery); 
		    rs = stmt.executeQuery();
		    }catch(SQLException se){ System.out.println("EXCEPTION "+se.getMessage());}
		return rs;
	}
	
	public int updatePassword(String db,String Newpass) {
		int rs=0;
		SqlServerConnector sql = new SqlServerConnector();
		try {
			String sqlQuery = "update rpc set password='"+Newpass+"' where dbname='"+db+"'";
		    PreparedStatement stmt = sql.con.prepareStatement(sqlQuery); 
		    rs = stmt.executeUpdate();
		    }catch(SQLException se){ System.out.println("EXCEPTION "+se.getMessage());}
		return rs;
	}
	
	public int insertData(String dbName,String Password ) {
	    int rs=0;
	    SqlServerConnector cn = new SqlServerConnector();
	  try {
	    String sqlQuery = "insert into rpc (dbname,password)VALUES (?,?)";
	    PreparedStatement stmt = cn.con.prepareStatement(sqlQuery); 
	    
	    stmt.setString(1, dbName);
	    stmt.setString(2, Password);
	    
	    
	   
	    rs = stmt.executeUpdate();
	     }catch(SQLException se){}
	   return rs;
	}
}
