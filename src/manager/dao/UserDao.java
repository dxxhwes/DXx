package manager.dao;

import manager.common.BaseDao;
import manager.pojo.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class UserDao
{

    /**
     * 按用户名和角色查找用户（原有功能）
     */
    public Users getUserByName(String userName, int roleId)
    {
        Users users = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t1.* ");
        sql.append("from users t1, user_role t2 ");
        sql.append("where t1.user_id = t2.user_id and t1.username = ? and t2.role_id = ?");

        Object[] obj = {userName, roleId};

        ResultSet rs = BaseDao.executeDQL(sql.toString(), obj);
        try {
            while (rs.next()) {
                users = new Users(
                        rs.getInt("user_id"),
                        rs.getDate("bir"),
                        rs.getString("tel"),
                        rs.getString("sex"),
                        rs.getString("uname"),
                        rs.getString("password"),
                        rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * 判断用户名是否已存在（注册用）
     */
    public static boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username=?";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{username});
        try {
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 注册新用户
     */
    public static boolean register(Users user) {
        String sql = "INSERT INTO users (username, password, uname, tel, bir) VALUES (?, ?, ?, ?, ?)";
        int result = BaseDao.executeDML(sql, new Object[]{
                user.getUsername(), user.getPassword(), user.getUname(), user.getTel(), user.getBir()
        });
        return result > 0;
    }

    /**
     * 找回密码（通过用户名和电话重置密码）
     */
    public static boolean resetPassword(String username, String tel, String newPwd) {
        // 校验用户名和电话
        String querySql = "SELECT 1 FROM users WHERE username=? AND tel=?";
        ResultSet rs = BaseDao.executeDQL(querySql, new Object[]{username, tel});
        try {
            if (rs.next()) {
                String updateSql = "UPDATE users SET password=? WHERE username=? AND tel=?";
                int result = BaseDao.executeDML(updateSql, new Object[]{newPwd, username, tel});
                return result > 0;
            } else {
                return false; // 用户名或电话错误
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 可选：通过用户名查找用户（用于注册成功后自动登录或其它用途）
     */
    public static Users getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{username});
        try {
            if (rs.next()) {
                return new Users(
                        rs.getInt("user_id"),
                        rs.getDate("bir"),
                        rs.getString("tel"),
                        rs.getString("sex"),
                        rs.getString("uname"),
                        rs.getString("password"),
                        rs.getString("username")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
