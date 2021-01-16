/**
 * Function: Handles additional room functionality.
 * Author: Aidan Rauscher 
 */
package roomscheduleraidanrauscherajr6536;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class RoomQueries {
    private static Connection connection;
    private static ArrayList<RoomEntry> getAllPossibleRooms = new ArrayList<RoomEntry>();
    private static PreparedStatement queryAllPossibleRooms;
    private static PreparedStatement removeRoom;
    private static PreparedStatement addRoom;
    private static ResultSet resultSet;

////////////////////////////////////////////////////////////////////////////////    
    public static ArrayList<RoomEntry> getAllPossibleRooms(){
        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> getAllPossibleRooms = new ArrayList<RoomEntry>();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            queryAllPossibleRooms = connection.prepareStatement("select room,seats from rooms order by seats, room asc");
            resultSet = queryAllPossibleRooms.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                RoomEntry currentRoom = new RoomEntry(resultSet.getString(1), resultSet.getInt(2));
                getAllPossibleRooms.add(currentRoom);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    return getAllPossibleRooms;
        
    }
////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<String> getAllPossibleRoomNames(){
        ArrayList<String> roomNames = new ArrayList<String>();
        ArrayList<RoomEntry> rooms = getAllPossibleRooms();
        for (int i=0; i<rooms.size(); i++){
            roomNames.add(rooms.get(i).getRoomName());
        }
        return roomNames;
    }
////////////////////////////////////////////////////////////////////////////////
    public static void dropRoom(String room){
        connection = DBConnection.getConnection();
    try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            removeRoom = connection.prepareStatement("delete from rooms where room = ?");
            removeRoom.setString(1, room);
            removeRoom.executeUpdate();
        }    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }      
        
    }

////////////////////////////////////////////////////////////////////////////////    
    public static void addRoom(String room, int seats){
        connection = DBConnection.getConnection();
    try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            addRoom = connection.prepareStatement("insert into rooms (room,seats) values (?,?)");
            addRoom.setString(1, room);
            addRoom.setInt(2, seats);
            addRoom.executeUpdate();
        }    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
    }
////////////////////////////////////////////////////////////////////////////////
}
  