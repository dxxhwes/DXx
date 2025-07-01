package manager.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{

   private static String url="jdbc:mysql://localhost:3306/expboot?serverTimezone=UTC";
   private static String username="root";
   private static String pass="20060303dx";

   static{
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
   }


   public static Connection getConn()
   {
       Connection conn=null;
       try {
           conn=DriverManager.getConnection(url,username,pass);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return conn;
   }


}
