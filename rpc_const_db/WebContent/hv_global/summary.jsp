<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="java.sql.PreparedStatement"  %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@page import="java.io.*"%>
<%@page import="Controller.*"%>
<%@ page import="java.io.*"%>  
<%@ page import="java.sql.*"%>
<%@page import="Database.SqlServerConnector"%>
<%@page import="com.oreilly.servlet.*"%>
<%@ page import="java.util.Date,java.sql.ResultSet,java.util.List,java.text.SimpleDateFormat,java.text.ParseException"%>
<%@page import="javax.security.auth.Subject"%>

<%@page import="Controller.*"%>

<%!
public int nullIntconv(String str)
{   
    int conv=0;
    if(str==null)
    {
        str="0";
    }
    else if((str.trim()).equals("null"))
    {
        str="0";
    }
    else if(str.equals(""))
    {
        str="0";
    }
    try{
        conv=Integer.parseInt(str);
    }
    catch(Exception e)
    {
    }
    return conv;
}
%>
<%

    SqlServerConnector  sql = new SqlServerConnector();
    ResultSet rsPagination = null;
    ResultSet rsRowCnt = null;
    
    PreparedStatement psPagination=null;
    PreparedStatement psRowCnt=null;
    
    int iShowRows=200;  // Number of records show on per page
    int iTotalSearchRecords=5;  // Number of pages index shown
    
    int iTotalRows=nullIntconv(request.getParameter("iTotalRows"));
    int iTotalPages=nullIntconv(request.getParameter("iTotalPages"));
    int iPageNo=nullIntconv(request.getParameter("iPageNo"));
    int cPageNo=nullIntconv(request.getParameter("cPageNo"));
    int iStartResultNo=0;
    int iEndResultNo=0;
    
    if(iPageNo==0)
    {
        iPageNo=0;
    }
    else{
        iPageNo=Math.abs((iPageNo-1)*iShowRows);
    }
    
    String sqlPagination =" SELECT * FROM ( SELECT chamber_name, eta_partition,wp_roll, wp_hv_ch,max_efficiency,slope50,hv50,chi2,efficiency_wp_roll,efficiency_wp_hv_ch,cls_wp_roll,cls_wp_hv_ch, series ,ROW_NUMBER() OVER (ORDER BY chamber_name) R FROM hv_scan_global_parameter) WHERE R BETWEEN "+ iPageNo+ " and " +(iPageNo+iShowRows)+"";
    //out.print (sqlPagination);
    psPagination=sql.con.prepareStatement(sqlPagination);
    rsPagination=psPagination.executeQuery();
     String sqlRowCnt="SELECT count(*) as cnt FROM hv_scan_global_parameter";
     psRowCnt=sql.con.prepareStatement(sqlRowCnt);
     rsRowCnt=psRowCnt.executeQuery();
     
     if(rsRowCnt.next())
      {
         iTotalRows=rsRowCnt.getInt("cnt");
      }
%>
<html>
<head>
<title>Pagination of JSP page</title>

<style type="text/css">
<!--
.style1 {color: #FFFFFF}
-->
</style>
</head>
<body>
<form name="frm">
<input type="hidden" name="iPageNo" value="<%=iPageNo%>">
<input type="hidden" name="cPageNo" value="<%=cPageNo%>">
<input type="hidden" name="iShowRows" value="<%=iShowRows%>">
<table width="80%"  border="1" align="center" cellpadding="1" cellspacing="1" style="border-collapse:collapse">
  <tr bgcolor="#CC0000">
    <th scope="col"><span class="style9 style1">Chamber Name</span></th>
    <th scope="col"><span class="style9 style1">ETA Partition</span></th>
   <th scope="col"><span class="style9 style1">WP Roll</span></th>
   
    <th scope="col"><span class="style9 style1">WP HV Ch</span></th>
    <th scope="col"><span class="style9 style1">Max Efficiency</span></th>
    <th scope="col"><span class="style9 style1">Slope 50</span></th>
    <th scope="col"><span class="style9 style1">HV 50</span></th>
    <th scope="col"><span class="style9 style1">CHi2</span></th>
    
    <th scope="col"><span class="style9 style1">Efficiency WP Roll</span></th>
    <th scope="col"><span class="style9 style1">Efficiency WP HV Ch</span></th>
    <th scope="col"><span class="style9 style1">CLS WP Roll</span></th>
    <th scope="col"><span class="style9 style1">CLS WP HV Ch</span></th>
    <th scope="col"><span class="style9 style1">Series</span></th>
    
      </tr>
<%
	while(rsPagination.next()){
  		out.print("<tr bgcolor='#F4F2E7'>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(1)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(2)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(3)+"</td>");
  	  	out.print("<td class='style2' align='center'>"+rsPagination.getString(4)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(5)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(6)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(7)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(8)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(9)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(10)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(11)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(12)+"</td>");
  		out.print("<td class='style2' align='center'>"+rsPagination.getString(13)+"</td>");
  		
  		out.print("</tr>");

	}

   %>
<%
  //// calculate next record start record  and end record 
        try{
            if(iTotalRows<(iPageNo+iShowRows))
            {
                iEndResultNo=iTotalRows;
            }
            else
            {
                iEndResultNo=(iPageNo+iShowRows);
            }
           
            iStartResultNo=(iPageNo+1);
            iTotalPages=((int)(Math.ceil((double)iTotalRows/iShowRows)));
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

%>
<tr>
<td colspan="3">
<div>
<%
        //// index of pages 
        
        int i=0;
        int cPage=0;
        if(iTotalRows!=0)
        {
        cPage=((int)(Math.ceil((double)iEndResultNo/(iTotalSearchRecords*iShowRows))));
        
        int prePageNo=(cPage*iTotalSearchRecords)-((iTotalSearchRecords-1)+iTotalSearchRecords);
        if((cPage*iTotalSearchRecords)-(iTotalSearchRecords)>0)
        {
         %>
          <a href="summary.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>"> << Previous</a>
         <%
        }
        
        for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
        {
          if(i==((iPageNo/iShowRows)+1))
          {
          %>
           <a href="summary.jsp?iPageNo=<%=i%>" style="cursor:pointer;color: red"><b><%=i%></b></a>
          <%
          }
          else if(i<=iTotalPages)
          {
          %>
           <a href="summary.jsp?iPageNo=<%=i%>"><%=i%></a>
          <% 
          }
        }
        if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
        {
         %>
         <a href="summary.jsp?iPageNo=<%=i%>&cPageNo=<%=i%>"> >> Next</a> 
         <%
        }
        }
      %>
<b>Rows <%=iStartResultNo%> - <%=iEndResultNo%>   Total Result  <%=iTotalRows%> </b>
</div>
</td>
</tr>
</table>
</form>
</body>
</html>
<%
    try{
         if(psPagination!=null){
             psPagination.close();
         }
         if(rsPagination!=null){
             rsPagination.close();
         }
         
         if(psRowCnt!=null){
             psRowCnt.close();
         }
         if(rsRowCnt!=null){
             rsRowCnt.close();
         }
         
         if(sql.con!=null){
          sql.con.close();
         }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>

