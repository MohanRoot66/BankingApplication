package databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection provideConnection(){

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/banking?allowPublicKeyRetrieval=true&useSSL=false";

        try{
            con = DriverManager.getConnection(url,"root","Admin#123");
        }
        catch (Exception ex){
            System.out.println("Exception while connecting to DB");
        }

        return  con;
    }

}
