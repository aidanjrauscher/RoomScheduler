/**
 * Function: Handles additional wait list functionality.
 * Author: Aidan Rauscher 
 */
package roomscheduleraidanrauscherajr6536;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class WaitlistQueries {
//CODE FOR FIRST METHOD REPURPOSED FROM GIVEN FACULTY CLASS AND BUILT RESERVATIONQUERIES CLASS 
    private static Connection connection;
    private static ArrayList<WaitlistEntry> getWaitlistByDate = new ArrayList<WaitlistEntry>();
    private static PreparedStatement queryWaitlistByDate;
    private static PreparedStatement addWaitlistEntry; 
    private static PreparedStatement queryFacultyWLSeats;
    private static PreparedStatement queryFacultyWLDate;
    private static PreparedStatement removeWaitlist; 
    private static ResultSet resultSet;

////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<WaitlistEntry> getWaitlistByDate(Date date){
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> getWaitlistByDate = new ArrayList<WaitlistEntry>();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            //query reservations from dates in checkWLByDateCombo ComboBox
            Date myDate = date; 
            queryWaitlistByDate = connection.prepareStatement("select faculty, seats, timestamp from waitlist where date = ? order by timestamp");
            queryWaitlistByDate.setDate(1, myDate);
            resultSet = queryWaitlistByDate.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                WaitlistEntry currentWaitlist = new WaitlistEntry(resultSet.getString(1),
                        myDate, resultSet.getInt(2), resultSet.getTimestamp(3));
                getWaitlistByDate.add(currentWaitlist);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    return getWaitlistByDate;  
    }
    
////////////////////////////////////////////////////////////////////////////////
    //USED TO CHECK FACULTY WITH RESERVED ROOMS
    public static ArrayList<String> getListOfFacultyNames(ArrayList<ReservationEntry> reservations){
    ArrayList<String> faculty = new ArrayList<String>();
        for (int i=0; i < reservations.size(); i++){
        String currentFacultyName = reservations.get(i).getFacultyRes();
        faculty.add(currentFacultyName);
        }
    return faculty;
    }
    
////////////////////////////////////////////////////////////////////////////////
    //USED TO CHECK FACULTY ON WAITLIST
    public static ArrayList<String> getListOfFacultyNamesWL(ArrayList<WaitlistEntry> waitlist){
    ArrayList<String> faculty = new ArrayList<String>();
        for (int i=0; i < waitlist.size(); i++){
        String currentFacultyName = waitlist.get(i).getFacultyWL();
        faculty.add(currentFacultyName);
        }
    return faculty;
    }
////////////////////////////////////////////////////////////////////////////////
    public static void addWaitlistEntry(String faculty, Date date, int seats){
        //GIVEN CODE TO GET TIMESTAMP
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
               
       
        connection = DBConnection.getConnection();
            //REPURPOSED FROM GIVEN ADDFACULTY METHOD
            try
            {
                addWaitlistEntry = connection.prepareStatement("insert into waitlist (faculty, date, seats, timestamp) "
                        + "values (?, ?, ?,?)");
                addWaitlistEntry.setString(1, faculty);
                addWaitlistEntry.setDate(2, date);
                addWaitlistEntry.setInt(3, seats);
                addWaitlistEntry.setTimestamp(4, currentTimestamp);
                addWaitlistEntry.executeUpdate();
            }
             catch(SQLException sqlException)
             {
                 sqlException.printStackTrace();
             }
    }
////////////////////////////////////////////////////////////////////////////////
public static ArrayList<String> getFacultyWLSeats(String faculty){
        ArrayList<String> getFacultyWLSeats = new ArrayList<String>();
        connection = DBConnection.getConnection();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            queryFacultyWLSeats = connection.prepareStatement("select seats from waitlist where faculty = ? order by date");
            queryFacultyWLSeats.setString(1, faculty );
            resultSet = queryFacultyWLSeats.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                getFacultyWLSeats.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    return getFacultyWLSeats;  
    }   

////////////////////////////////////////////////////////////////////////////////
public static ArrayList<String> getFacultyWLDate(String faculty){
        ArrayList<String> getFacultyWLDate = new ArrayList<String>();
        connection = DBConnection.getConnection();
        //REPURPOSED FROM GIVEN FACULTY CLASS
        try
        {
            //ORDERING ROOMS BY SEATS MAKES IT EASIER TO FIND SMALLEST (I.E. FIRST) AVAILABLE ROOM
            queryFacultyWLDate = connection.prepareStatement("select date from waitlist where faculty = ? order by date");
            queryFacultyWLDate.setString(1, faculty );
            resultSet = queryFacultyWLDate.executeQuery();
            
            while(resultSet.next())
            {
                //CREATE NEW INSTACNE OF ROOMENTRY USING ROOM NAME AND SEATS FROM TABLE 
                getFacultyWLDate.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    return getFacultyWLDate;  
    } 
////////////////////////////////////////////////////////////////////////////////
public static void removeWaitlist(Date date){    
    connection = DBConnection.getConnection();
    try
        {
            removeWaitlist = connection.prepareStatement("delete from waitlist where date = ?");
            removeWaitlist.setDate(1, date );
            removeWaitlist.executeUpdate();
        }    
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
}
////////////////////////////////////////////////////////////////////////////////
public static ArrayList<WaitlistEntry> cancelWaitlist(Date date, String faculty){    
    ArrayList<WaitlistEntry> todaysWaitlist = getWaitlistByDate(date);
    removeWaitlist(date); 
    for (int i=0; i<todaysWaitlist.size(); i++){
        String currentFaculty = todaysWaitlist.get(i).getFacultyWL();
        if (currentFaculty.equals(faculty)){
            todaysWaitlist.remove(i);
        }
        
    }
     return todaysWaitlist;
}
////////////////////////////////////////////////////////////////////////////////
public static ArrayList<WaitlistEntry> pullWaitlist(Date date){
    ArrayList<WaitlistEntry> todaysWaitlist = getWaitlistByDate(date);
    removeWaitlist(date);
    return todaysWaitlist;
}
////////////////////////////////////////////////////////////////////////////////
}