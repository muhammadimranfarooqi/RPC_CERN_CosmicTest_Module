<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.List" %>
   <%@ page import="java.util.Iterator" %>
   <%@ page import="java.io.File" %>

<%@page import="java.io.FileReader"%>
   <%@ page import="java.sql.*" %>
  <%@ page import= "java.io.FileOutputStream.*"%>
  <%@ page import= "java.util.Properties"%>
  
  <%@page import="java.io.InputStream" %>
  <%@page import="java.io.BufferedReader"%>
  <%@page import="Controller.*"%>

<%@page import="Database.*"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.FileWriter"%>
<html>
<head>

   
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%SqlServerConnector con=new SqlServerConnector();
  

Properties prop = new Properties();

String current = request.getParameter("current");
String Newpass=request.getParameter("new");
String conpass=request.getParameter("confirm");



	
					String data="",username="",pass="";
					/*String[] temp;
					
					pass=con.pass;
					username=con.username;
					//con.setProperties("abc","123");
					
					
					//String username = prop.getProperty("username");
		        	//String password = prop.getProperty("password");
		        	out.print("From page : "+username+"    "+pass);
		        	
		        	
    
		         	// String username = prop.getProperty("username");
		        		///String password = prop.getProperty("password");
		        	*/
					//out.print(username+password);
				/*	String storedPass="";
					if(rs.next())
					{
						storedPass=rs.getString("pass");
						if(storedPass.equals(current))
						response.sendRedirect("changepass.jsp");
						else
						response.sendRedirect("error.jsp");	
					}else
						response.sendRedirect("changepass.jsp");
					*/	
 %>
 <%
           String jspPath = session.getServletContext().getRealPath("/");
			String temp,flag="false";
			String[] array;
           // String txtFilePath = jspPath+"WEB-INF\\classes\\Database\\config.properties";
            
			 String txtFilePath = jspPath+"WEB-INF/classes/Database/config.properties";
			 
            System.out.print("path: "+txtFilePath); 
           BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
           BufferedReader reader1 = new BufferedReader(new FileReader(txtFilePath));
        //   BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));
           StringBuilder sb = new StringBuilder();
            String line,line2;

            while((line = reader.readLine())!= null){
            	//out.print(line+"\n");
            	sb.append(line+"\n");
                
            }
            
           // out.print(line);
           // out.println("printing "+sb.toString());
            temp=sb.toString();
           // out.print(temp);
            
            array=temp.split("=");
            
            pass=array[1];
            pass=pass.replaceAll("username","");
            username=array[2];
           // out.print("password="+pass);
          	//out.print("username="+username);
            String storedPass = "password="+pass;
            storedPass=storedPass.trim();
          	String currentPass="password="+current;
          	//out.println(storedPass+" === "+currentPass);
          	if (storedPass.equals(currentPass))
          		flag="true";
          	else
          		response.sendRedirect("error.jsp");	
          
          	
          	
			//String currentLine;
          	///out.println(storedPass+"     "+currentPass);
          	
          if(flag=="true"|| flag.equals("true")){
			FileWriter fw = new FileWriter(txtFilePath);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("#"+System.currentTimeMillis());
			bw.newLine();
			bw.append("password="+conpass);
			bw.newLine();
			bw.write("username=cms_rpc_constr");
			bw.flush();
			bw.close();
			//out.print(flag);
			response.sendRedirect("changepass.jsp");
          }

			
			
         %>
</body>
</html>