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
			  <td width="40%" class="style1"> <div align="center">Ener Test Set </div></td>
		      <td width="60%"><div align="center">
		        <input type="text" name="test_set_txt" id="test_set_txt" onkeypress="return numbersonly(event)">
	          </div></td>
		  </tr>
			<tr>
        	<td colspan="2"><div align="center" class="style1">Select csv or txt file to upload on database. </div></td>
        </tr>
		
      	<tr>
        	<td colspan="2">
          	<div align="center">
            <input name="file" type="file" id="file" size="35">
          	</div>
        	</td>
        </tr>
      	<tr>
        	<td colspan="2">
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
