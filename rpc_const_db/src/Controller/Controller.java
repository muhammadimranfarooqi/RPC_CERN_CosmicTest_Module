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
import java.sql.Timestamp;
import javax.swing.JOptionPane;

import Database.SqlServerConnector;
import com.csvreader.CsvReader;


public class Controller {
private SqlServerConnector sql;
private List combination=null;
private List combination_for_testset=null;
private HashMap<String,Integer> hm= null ;
private List chamber_id_list=null;


public Controller(){
 sql = new SqlServerConnector();
 combination = new ArrayList<String>();
 chamber_id_list = new ArrayList<String>();
 combination_for_testset = new ArrayList<String>();
 hm=new HashMap<String,Integer>();

}
public static String getTempDirectory(){
	String tmpFolder = System.getProperty("java.io.tmpdir");
	String temp = tmpFolder + "/rpc_construction_db";
	File dir = new File(temp);
	boolean dirCheck = dir.isDirectory();
	if (dirCheck) {
	    File[] listFiles = dir.listFiles();              
		long purgeTime = System.currentTimeMillis() - (1 * 1 * 60 * 60 * 1000); 
		//System.out.println("Purge time: "+purgeTime);
		
	    for(File listFile : listFiles) {  
	    ///	out.print("<br>"+listFile.lastModified());

	        if(listFile.lastModified() < purgeTime) {  
	        	listFile.delete();
	        }  
	    }
	}
	    else
	    	new File(temp).mkdirs(); 
	return temp;
}

public List verifyFile(File file){
	  List error = new ArrayList<String>();

	  try {
		  FileInputStream fstream = new FileInputStream(file);
		  
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  
		  CsvReader cosmic = new CsvReader(br);
		  List run = new ArrayList<Integer>();
		  
		  List chamber_id = getChamberIds();
		  //cosmic.readHeaders();
			while (cosmic.readRecord())
			{
		//		System.out.println("Total No of Columns : "+cosmic.getColumnCount());
//				System.out.println("Current Record : "+cosmic.getCurrentRecord());
//				System.out.println("Raw Record"+test.add(cosmic.getRawRecord()));
//				System.out.println("List Record: "+test.get(i));
//				System.out.println("List Record: "+cosmic.get(0));
				if(cosmic.getColumnCount()!=237)
					error.add("No of Columns are different in line number : "+(cosmic.getCurrentRecord()+1));
								
					for (int i=0;i<cosmic.getColumnCount();i++){
						if(i==0)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("Run No is Null in line number : "+(cosmic.getCurrentRecord()+1));
						
						if(i==2)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("Site is Null in line number : "+(cosmic.getCurrentRecord()+1));
						
						
						if(i==6)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("Chamber ID is Null in line number : "+(cosmic.getCurrentRecord()+1));
							else if(isParsableToInteger(cosmic.get(i)))								 
									if(chamber_id.contains(Integer.parseInt(cosmic.get(i))))
										;
									else
										error.add("Error at line number : "+(cosmic.getCurrentRecord()+1)+": Chamber ID  "+cosmic.get(i) +" does not exist in database.");
						//	else
						//		error.add("Error at line number : "+(cosmic.getCurrentRecord()+1)+": Chamber ID  "+cosmic.get(i) +" should be number value.");
						
						if(i==7)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("ETA Partition is Null in line number : "+(cosmic.getCurrentRecord()+1));
							else if (cosmic.get(i).equalsIgnoreCase("A") ||cosmic.get(i).equalsIgnoreCase("B")||cosmic.get(i).equalsIgnoreCase("C")||cosmic.get(i).equalsIgnoreCase("All"))
								;
							else
								error.add("Error at line number : "+(cosmic.getCurrentRecord()+1)+". ETA Partition can only contain A, B, C and All values.");
											
						if(i==8)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("Layer is Null in line number : "+(cosmic.getCurrentRecord()+1));
						if(i==10)
							if(cosmic.get(i)==null ||cosmic.get(i)=="")
								error.add("HV is Null in line number : "+(cosmic.getCurrentRecord()+1));
						if(i==18)
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
						if(i==2 || i==7 || i==8){}
						else{
							if(i!=1)
								if( cosmic.get(i).isEmpty())
									;
								else
							
								if(isParsableToDouble(cosmic.get(i)))
								;
								else
									error.add("Error at line number : "+(cosmic.getCurrentRecord()+1)+": Column No : "+(i+1)+". Value "+cosmic.get(i)+" should be number");
										
						}
						
						if(i==1)
						{
							if(checkDateFormat(cosmic.get(i)))
								;
							else
								error.add("Error at line number : "+(cosmic.getCurrentRecord()+1)+": Column No : "+(i+1)+". Date value "+cosmic.get(i)+" format is not correct");
								
						}
						
					}
					combination.add(cosmic.get(0)+","+cosmic.get(2)+","+cosmic.get(7)+","+cosmic.get(6)+",test");
					chamber_id_list.add(cosmic.get(6));
					combination_for_testset.add(cosmic.get(6)+","+cosmic.get(7)+","+cosmic.get(8));

			}
			cosmic.close();
			
			
//			for(int i =0;i<combination.size();i++)
//				System.out.println(combination.get(i));
			
			for(int i =0;i<combination.size();i++){
				int first = combination.indexOf(combination.get(i));
				int last = combination.lastIndexOf(combination.get(i));
				if (first==last)
				{}
				else
					error.add("Error at line number: "+(i+1)+". Duplicate combination of Run, Site, Chamber Id and Eta Partition i.e "+combination.get(i)+" exist in the file");
				
			}
			//System.out.println(combination.size());
			for(int i =0;i<combination.size();i++){
				String temp = combination.get(i).toString();
				
				//System.out.println(temp);
				String[] tokens = temp.split(",");
			//	System.out.println(tokens.length);
			//	System.out.println(tokens[0]+","+ tokens[1]+","+ tokens[2]);
				if(checkCombinationExist(tokens[0], tokens[3], tokens[2], tokens[1]))
					error.add("Error at line number: "+(i+1)+". Duplicate combination of Run, Site, Chamber Id and Eta Partition i.e. "+combination.get(i)+" exist in the database");
				
			}
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
	SimpleDateFormat format4 = new SimpleDateFormat("dd.MM.yyyy HH:MM:SS");

	if(isValidDate(str_date, format1))
		return true;
	else if (isValidDate(str_date, format2))
		return true;
	else if (isValidDate(str_date, format4))
		return true;
	else if (isValidDate(str_date, format3))
		return true; 
	
	else return false;
	  
}

public List getChamberIds(){
	PreparedStatement stmt;
	ResultSet rs=null;
	List ch_id = new ArrayList<Integer>();
	try
	{
		String query = "select chamber_id from re4_chamber";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery();
		while(rs.next())
			ch_id.add(rs.getInt("chamber_id"));
		
		try{stmt.close();}catch (Exception ex){}

	}
	catch(Exception nfe)
	{
		nfe.getLocalizedMessage();
	}
	return ch_id;
}

public boolean checkCombinationExist(String num, String chamber_id, String eta_part, String site){
	PreparedStatement stmt;
	ResultSet rs=null;
	try
	{
		String query = "select cosmic_id from re4_cosmic_table where run=? and chamber_id=? and eta_partition=? and site = ? ";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1,num);
		stmt.setString(2,chamber_id);
		stmt.setString(3,eta_part.toUpperCase());
		stmt.setString(4,site);
		rs = stmt.executeQuery();
		
		if(rs.next()){
			try{stmt.close();}catch (Exception ex){}
			return true;
			}
		else{
			try{stmt.close();}catch (Exception ex){}
			return	false;
		}
	}
	catch(Exception nfe)
	{
		nfe.getLocalizedMessage();
	}
	return false;
}

public int getMaxTestSet(String chamber_id, String eta_part, String layer){
	PreparedStatement stmt;
	ResultSet rs=null;
	int test_set=0;
	try
	{
		String query = "select max(test_set) as test_set from re4_cosmic_table where chamber_id=? and eta_partition=? and layer=? ";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1,chamber_id);
		stmt.setString(2,eta_part.toUpperCase());
		stmt.setString(3,layer.toUpperCase());
		rs = stmt.executeQuery();
		rs.next();
		if(rs.getString("test_set")==null)
			test_set=1;
		else
			test_set=rs.getInt("test_set")+1;
		try{stmt.close();}catch (Exception ex){}

	}
	catch(Exception nfe)
	{
		nfe.getLocalizedMessage();
	}
	return (test_set);
}

