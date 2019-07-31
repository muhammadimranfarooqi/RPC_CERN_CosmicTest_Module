<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
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
<script language="JavaScript" src="../resources/Calendar/datetimepicker_css.js"></script>
</head>

<body>
<table width="100%"  border="0" style="border-collapse:collapse ">
	<%
//	String urls = "http://127.0.0.1:8080/apex/"+request.getParameter("test");
//	String urls = "http://111.68.96.179:8080/apex/"+request.getParameter("test");
	String urls = "https://apex.cern.ch/pls/htmldb_cmsr/"+request.getParameter("test");

	
	%>
  <tr>
    <td colspan="2">
	<form name="select_form" enctype="multipart/form-data" method="post" action="upload_file.jsp">
	<table width="30%"  border="1" align="center" cellpadding="2" bordercolor="#333333" bgcolor="#F4F2E7" style="border-collapse:collapse ">
		
		<div id="FileDiv" name="FileDiv" align="center"> </div>
		
			<tr>
			  <td class="style1"><div align="center">Select Date </div></td>
			  <td colspan="2">
			  <input name="reqDate" id="reqDate" size="20" readonly>
								<a href="javascript:NewCssCal('reqDate','yyyymmdd','dropdown',true,24,false)">
                                                        
                                                        <img src="../resources/Calendar/cal.gif" alt="Pick a date" width="16" height="16" border="0"></a>
			  &nbsp;</td>
		  </tr>
		  <tr>
			  <td width="40%" class="style1"> <div align="center">Ener  Series </div></td>
		      <td width="30%">
	            <div align="left">
	              <input name="series" type="text" id="series" size="10" >
                </div></td>
		      <td width="30%" class="style2" id ="series_label" style="display:none"><label>Value is wrong.</label>&nbsp;</td>
			</tr>
			
			<tr>
        	<td colspan="3"><div align="center" class="style1">Select csv or txt file to upload on database. </div></td>
        </tr>
		
      	<tr>
        	<td colspan="3">
          	<div align="center">
            <input name="file" type="file" id="file" size="35">
          	</div>
        	</td>
        </tr>
      	<tr>
        	<td colspan="3">
			<div align="center">
          	<input name="Submit" type="submit"  value="Upload File" onclick="return Checkfiles();">
          	<input type="hidden" name="test" id="test" value=" imran" size="35">
          	<input type="hidden" name="test1" id="test1" value="<%=urls%>" size="35">
          	
        	</div>
			</td>
      	</tr>
    </table>
    </form>
    
    </td>
  </tr>
</table>
</body>
</html>
