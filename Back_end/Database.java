package Back_end;

import java.sql.Connection;
import java.sql.DriverManager;
//This is the database class it handles the connections of the SQL to the java program.
public class Database {
    private static final String url = "jdbc:mysql://localhost:3306/new_pares";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() throws Exception{
        try{
            return DriverManager.getConnection(url, user, password);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}