public ResultSet displaySummary(){
	PreparedStatement stmt;
	ResultSet rs=null;
	int test_set=0;
	try
	{
		String query = "select ch.chamber_name, ct.eta_partition,ct.run, ct.layer,ct.hv, ct.date_time,ct.site,ct.test_set from re4_cosmic_table ct"
		+" join re4_chamber ch on ct.chamber_id = ch.chamber_id"
		+" order by ct.test_set desc , ct.eta_partition asc ";

		
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery();
		try{stmt.close();}catch (Exception ex){}
		
	}
	catch(Exception nfe)
	{
		nfe.getLocalizedMessage();
	}
	return rs;
}

public HashMap gasMixture(){
	
	PreparedStatement stmt;
	ResultSet rs=null;
//	List gasmix = new ArrayList<String>();
    HashMap hashMap = new HashMap();
	try
	{	SqlServerConnector sql = new SqlServerConnector();
		String query = "select gm_id,gas_mix from gas_mixtures";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery();
		
		while (rs.next()){
	//		gasmix.add(rs.getString("gas_mix").toString());
			hashMap.put(rs.getString("gm_id").toString(), rs.getString("gas_mix").toString());
		}
		try{stmt.close();}catch (Exception ex){}

	}
	catch(Exception nfe)
	{
		System.out.println(nfe.getMessage());
	}
	return hashMap;
}

