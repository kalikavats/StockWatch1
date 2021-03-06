package edu.stock;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DataConnect{

   // public static String hostName = "localhost";
    //public static String portNumber = "3306";
   // public static String databaseName = "userdb_userdetails";
    //public static String userName = "root";
    //public static String password = "root";

   public static String hostName = System.getenv("ICSI518_SERVER");
   public static String portNumber = System.getenv("ICSI518_PORT");
   public static String databaseName = System.getenv("ICSI518_DB");
   public static String userName = System.getenv("ICSI518_USER");
   public static String password = System.getenv("ICSI518_PASSWORD");
    
    
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + hostName + ":" + portNumber + "/" + databaseName, userName, password);
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
