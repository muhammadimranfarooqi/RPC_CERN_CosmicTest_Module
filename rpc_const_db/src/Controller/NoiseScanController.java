package Controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import Database.SqlServerConnector;
import com.csvreader.CsvReader;


public class NoiseScanController {
private SqlServerConnector sql;

public NoiseScanController(){
 sql = new SqlServerConnector();

}
public List verifyFile(File file){
	  List error = new ArrayList<String>();

	  try {
		  FileInputStream fstream = new FileInputStream(file);
		  
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  
		  CsvReader cosmic = new CsvReader(br);
		 
		  while (cosmic.readRecord())
			{
//				System.out.println("Total No of Columns : "+cosmic.getColumnCount());
//				System.out.println("Current Record : "+cosmic.getCurrentRecord());
//				System.out.println("Raw Record"+test.add(cosmic.getRawRecord()));
//				System.out.println("List Record: "+test.get(i));
//				System.out.println("List Record: "+cosmic.get(0));
				
				if(cosmic.getColumnCount()!=4)
					error.add("No of Columns are different in line number : "+(cosmic.getCurrentRecord()+1));
								
					for (int i=0;i<cosmic.getColumnCount();i++){
						if(i==0)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("Chamber Name is Null in line number : "+(cosmic.getCurrentRecord()+1));
							
						if(i==1)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("ETA Partition is Null in line number : "+(cosmic.getCurrentRecord()+1));
									
						if(i==0 || i==1 ){}
						else{
							if(cosmic.get(i).isEmpty())
								;
							else
												
								if(isParsableToDouble(cosmic.get(i)))
								;
								else
									error.add("Error at line number : "+(cosmic.getCurrentRecord()+1)+": Column No : "+(i+1)+". Value "+cosmic.get(i)+" should be number");
										
						}
						
					}
			}
			cosmic.close();
			for(int i =0;i<error.size();i++)
				System.out.println(error.get(i));

			
	  } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return error;
}

public boolean isParsableToInteger(String i){
	try
	{
	Integer.parseInt(i);
	return true;
	}
	catch(NumberFormatException nfe)
	{
	return false;
	}
}
public boolean isParsableToDouble(String i){
	try
	{
	Double.parseDouble(i);
	return true;
	}
	catch(NumberFormatException nfe)
	{
	return false;
	}
}
boolean isValidDate(String input, SimpleDateFormat format)
{
  try{
	  format.setLenient(false);
	  Date temp = (Date)format.parse(input);
	  java.sql.Date sqlDate = new java.sql.Date(temp.getTime());

	  //System.out.println(temp);
	 
	  return true;
	  }
  catch(ParseException e){ return false; }
}

public boolean checkDateFormat(String str_date){
	Date date=null ;

	SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat format3 = new SimpleDateFormat("dd.MM.yyyy");

	if(isValidDate(str_date, format1))
		return true;
	else if (isValidDate(str_date, format2))
		return true;
	else if (isValidDate(str_date, format3))
		return true; 
	else return false;
	  
}

public  void insertRecords(File file, java.sql.Date req_date, double series){
	PreparedStatement stmt;
	//ResultSet rs=null;
	try
	{
		
		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		CsvReader cosmic = new CsvReader(br);
		 //cosmic.readHeaders();
		while (cosmic.readRecord())
		{		
		String query = "insert into noise_scan values (?,?,?,?,?,?,NOISE_SCAN_SEQ.NEXTVAL)";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		//System.out.println(query);
			for (int i=0;i<cosmic.getColumnCount();i++){

				if(i==0 )
					stmt.setString((i+1), cosmic.get(i));
				else 
			
				if(i==1 )
					stmt.setString((i+1), cosmic.get(i).toUpperCase());
				else
					stmt.setString((i+1),cosmic.get(i));
			}
			stmt.setDate(5,req_date);
			stmt.setDouble(6, series);
			int num = stmt.executeUpdate();

			try{stmt.close();}catch (Exception ex){}

		}
		cosmic.close();
		
	
	}
	catch(Exception nfe)
	{
		System.out.println(nfe.getLocalizedMessage());
	}

}

public java.sql.Date convertStringToSqlDate(String str_date){
	java.sql.Date sqlDate=null ;
	
	try {  
	
		if(str_date==null || str_date == "")
			return sqlDate;
		else{
			
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat format3 = new SimpleDateFormat("dd.MM.yyyy");

			if(isValidDate(str_date, format1))
			{
				Date temp = (Date)format1.parse(str_date);  
				sqlDate = new java.sql.Date(temp.getTime());
				return sqlDate;
				
			}
				else if (isValidDate(str_date, format2))
				{
					Date temp = (Date)format2.parse(str_date);  
					sqlDate = new java.sql.Date(temp.getTime());
					return sqlDate;
					
				}else if (isValidDate(str_date, format3))
						{
							Date temp = (Date)format3.parse(str_date);  
							sqlDate = new java.sql.Date(temp.getTime());
							return sqlDate;
							
						}
						
			}
	} 
	catch (ParseException e)
	{
		System.out.println("Date pattern should be dd/MM/yyyy"); 
		//JOptionPane.showMessageDialog( null, "Date pattern should be dd/MM/yyyy"); 
	}  
 return sqlDate;
 
}
public static void main (String agrs[] ) throws FileNotFoundException{
	
	  File file = new File("noise_scan.csv");
	  NoiseScanController cont = new NoiseScanController();
	  //cont.gasMixture();
	  cont.verifyFile(file);
	  //System.out.println(cont.checkDateFormat("300/12/2012"));
	  // cont.getChamberIdinfile();
	  Date date = new Date();
	  java.sql.Date dates = new java.sql.Date( date.getTime());
	  //cont.insertRecords(file,dates);
}
}
