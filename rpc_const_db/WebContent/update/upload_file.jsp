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
        function checkStack()
		{
		var stack = document.getElementById("stack").value;
		if(stack==null || stack=="")
		{
		document.getElementById("stackmsj").style.display = "";
		return false;
		}
		else{
		return true;
		}
		
		}
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
	function setGasMixture(sel)
		{
		//document.getElementById("gas_mixture_text").style.display = "none";
		document.form1.gas_mixture_text.value = sel.options[sel.selectedIndex].text;  
		}
	function displayGasMixture()
		{
		document.getElementById("row1").style.display = "";
		}
		function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){ //if the key isn't the backspace key (which we should allow)
			if (unicode<48||unicode>57) //if not a number
			return false //disable key press
		}
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
	String test_set = mrequest.getParameter("test_set_txt");
	File file = mrequest.getFile("file");
	Controller cont = new Controller();
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
	
	String gm = "Freon 95.2% + Isobutan 4.5% + SF6 0.3%";
	%>
<form  name="form1" method="post" action="database_insert.jsp">
	
<table width="80%"  border="1" align="center" cellpadding="1" cellspacing="1" style="border-collapse:collapse">
  <tr bgcolor="#F4F2E7">
    <td colspan="3"><div align="center" class="style8"><strong>
      Data Validation successfully done. The imported file contains no errors. </strong></div></td>
  </tr>
  <tr bgcolor="#F4F2E7"  >
    <td bgcolor="#F4F2E7" class="style8"><strong>*Select Flag</strong></td>
    <td colspan="2" bgcolor="#F4F2E7"><select name="flag" id="flag">
      <option value="GOOD">GOOD</option>
      <option value="BAD">BAD</option>
    </select></td>
    </tr>
  <tr bgcolor="#F4F2E7" >
    <td bgcolor="#F4F2E7" class="style8"><strong>*Enter Stack Number </strong></td>
    <td colspan="2" bgcolor="#F4F2E7"><input name="stack" type="text" id="stack" onkeypress="return numbersonly(event)" >
    <label style="display:none" id="stackmsj"><span class="style10"> Stack value is required.</span></label></td>
    </tr>
  <tr bgcolor="#F4F2E7" id="row5" >
    <td bgcolor="#F4F2E7" class="style8"><strong>*Gas Mixture Selected: </strong></td>
    <td bgcolor="#F4F2E7"><input name="gas_mixture_text" type="text" class="style3" id="gas_mixture_text" value="<%=gm%>" size="50" readonly></td>
    <td bgcolor="#F4F2E7"><div align="center">
      <input name="Change" type="button" class="style3" id="Change" value="Change" onClick="displayGasMixture()">
    </div></td>
  </tr> 
  
  <tr bgcolor="#F4F2E7" id="row1" style="display:none">
  <td bgcolor="#F4F2E7" class="style8"><strong>Select Gas Mixture:</strong></td>
    <td bgcolor="#F4F2E7"><select name="gas_mixture" id="gas_mixture" onChange="setGasMixture(this)">
      
	 <%
		HashMap gas = cont.gasMixture();
	 	Iterator iterator = gas.keySet().iterator();
		//for(int i=0;i<gas.size();i++)
			while(iterator. hasNext()){ 
			String value = iterator.next().toString();
			String selected = "false";
			if(gas.get(value).toString().equalsIgnoreCase(gm))
				out.print("<option selected='true' value = "+value+">"+gas.get(value)+"</option>");
			else
				out.print("<option  value = "+value+">"+gas.get(value)+"</option>");
			}
	%>  
    
    </select></td>
    <td bgcolor="#F4F2E7"><div align="center">
      <input name="register" type="hidden" class="style3" id="register" value="Register New Gas Mixture" onClick="window.open('http://111.68.96.179:8080/apex/f?p=107:108:','_top')">
    </div></td>
  </tr>
 
  <tr bgcolor="#F4F2E7" id="row4" >
    <td bgcolor="#F4F2E7">
	  <div align="center" class="style8"><strong>Click the save button to store data in database. </strong></div>	</td>
    <td bgcolor="#F4F2E7"><div align="center">
      <input type="button" class="style3" name="Button" value="Cancel" onClick="window.open('<%=url%>','_top')">
     
    </div></td>

    <td bgcolor="#F4F2E7"><div align="center">
      <input name="Save" type="submit" class="style3" value="Save" onclick="return checkStack();">
      <input type="hidden" name="file" id="file" value="<%out.print(file.getName()); %>" size="35">
	  <input type="hidden" name="chamber_id" id="chamber_id" value="<%out.print(cont.getChamberIdinfile());%>" size="35">
	  <input type="hidden" name="test" id="test" value="<%=url%>" size="35">
	 <input type="hidden" name="test_set" id="test_set" value="<%=test_set%>" size="35">
    </div></td>
 
  </tr>
  
</table>
</form>
<%} %>

<iframe id="frame1" src="summary.jsp?chamber_id=<%out.print(cont.getChamberIdinfile());%>" height="1100" width="100%" align="center" FRAMEBORDER=0>You need a Frames Capable browser to view this content</iframe> 
<%
//	}
//	}
//	else{

//	response.sendRedirect("select_file.jsp?flag=2");
//	mrequest.getFile("file").delete();	
	
//}
//	}
	%>
</body>
</html>
