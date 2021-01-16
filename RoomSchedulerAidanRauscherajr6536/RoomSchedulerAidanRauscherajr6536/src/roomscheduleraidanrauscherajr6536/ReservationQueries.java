/**
 * Function: Handles additional reservation functionality. 
 * Author: Aidan Rauscher 
 */
package roomscheduleraidanrauscherajr6536;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Calendar; 
import java.text.DateFormat;
import java.util.Arrays;


public class ReservationQueries {
    //CODE FOR FIRST TWO METHODS REPURPOSED FROM GIVEN FACULTY CLASS 
    private static Connection connection;
    private static PreparedStatement queryReservationsByDate;
    private static PreparedStatement queryRoomsReservedByDate;
    private static PreparedStatement queryNewestReservation;
    private static PreparedStatement addReservationEntry; 
    private static PreparedStatement queryFacultyByDate;
    private static PreparedStatement removeReservation;
    private static ResultSet resultSet;

////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<ReservationEntry> getReservationsByDate(Date date){
        ArrayList<ReservationEntry> getReservationsByDate = new ArrayList<ReservationEntry>();
        connection = DBConnection.getConnection();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            Date myDate = date;
            queryReservationsByDate = connection.prepareStatement("select faculty, room, seats, timestamp from reservations where date = ?");
            queryReservationsByDate.setDate(1, myDate );
            resultSet = queryReservationsByDate.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                ReservationEntry currentReservations = new ReservationEntry(resultSet.getString(1), resultSet.getString(2),
                        myDate, resultSet.getInt(3), resultSet.getTimestamp(4));
                getReservationsByDate.add(currentReservations);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    return getReservationsByDate;  
    }
    
////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<RoomEntry> getRoomsReservedByDate(Date thisDate){
        ArrayList<RoomEntry> roomsReservedByDate = new ArrayList<RoomEntry>(); 
        connection = DBConnection.getConnection();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            Date myDate = thisDate; 
            queryRoomsReservedByDate = connection.prepareStatement("select room, seats from reservations where date = ?");
            queryRoomsReservedByDate.setDate(1, myDate);
            resultSet = queryRoomsReservedByDate.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                RoomEntry currentRoom = new RoomEntry(resultSet.getString(1), resultSet.getInt(2));
                roomsReservedByDate.add(currentRoom);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    return roomsReservedByDate;  
    }
    
////////////////////////////////////////////////////////////////////////////////
    //THIS METHOD GETS THE NAME OF EVERY ROOMENTRY IN AN ARRAY OF INSTANCES 
    public static ArrayList<String> getListOfRoomNames(ArrayList<RoomEntry> rooms){
        ArrayList<String> names = new ArrayList<String>();
        for (int i=0; i <rooms.size(); i++){
            String currentRoomName = rooms.get(i).getRoomName();
            names.add(currentRoomName);
        }
    return names;
    }
////////////////////////////////////////////////////////////////////////////////  
public static String getNewestRes(Date date){
    ArrayList<String> roomNames = new ArrayList<String>(); 
            try
        {
            queryNewestReservation = connection.prepareStatement("select room from reservations where date = ? order by timestamp desc");
            queryNewestReservation.setDate(1,date);
            resultSet = queryNewestReservation.executeQuery();
            
            while(resultSet.next())
            {
                roomNames.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
   
    return roomNames.get(0);    
    }
    

////////////////////////////////////////////////////////////////////////////////
    public static boolean addReservationEntry(String faculty, Date date, int seats){
        boolean successfulReservation = false;
        //GIVEN CODE TO GET TIMESTAMP
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        
        ArrayList<RoomEntry> allAvailableRooms = RoomQueries.getAllPossibleRooms();
        connection = DBConnection.getConnection();
        //CHECK FOR AVAILABLE RESERVATION
       
        for (int i = 0; i < allAvailableRooms.size(); i++){
            //GET ROOM INFO FOR ITH AVAILABLE ROOM
            RoomEntry currentRoom = allAvailableRooms.get(i);
            String currentRoomName = currentRoom.getRoomName();
            int currentRoomSeats = currentRoom.getRoomSeats();
            //CHECKS IF ROOM IS BIG ENOUGH 
            ArrayList<RoomEntry> unavailableRooms = getRoomsReservedByDate(date);
            ArrayList<String> unavailableRoomNames = getListOfRoomNames(unavailableRooms); 
            //SMALLEST ROOM WILL AUTOMATICALLY BE CHECKED FIRST BECAUSE OF SQL QUERY
            if (currentRoomSeats >= seats && (unavailableRoomNames.contains(currentRoomName)==false)){
                    
                    try
                    {  
                        successfulReservation = true;                     
                        addReservationEntry = connection.prepareStatement("insert into reservations (faculty, room, date, seats, timestamp) values (?, ?, ?, ? ,?)");
                        addReservationEntry.setString(1, faculty);
                        addReservationEntry.setString(2, currentRoomName);
                        addReservationEntry.setDate(3, date);
                        addReservationEntry.setInt(4, currentRoomSeats);
                        addReservationEntry.setTimestamp(5, currentTimestamp);
                        addReservationEntry.executeUpdate(); 
                        return successfulReservation;  
                    }
                     catch(SQLException sqlException)
                     {
                        sqlException.printStackTrace();
                        return successfulReservation;  
                    }
                }
            }  
        return successfulReservation; 
        }
        
    
    
////////////////////////////////////////////////////////////////////////////////
public static ArrayList<String> getFacultyRoom(String faculty){
        ArrayList<String> getFacultyRoom = new ArrayList<String>();
        connection = DBConnection.getConnection();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            queryReservationsByDate = connection.prepareStatement("select room from reservations where faculty = ? order by date");
            queryReservationsByDate.setString(1, faculty );
            resultSet = queryReservationsByDate.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                getFacultyRoom.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    return getFacultyRoom;  
    }   

////////////////////////////////////////////////////////////////////////////////
public static ArrayList<Date> getFacultyDate(String faculty){
        ArrayList<Date> getFacultyDate = new ArrayList<Date>();
        connection = DBConnection.getConnection();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            queryFacultyByDate = connection.prepareStatement("select date from reservations where faculty = ? order by date");
            queryFacultyByDate.setString(1, faculty );
            resultSet = queryFacultyByDate.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                getFacultyDate.add(resultSet.getDate(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    return getFacultyDate;  
    } 
////////////////////////////////////////////////////////////////////////////////
public static void removeReservations(Date date){    
    connection = DBConnection.getConnection();
    try
        {

            removeReservation = connection.prepareStatement("delete from reservations where date = ?");
            removeReservation.setDate(1, date);
            removeReservation.executeUpdate();
        }    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
}
////////////////////////////////////////////////////////////////////////////////
public static ArrayList<ReservationEntry> cancelReservation(Date date, String faculty){
    ArrayList<ReservationEntry> todaysReservations = getReservationsByDate(date);
    removeReservations(date);
    for (int i=0; i<todaysReservations.size(); i++){
        String currentFaculty = todaysReservations.get(i).getFacultyRes();
        if (currentFaculty.equals(faculty)){
            String name = todaysReservations.get(i).getFacultyRes();
            todaysReservations.remove(i);
        }
        
    }
     return todaysReservations;
}
////////////////////////////////////////////////////////////////////////////////
public static ArrayList<ReservationEntry> pullReservation(Date date){
    ArrayList<ReservationEntry> todaysReservations = getReservationsByDate(date);
    removeReservations(date);
    return todaysReservations;
}
////////////////////////////////////////////////////////////////////////////////
}


