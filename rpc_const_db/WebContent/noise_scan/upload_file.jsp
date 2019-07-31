<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.io.*"%>
<%@page import="Controller.*"%>
<%@ page import="java.io.*"%>  
<%@ page import="java.sql.*"%>
<%@page import="Database.SqlServerConnector"%>
<%@page import="com.oreilly.servlet.*"%>
<%@ page import="java.util.*,java.sql.ResultSet,java.util.List,java.text.SimpleDateFormat,java.text.ParseException"%>
<%@page import="javax.security.auth.Subject"%>
<%@page import="Controller.*"%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.style3 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.style8 {font-family: Verdana, Arial, Helvetica, sans-serif}
.style9 {color: #FFFFFF}
.style10 {
	color: #FF0000;
	font-weight: bold;
}
-->
</style>
<script type="text/javascript">
        
        function tableHide()
		{
		//alert('testing'); 
		document.getElementById("errortable").style.display = 'none';
		//document.getElementById("errortable").style.visibility = "hidden";
		document.getElementById("Hide").style.display = "none";
		document.getElementById("Show").style.display = "block";
		
		}
        function tableShow()
		{
		
		document.getElementById("errortable").style.display = "";
		document.getElementById("Hide").style.display = "block";
		document.getElementById("Show").style.display = "none";
		
		}
		
</script>
</head>

<body>

<%
	
	FileRename f = new FileRename();
	String body="";
	MultipartRequest mrequest = new MultipartRequest(request,Controller.getTempDirectory(),20*1021*1024,f);

	String filename = mrequest.getFilesystemName("file");
	String url = mrequest.getParameter("test1");
	String series = mrequest.getParameter("series");

	String reqDate = mrequest.getParameter("reqDate");

	File file = mrequest.getFile("file");
	NoiseScanController cont = new NoiseScanController();
	List error = cont.verifyFile(file);
	if(error.size()>0){
%><BR>
<form action="" method="get" >
<table width="80%" border="1" align="center" cellpadding="4" cellspacing="1" style="border-collapse:collapse">
  <tr bgcolor="#CC0000">
  <td colspan="3" class="style2"><strong> <div align="center" class="style9"><strong>Imported file contains some errors.</strong></div></td></tr>
  <tr>
    <td width="33%" class="style2"><div align="center">
      <input type="button" name="Button" value="Cancel" onClick="window.open('<%=url%>','_top')">
    </div></td>
    <td width="33%" class="style2"><div align="center">
      <input name="Import" type="button" id="Import" value="Import New File" onClick="window.open('<%=url%>','_top')">
    </div></td>
    <td width="33%" class="style2"><div align="center">
      <input name="Show" type="button" id="Show" value="Show Errors" onClick="tableShow()">
      <input name="Hide" type="button" style='display:none' id="Hide" value="Hide Errors" onClick="tableHide()">
    </div></td>
  </tr>
  
</table>

</form>
<br>
<%		file.delete();
	}
 %>
<table width="80%" id="errortable" style='display:none'  border="1" align="center" cellpadding="2" cellspacing="1" style="border-collapse:collapse">
  <tr bgcolor="#CC0000" width="80%">
    <th width="100%" bgcolor="#CC0000" scope="col"><span class="style9">The imported file contains the following errors</span></th>
  </tr>
		
<%
			for(int i =0;i<error.size();i++)
		{
		out.print("<tr bgcolor='#F4F2E7'>");
  		out.print("<td class='style2'><strong>"+error.get(i)+"</td>");
  		out.print("</tr>");
		}


%></table><br>
<%if(error.size()>0){}else{
	
	
	%>
<form  name="form1" method="post" action="database_insert.jsp">
	
<table width="80%"  border="1" align="center" cellpadding="1" cellspacing="1" style="border-collapse:collapse">
  <tr bgcolor="#F4F2E7">
    <td colspan="3"><div align="center" class="style8"><strong>
      Data Validation successfully done. The imported file contains no errors. </strong></div></td>
  </tr>
   
  <tr bgcolor="#F4F2E7" id="row4" >
    <td bgcolor="#F4F2E7">
	  <div align="center" class="style8"><strong>Click the save button to store data in database. </strong></div>	</td>
    <td bgcolor="#F4F2E7"><div align="center">
      <input type="button" class="style3" name="Button" value="Cancel" onClick="window.open('<%=url%>','_top')">
     
    </div></td>

    <td bgcolor="#F4F2E7"><div align="center">
      <input name="Save" type="submit" class="style3" value="Save" >
      <input type="hidden" name="file" id="file" value="<%out.print(file.getName()); %>" size="35">
	  <input type="hidden" name="test" id="test" value="<%=url%>" size="35">
	 <input type="hidden" name="reqDate" id="reqDate" value="<%=reqDate%>" size="35">
   <input type="hidden" name="series" id="series" value="<%=series%>" size="35">
   
    </div></td>
 
  </tr>
  
</table>
</form>
<%} %>
<iframe id="frame1" src="summary.jsp" height="1100" width="100%" align="center" FRAMEBORDER=0>You need a Frames Capable browser to view this content</iframe> 

</body>
</html>
