package manager.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
   安全的BaseDao工具类：自动管理连接关闭，防止连接泄漏。
   用完ResultSet后，在finally块同时关闭ResultSet、Statement和Connection。
 */
public class BaseDao {

    /*
       查询操作，返回ResultSet。
       调用方法在finally中关闭ResultSet、PreparedStatement和Connection。
     */
    public static ResultSet executeDQL(String sql, Object[] params) {
        Connection conn = DBConn.getConn();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    st.setObject(i + 1, params[i]);
                }
            }
            rs = st.executeQuery();


            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            // 发生异常时应该关闭连接
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
            try { if (st != null) st.close(); } catch (Exception ignore) {}
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
            return null;
        }
    }

    /*
       增删改操作，自动关闭所有JDBC资源。
     */
    public static int executeDML(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement st = null;
        int r = 0;

        try {
            conn = DBConn.getConn();
            st = conn.prepareStatement(sql);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    st.setObject(i + 1, params[i]);
                }
            }
            r = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (st != null) st.close(); } catch (Exception ignore) {}
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
        }
        return r;
    }
}