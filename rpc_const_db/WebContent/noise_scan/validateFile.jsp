<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.text.*" %>
<%@page import="java.util.*" %>
<%@page import="javax.naming.NamingException"%>
<%
String flagtest ="";
flagtest = request.getParameter("flag");
if(flagtest==null){}
else{

	if(flagtest.equalsIgnoreCase("3"))
		out.print("<h2 align='center' class='style1  style2' > Date should be selected. </h2>");
	else
		if(flagtest.equalsIgnoreCase("6")){
			out.print("<h2 align='center' class='style1  style2' > Series value is required. </h2>");
		}
		else
			if(flagtest.equalsIgnoreCase("7")){
				out.print("<h2 align='center' class='style1  style2' > Series value is wrong.</h2>");
			}
			else
				if(flagtest.equalsIgnoreCase("1")){
					out.print("<h2 align='center' class='style1  style2' > You have not selected file: </h2>");
					}
				else if(flagtest.equalsIgnoreCase("2"))
						out.print("<h2 align='center' class='style1  style2' > You have selected wrong file: </h2>");
}
											
	 
	%>									