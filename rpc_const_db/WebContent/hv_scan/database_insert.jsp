<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.io.*"%>
<%@page import="Controller.*"%>
<%@ page import="java.io.*"%>  
<%@ page import="java.sql.*"%>
<%@page import="Database.SqlServerConnector"%>
<%@page import="com.oreilly.servlet.*"%>
<%@ page import="java.util.Date,java.sql.ResultSet,java.util.List,java.text.SimpleDateFormat,java.text.ParseException"%>
<%@page import="javax.security.auth.Subject"%>

<%@page import="Controller.*"%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.style1 {color: #FFFF00}
.style3 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.style8 {font-family: Verdana, Arial, Helvetica, sans-serif}
-->
</style>
</head>

<body>

<%
	int stacknumber=0;
	FileRename f = new FileRename();
	String body="";
	String filename = request.getParameter("file");
	String url = request.getParameter("test");
	String pressure = request.getParameter("pressure");
	String series = request.getParameter("series");
	String reqDate = request.getParameter("reqDate");
	java.util.Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(reqDate);
	java.sql.Date datesql = new java.sql.Date( date.getTime());
	File file = new File (Controller.getTempDirectory()+"/"+filename);
	HVSCANController cont = new HVSCANController();
	//out.print(file.getName());
	cont.verifyFile(file);
	cont.insertRecords(file,Double.parseDouble(pressure),datesql,Double.parseDouble(series));
	file.delete();
%>
<form action="" method="get" >
<table width="80%" border="0" align="center" cellpadding="4" cellspacing="1" style="border-collapse:collapse">
  <tr>
    <td width="100%" class="style2"><div align="center">
      <input name="Import" type="button" id="Import" value="Import New File" onClick="window.open('<%=url%>','_top')">
    </div></td>
  </tr>
  
</table>
</form>
<BR>
<iframe id="frame1" src="summary.jsp" height="1100" width="100%" align="center" FRAMEBORDER=0>You need a Frames Capable browser to view this content</iframe> 

</body>
</html>
