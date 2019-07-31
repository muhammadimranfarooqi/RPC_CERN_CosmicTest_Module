<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@page import="java.io.*"%>
<%@page import="Controller.*"%>
<%@ page import="java.io.*"%>  
<%@ page import="java.sql.*"%>
<%@ page import="Database.SqlServerConnector"%>
<%@ page import="com.oreilly.servlet.*"%>
<%@ page import="java.util.Date,java.sql.ResultSet,java.util.List,java.text.SimpleDateFormat,java.text.ParseException"%>
<%@ page import="javax.security.auth.Subject"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.style1 {
	color: #000033;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
<script src="selectItem.js"></script>
</head>

<body>
<table width="100%"  border="0" style="border-collapse:collapse ">
 <tr>
    <td width="100%" class="style2"><div align="center">
	<%
	out.print("Testing Database Connection:");
	
	 String jspPath = session.getServletContext().getRealPath("/");
	
	 out.print(jspPath);
     String txtFilePath = jspPath+"WEB-INF/classes/Database/config.properties";
     
	//out.print("path: "+txtFilePath); 
   
	 
	int i =0;
	try{
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@( DESCRIPTION= (ADDRESS= (PROTOCOL=TCP) (HOST=cmsr1-s.cern.ch) (PORT=10121)) (ADDRESS= (PROTOCOL=TCP) (HOST=cmsr2-s.cern.ch) (PORT=10121)) (ADDRESS= (PROTOCOL=TCP) (HOST=cmsr3-s.cern.ch) (PORT=10121)) (LOAD_BALANCE=on) (ENABLE=BROKEN) (CONNECT_DATA= (SERVER=DEDICATED) (SERVICE_NAME=cmsr_lb.cern.ch)))","cms_rpc_constr","RPCprova1");
	}
	catch (Exception e){
		i++;
		out.print("<br> Database Connection Failed:");
		out.print("<br> "+e.getMessage());
	}
	if (i==0)
		out.print("<br> Database Connection Successfull:");
		
	
	

	%>
	 </div></td>
	  </tr>
  <tr>
    <td colspan="2">
	
    </td>
  </tr>
</table>
</body>
</html>
