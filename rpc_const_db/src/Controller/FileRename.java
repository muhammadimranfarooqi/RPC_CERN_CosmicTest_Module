package Controller;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;
import java.io.*;
import java.util.*;
public class FileRename implements FileRenamePolicy {

	
		 
		 //implement the rename(File f) method to satisfy the 
		    // FileRenamePolicy interface contract
		   
	public File rename(File f){
		    
	//Get the parent directory path as in h:/home/user or /home/user
		        
				String parentDir = f.getParent( );
		      
		        //Get filename without its path location, such as 'index.txt'
		        String fname = f.getName( );
		      
		        
		       String a =  fname.replace(" ", "");
		        
		        //Get the extension if the file has one
		        String fileExt = "";
		        int i = -1;
		        if(( i = fname.indexOf(".")) != -1){
		      
		            fileExt = fname.substring(i);
		            fname = fname.substring(0,i);
		        }
		      
		  //      Calendar cal = Calendar.getInstance();
		        
		     

		        //fname = fname.replace(" ", "")+ (""+cal.getTime());
		        //add the timestamp
		        
		        fname = fname.replace(" ", "")+ (""+( new Date( ).getTime( ) / 1000));
		      
		        //piece together the filename
		        fname = parentDir + System.getProperty(
		            "file.separator") + fname + fileExt;
		      
		        
		        
		        
		        File temp = new File(fname);
		 
		         return temp;

	 }


	
}
