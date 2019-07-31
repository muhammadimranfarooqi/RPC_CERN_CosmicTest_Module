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
	String chamber_id = request.getParameter("chamber_id");
	String url = request.getParameter("test");
	String flag = request.getParameter("flag");
	int test_set = Integer.parseInt(request.getParameter("test_set"));
	if(request.getParameter("stack")==null || request.getParameter("stack")=="" || request.getParameter("stack").isEmpty()  )
		;
	else
		stacknumber = Integer.parseInt(request.getParameter("stack"));
	String gas_mixture = request.getParameter("gas_mixture_text");
	int gas_mixture_value = Integer.parseInt(request.getParameter("gas_mixture"));
	
//	out.print(stack);
//	out.print(flag);
//	out.print(gas_mixture);
//	out.print(gas_mixture_value);
	File file = new File (Controller.getTempDirectory()+"/"+filename);
	Controller cont = new Controller();
	//out.print(file.getName());
	cont.verifyFile(file);
	cont.updateRecords(file,flag,gas_mixture_value,stacknumber,test_set);
	//	out.print(cont.verifyFile(file).size());
	//cont.insertRecords(file);
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

<iframe id="frame1" src="summary.jsp?chamber_id=<%=chamber_id%>" height="1100" width="100%" align="center" FRAMEBORDER=0>You need a Frames Capable browser to view this content</iframe> 
</body>
</html>