public  void insertRecords(File file, String flag, int gas_mix,int stack){
	PreparedStatement stmt;
	ResultSet rs=null;
	try
	{
		List test_comb = getCombination_for_testset();
	//	System.out.println("List :"+test_comb);
		Set<String> set = new HashSet<String>(test_comb);
	//	System.out.println("List Set:"+set);

		for (String elem : set) {
		    String[] tokens = elem.split(",");
			int testset = getMaxTestSet(tokens[0], tokens[1], tokens[2]);
			hm.put(elem, testset);
		}
	//	System.out.println("Map:"+hm);
		
		FileInputStream fstream = new FileInputStream(file);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		CsvReader cosmic = new CsvReader(br);
		 //cosmic.readHeaders();
		while (cosmic.readRecord())
		{		
		String query = "insert into re4_cosmic_table values (RE4_COSMIC_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		//stmt.setString(1,"RE4_COSMIC_SEQ.NEXTVAL");

//		System.out.println(query);
			for (int i=0;i<cosmic.getColumnCount();i++){
	
				if(i==0)
					stmt.setInt((i+1), Integer.parseInt(cosmic.get(i)));
				else if(i==1)
					stmt.setTimestamp((i+1), convertStringToSqlDateTime(cosmic.get(i)));
				//	System.out.println(convertStringToSqlDateTime(cosmic.get(i)));
						
				else if(i==7 || i==8)
					stmt.setString((i+1), cosmic.get(i).toUpperCase());
				else
					stmt.setString((i+1), cosmic.get(i));
			}
			//System.out.println(hm.get(cosmic.get(6)+","+cosmic.get(7)+","+cosmic.get(8)));
			stmt.setInt(238,hm.get(cosmic.get(6)+","+cosmic.get(7)+","+cosmic.get(8)));
			stmt.setString(239, flag);
			stmt.setInt(240, gas_mix);
			stmt.setInt(241, stack);
			int num = stmt.executeUpdate();
			try{stmt.close();}catch (Exception ex){}

	//		System.out.println(num);

		}
		cosmic.close();
		
	
	}
	catch(Exception nfe)
	{
		System.out.println(nfe.getLocalizedMessage());
	}

}

