/**
 * Function: Handles adding to and checking date table 
 * Author: Aidan Rauscher 
 */
package roomscheduleraidanrauscherajr6536;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date; 


public class Dates {
    //REPURPOSED FROM GIVEN FACULTY CLASS
    //ADD addDates(FINAL)
    private static Connection connection;
    private static ArrayList<Date> dates = new ArrayList<Date>();
    private static PreparedStatement getDates;
    private static PreparedStatement addDate;
    private static ResultSet resultSet;
    
    
////////////////////////////////////////////////////////////////////////////////   
    //GETTER
        public static ArrayList<Date> getDates(){
        connection = DBConnection.getConnection();
        ArrayList<Date> dates = new ArrayList<Date>();
        try
        {
            getDates = connection.prepareStatement("select date from dates order by date");
            resultSet = getDates.executeQuery();
            
            while(resultSet.next())
            {
                dates.add(resultSet.getDate(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return dates;
    }
////////////////////////////////////////////////////////////////////////////////
    public static void addDate(Date date){
        connection = DBConnection.getConnection();
        try
        {
            addDate = connection.prepareStatement("insert into dates (date) values (?)");
            addDate.setDate(1, date);
            addDate.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
//////////////////////////////////////////////////////////////////////////////// 
}

