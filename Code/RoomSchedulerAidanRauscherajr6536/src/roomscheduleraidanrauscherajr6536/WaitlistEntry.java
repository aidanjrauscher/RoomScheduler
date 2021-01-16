/**
 * Function: Handles SQL wait list entries. 
 * Author: Aidan Rauscher 
 */
package roomscheduleraidanrauscherajr6536;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;


public class WaitlistEntry {
    
    private String facultyWL; 
    private Date dateWL; 
    private int roomSeatsWL; 
    private Timestamp timestampWL; 
    
////////////////////////////////////////////////////////////////////////////////
    //CONSTRCUTOR
    public WaitlistEntry(String faculty, Date date, int seats, Timestamp timestamp){
        this.facultyWL = faculty; 
        this.dateWL = date;
        this.roomSeatsWL = seats;
        this.timestampWL = timestamp; 
    }
////////////////////////////////////////////////////////////////////////////////   
    //GETTERS 
    public String getFacultyWL(){
        return this.facultyWL;
    }
    
    public Date getDateWL(){
        return this.dateWL;
    }
    
    public int getRoomSeatsWL(){
        return this.roomSeatsWL;
    }
    
    public Timestamp getTimestampWL(){
        return this.timestampWL;
    }
////////////////////////////////////////////////////////////////////////////////
}   
    
  

