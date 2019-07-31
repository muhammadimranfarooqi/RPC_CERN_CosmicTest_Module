<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "RPC Construction Database">

<html>
<head>
<title>RPC Construction Database</title>
</head>
<script type="text/javascript">function validate(){
	if(document.changepass.current.value=="")
    {
      alert("Please enter Current Password");
      document.changepass.current.focus();
      return false;
    }
    if(document.changepass.new.value=="")
    {
      alert("Please enter New Password");
      document.changepass.new.focus();
      return false;
    }
    if(document.changepass.confirm.value=="")
    {
      alert("Please enter Confirm Password");
      document.changepass.confirm.focus();
      return false;
    }
    
    if(document.changepass.confirm.value!=document.changepass.new.value)
    {
      alert("New Password & Confirm Password does not matched. Please enter again!");
      document.changepass.new.value="";
      document.changepass.confirm.value="";
      document.changepass.new.focus();
      return false;
    }
    
    }</script>
<form action="validate.jsp" method="post" name="changepass">
<div align="center">
&nbsp;<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<table border="1" width="342" height="163" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bgcolor="#F4F2E7">
	<tr>
		<td align="center">Old Password</td><td align="center"><input type="password" name="current" ></td>
	</tr>
	<tr>
		<td align="center">New Password</td><td align="center"><input type="password" name="new"></td>
	</tr>
	<tr>
		<td align="center">Confirm Password</td><td align="center"><input type="password" name="confirm"></td>
	</tr>
	<tr>
		<td height="34" width="342" colspan="2">
		<p align="center">
		<input type="submit" onClick="return validate();" value="Confirm" name="Submit"></td>
	</tr>
</table>

</div>
</form>
</html>
