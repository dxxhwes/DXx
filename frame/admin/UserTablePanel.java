package manager.frame.admin;

import manager.common.BaseDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UserTablePanel extends JInternalFrame {
    public UserTablePanel(int roleId) {
        super(roleId == 1 ? "管理员列表" : "用户列表", true, true, true, true);
        this.setSize(700, 400);
        this.setVisible(true);

        String[] columns = {"用户ID", "账号", "姓名", "性别", "电话", "生日"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // 查询所有指定角色的用户
        String sql = "SELECT u.user_id, u.username, u.uname, u.sex, u.tel, u.bir " +
                "FROM users u JOIN user_role ur ON u.user_id=ur.user_id WHERE ur.role_id = ?";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{roleId});
        try {
            while (rs != null && rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("user_id"));
                row.add(rs.getString("username"));
                row.add(rs.getString("uname"));
                row.add(rs.getString("sex"));
                row.add(rs.getString("tel"));
                row.add(rs.getDate("bir"));
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}