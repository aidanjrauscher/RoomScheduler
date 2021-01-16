/**
 * Function: Handles room SQL entries. 
 * Author: Aidan Rauscher 
 */
package roomscheduleraidanrauscherajr6536;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;


////////////////////////////////////////////////////////////////////////////////
public class RoomEntry {
    private String roomName;
    private int roomSeats; 
    
    //CONSTRUCTOR
    public RoomEntry(String name, int seats){
        this.roomName = name;
        this.roomSeats = seats;
    }

////////////////////////////////////////////////////////////////////////////////
    //GETTERS
    public String getRoomName(){
        return this.roomName;
    }
    
    public int getRoomSeats(){
        return this.roomSeats; 
    }
////////////////////////////////////////////////////////////////////////////////    
}
    