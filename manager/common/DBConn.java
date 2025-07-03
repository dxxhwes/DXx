package manager.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * 数据库连接工具类
 * 负责加载数据库驱动，提供获取数据库连接的方法
 */

public class DBConn
{

    private static String url="jdbc:mysql://localhost:3306/expboot?serverTimezone=UTC";
    private static String username="root";
    private static String pass="20060303dx";

    // 静态代码块：程序启动时加载JDBC驱动
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //获取数据库连接
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