public  void updateRecords(File file, String flag, int gas_mix,int stack, int test_set){
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
		String query = "insert into re4_cosmic_table values (RE4_COSMIC_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		stmt = sql.con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		for (int i=0;i<cosmic.getColumnCount();i++){
	
				if(i==0)
					stmt.setInt((i+1), Integer.parseInt(cosmic.get(i)));
				else if(i==1)
//					stmt.setDate((i+1), convertStringToSqlDate(cosmic.get(i)));
				stmt.setTimestamp((i+1), convertStringToSqlDateTime(cosmic.get(i)));

				else if(i==2 || i==7 || i==8)
					stmt.setString((i+1), cosmic.get(i).toUpperCase());
				else
					stmt.setString((i+1), cosmic.get(i));
			}
			//System.out.println(hm.get(cosmic.get(6)+","+cosmic.get(7)+","+cosmic.get(8)));
			stmt.setInt(238,test_set);
			stmt.setString(239, flag);
			stmt.setInt(240, gas_mix);
			stmt.setInt(241, stack);
			int num = stmt.executeUpdate();
	//		System.out.println(num);
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
			SimpleDateFormat format4 = new SimpleDateFormat("dd.MM.yyyy HH:MM:SS");

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
					
				}
				else if (isValidDate(str_date, format4))
						{
							Date temp = (Date)format4.parse(str_date);  
							sqlDate = new java.sql.Date(temp.getTime());
							return sqlDate;
							
						}
				else if (isValidDate(str_date, format3))
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
public java.sql.Timestamp convertStringToSqlDateTime(String str_date){
	java.sql.Timestamp sqlDate=null ;
	
	try {  
	
		if(str_date==null || str_date == "")
			return sqlDate;
		else{
			
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat format3 = new SimpleDateFormat("dd.MM.yyyy");
			SimpleDateFormat format4 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

			if(isValidDate(str_date, format1))
			{
				Date temp = (Date)format1.parse(str_date);  
				sqlDate = new java.sql.Timestamp(temp.getTime());
			//	System.out.println("Format 1 = "+sqlDate);
				return sqlDate;
				
			}
				else if (isValidDate(str_date, format2))
				{
					Date temp = (Date)format2.parse(str_date);  
					sqlDate = new java.sql.Timestamp(temp.getTime());
				//	System.out.println("Format 2 = "+sqlDate);
					
					return sqlDate;
					
				}
				else if (isValidDate(str_date, format4))
						{
							Date temp = (Date)format4.parse(str_date);  
							sqlDate = new java.sql.Timestamp(temp.getTime());
					//		System.out.println("Format 4 = "+sqlDate);
							
							return sqlDate;
							
						}
				else if (isValidDate(str_date, format3))
				{
					Date temp = (Date)format3.parse(str_date);  
					sqlDate = new java.sql.Timestamp(temp.getTime());
	//				System.out.println("Format 3 = "+sqlDate);
					
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


public String getChamberIdinfile(){
	String chamber_id="";
	
	Set<String> s = new HashSet<String>(chamber_id_list);
	for (String elem : s) {
		chamber_id+=elem;
		chamber_id+=",";
    }
	chamber_id= chamber_id.substring(0,chamber_id.lastIndexOf(","));
	//System.out.println(chamber_id);
	
	return chamber_id;
}
public static void main (String agrs[] ) throws FileNotFoundException{
	
	  File file = new File("cosmic_template2.csv");
	  Controller cont = new Controller();

	  
	  SqlServerConnector sql = new SqlServerConnector();
	//  String tmpFolder = System.getProperty("java.io.tmpdir");
	//  System.out.println(tmpFolder);
	 // File tmpFile = new File(tmpFolder, "cosmic_template2.csv");
	  
	  
	  //cont.gasMixture();
	  cont.verifyFile(file);
	  //System.out.println(cont.checkDateFormat("300/12/2012"));
	   cont.getChamberIdinfile();
	//  cont.insertRecords(file,"GOOD", 2,10);
}
public static double Round(double Rval, int Rpl) {
	  double p = (double)Math.pow(10,Rpl);
	  Rval = Rval * p;
	  double tmp = Math.round(Rval);
	  return tmp/p;
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
