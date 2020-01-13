package zipcode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class ZipCode {
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException 
	{
	String path=ZipCode.class.getResource("ChIzipcode.csv").getFile();
	
	//System.out.print(path);
	Connection conn;
    String url = "jdbc:oracle:thin:@acadoradbprd01.dpu.depaul.edu:1521:ACADPRD0";
   
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
    try
	{
    	
    	// Establishing sql connection set up 
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    conn = DriverManager.getConnection(url, "AABDIKAD", "cdm1800859");
	    Statement st = conn.createStatement();
	    
	    

        try {

            br = new BufferedReader(new FileReader(path));
            line=line = br.readLine();
            while ((line = br.readLine()) != null) {
           

                // use comma as separator
                String[] locations = line.split(cvsSplitBy); // this array holds rows from csv file 
                String upd = "INSERT INTO zipcode VALUES ("
                		+locations[0].replace("\"", "\'") +","
                		+locations[1].replace("\"", "\'") +","
                		+locations[2].replace("\"", "\'") +","
                		+locations[3].replace("\"", "") +","
                		+locations[4].replace("\"", "") +","
                		+locations[5].replace("\"", "") +","
                		+locations[6].replace("\"", "") +")";
                System.out.println("Inserting to table ZIPCODE");
               
                st.execute(upd);

               
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	    
        
        /// Printing name, zipcode, latitude, longitude 
	    
	  String joinQuerry= "select res.name, loc.ZIPCODE, zip.LATITUDE, zip.LONGITUDE  "
	  		+ "from restaurant res, restaurant_locations loc, zipcode zip 			 "
	  		+ " where res.RID=loc.RID and loc.ZIPCODE=zip.ZIP";
	  ResultSet rs = st.executeQuery(joinQuerry);
	  while (rs.next())
		{
		  String columnValue="";
          for (int i=1; i<4; i++) {
        	  columnValue = columnValue+rs.getString(i)+ ", ";
          }
          columnValue = columnValue+rs.getString(4);
          System.out.println("");
          System.out.println(columnValue );
		} 
	  
	    
	    
	    conn.close();
	}
    catch (ClassNotFoundException ex) {System.err.println("Class not found " + ex.getMessage());}
    catch (SQLException ex )          {System.err.println(ex.getMessage());
    }
    }


	
	
	
}
