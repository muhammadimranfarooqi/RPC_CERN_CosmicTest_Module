package Database;

import java.sql.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.util.Properties;
import javax.swing.JOptionPane;





public class SqlServerConnector {
       
        Properties prop = new Properties();
        OutputStream output = null;
        InputStream input = null;
       
       

//      public static String dbname = "rpct-masking";
//      public static String username = "hr";
//      public static String passwd = "test";
       
        public static Connection con = null;
       
         public SqlServerConnector() {
                 
                   
        try {
               // input = new FileInputStream("/config.properties");
               
        		input  = getClass().getResourceAsStream("config.properties");
                System.out.print("Test"+input);

        		
        		
                //setProperties("cms_rpc_constr", "RPCConDB_2012");
        		 prop.load( input );
                Class.forName("oracle.jdbc.driver.OracleDriver");
               
                
                String password = prop.getProperty("password");
                String username = prop.getProperty("username");
                System.out.println("Username"+username);
                System.out.println("password"+password);
               
                //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","constructiondb","imran@ncp");
        //      con=DriverManager.getConnection("jdbc:oracle:thin:@111.68.96.179:1521:xe","constructiondb","imran@ncp");
                //con=DriverManager.getConnection("jdbc:oracle:thin:@cmsr1-v.cern.ch:10121:cmsr_lb.cern.ch","cms_rpc_constr","RPCConDB_2012");
                //con=DriverManager.getConnection("jdbc:oracle:thin:@//cmsr2-v.cern.ch:10121/cmsr_lb.cern.ch","cms_rpc_constr","RPCConDB_2012");
               
                con=DriverManager.getConnection("jdbc:oracle:thin:@( DESCRIPTION= (ADDRESS= (PROTOCOL=TCP) (HOST=cmsr1-s.cern.ch) (PORT=10121)) (ADDRESS= (PROTOCOL=TCP) (HOST=cmsr2-s.cern.ch) (PORT=10121)) (ADDRESS= (PROTOCOL=TCP) (HOST=cmsr3-s.cern.ch) (PORT=10121)) (LOAD_BALANCE=on) (ENABLE=BROKEN) (CONNECT_DATA= (SERVER=DEDICATED) (SERVICE_NAME=cmsr_lb.cern.ch)))",username,password);
               
//              con = DriverManager.getConnection(connectionUrl);
               
                //System.out.print(con);
        }
       catch(Exception ce){

           System.out.println( ce.getMessage());
           }
     
   }
         
 public  void setProperties(String username, String password) {
                 
                 try {
                         
                                output = new FileOutputStream("config.properties");
                 
                                // set the properties value
                                //prop.setProperty("username", "cms_rpc_constr");
                                //prop.setProperty("password", "RPCConDB_2012");
                 
                                prop.setProperty("username", username);
                                prop.setProperty("password", password);
                 
                               
                                // save properties to project root folder
                                prop.store(output, null);
                 
                        } catch (IOException io) {
                                io.printStackTrace();
                        } finally {
                                if (output != null) {
                                        try {
                                                output.close();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                 
                        }
                       
 }
   public static void main (String arg[]){
           try {
           SqlServerConnector sql = new SqlServerConnector();
           
           System.out.println(" SQL Connection String:::::"+ sql.con);
           }
           catch (Exception e){
                   System.out.println("ejb "+ e.getMessage());  
           }
   }
   
  }
 


