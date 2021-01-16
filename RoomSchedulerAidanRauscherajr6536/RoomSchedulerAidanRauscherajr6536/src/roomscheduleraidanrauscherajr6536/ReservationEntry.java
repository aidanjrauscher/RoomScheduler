/**
 * Function: Handles reservation SQL entries. 
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


public class ReservationEntry {

    private String facultyRes;
    private String roomNameRes; 
    private Date dateRes; 
    private int roomSeatsRes; 
    private Timestamp timestampRes; 
    
////////////////////////////////////////////////////////////////////////////////
    //CONSTRCUTOR
    public ReservationEntry(String faculty, String room, Date date, int seats, Timestamp timestamp){
        this.facultyRes = faculty; 
        this.roomNameRes = room;
        this.dateRes = date;
        this.roomSeatsRes = seats;
        this.timestampRes = timestamp; 
    }
////////////////////////////////////////////////////////////////////////////////    
    //GETTERS 
    public String getFacultyRes(){
        return this.facultyRes;
    }
    
    public String getRoomNameRes(){
        return this.roomNameRes;
    }
    
    public Date getDateRes(){
        return this.dateRes;
    }
    
    public int getRoomSeatsRes(){
        return this.roomSeatsRes;
    }
    
    public Timestamp getTimestampRes(){
        return this.timestampRes;
    }
////////////////////////////////////////////////////////////////////////////////
}   
    
  
    

