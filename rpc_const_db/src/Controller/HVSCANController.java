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


public class HVSCANController {
private SqlServerConnector sql;
private List combination=null;
private List combination_for_testset=null;
private HashMap<String,Integer> hm= null ;
private List chamber_id_list=null;


public HVSCANController(){
 sql = new SqlServerConnector();
 combination = new ArrayList<String>();
 chamber_id_list = new ArrayList<String>();
 combination_for_testset = new ArrayList<String>();
 hm=new HashMap<String,Integer>();

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
				
				if(cosmic.getColumnCount()!=107)
					error.add("No of Columns are different in line number : "+(cosmic.getCurrentRecord()+1));
								
					for (int i=0;i<cosmic.getColumnCount();i++){
						if(i==0)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("Chamber Name is Null in line number : "+(cosmic.getCurrentRecord()+1));
							
						if(i==1)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("ETA Partition is Null in line number : "+(cosmic.getCurrentRecord()+1));
									
						if(i==4)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								;
							else{
								if(isParsableToDouble(cosmic.get(i))){
									double efficiency = Double.parseDouble(cosmic.get(i));
										if(efficiency<0 || efficiency>100)
											error.add("Efficiency at line number : "+(cosmic.getCurrentRecord()+1)+" is out of range (0-100).");
								}
								else
									error.add("Error at line number : "+(cosmic.getCurrentRecord()+1)+": Value "+cosmic.get(i)+" is not number");
							} 
						if(i==0 || i==1 || i==4){}
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
			
			
//			for(int i =0;i<combination.size();i++)
//				System.out.println(combination.get(i));
			/*
			for(int i =0;i<combination.size();i++){
				int first = combination.indexOf(combination.get(i));
				int last = combination.lastIndexOf(combination.get(i));
				if (first==last)
				{}
				else
					error.add("Error at line number: "+(i+1)+". Duplicate combination of Run, Chamber Id and Eta Partition i.e "+combination.get(i)+" exist in the file");
				
			}
			
			//System.out.println(combination.size());
			for(int i =0;i<combination.size();i++){
				String temp = combination.get(i).toString();
				String[] tokens = temp.split(",");
			//	System.out.println(tokens[0]+","+ tokens[1]+","+ tokens[2]);
				if(checkCombinationExist(tokens[0], tokens[1], tokens[2]))
					error.add("Error at line number: "+(i+1)+". Duplicate combination of Run, Chamber Id and Eta Partition i.e "+combination.get(i)+" exist in the database");
				
			}
			*/
			for(int i =0;i<error.size();i++)
				System.out.println(error.get(i));

		//	for(int i =0;i<chamber_id_list.size();i++)
		//		System.out.println(chamber_id_list.get(i));

			
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

public  void insertRecords(File file, double pressure, java.sql.Date req_date, double series){
	PreparedStatement stmt;
	ResultSet rs=null;
	try
	{
		
		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		CsvReader cosmic = new CsvReader(br);
		 //cosmic.readHeaders();
		while (cosmic.readRecord())
		{		
		String query = "insert into hv_scan values (HV_SCAN_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		//System.out.println(query);
			for (int i=0;i<cosmic.getColumnCount();i++){
			
				if(i==0 )
					stmt.setString((i+1), cosmic.get(i));
				else 
					if(i==1 )
						stmt.setString((i+1), cosmic.get(i).toUpperCase());
					else
						stmt.setString((i+1), cosmic.get(i));
			}
			stmt.setDate(108,req_date);
			stmt.setDouble(109, pressure);
			stmt.setDouble(110, series);
			
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
	
	  File file = new File("hv_scan2.csv");
	  HVSCANController cont = new HVSCANController();
	  //cont.gasMixture();
	  cont.verifyFile(file);
	  //System.out.println(cont.checkDateFormat("300/12/2012"));
	  // cont.getChamberIdinfile();
	  Date date = new Date();
	  java.sql.Date dates = new java.sql.Date( date.getTime());
	  cont.insertRecords(file,990,dates,2011);
}
public List getCombination() {
	return combination;
}
public void setCombination(List combination) {
	this.combination = combination;
}
public List getCombination_for_testset() {
	return combination_for_testset;
}
public HashMap<String, Integer> getHm() {
	return hm;
}
}
