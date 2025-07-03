package manager.frame;

import manager.dao.UserDao;
import manager.frame.MainFrame;
import manager.pojo.Users;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordPanel extends JInternalFrame {
    public ChangePasswordPanel() {
        super("修改密码", true, true, true, true);
        this.setSize(400, 260);
        this.setVisible(true);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);

        JLabel lblOld = new JLabel("原密码：");
        lblOld.setBounds(60, 30, 80, 30);
        panel.add(lblOld);

        JPasswordField txtOld = new JPasswordField();
        txtOld.setBounds(140, 30, 180, 30);
        panel.add(txtOld);

        JLabel lblNew = new JLabel("新密码：");
        lblNew.setBounds(60, 80, 80, 30);
        panel.add(lblNew);

        JPasswordField txtNew = new JPasswordField();
        txtNew.setBounds(140, 80, 180, 30);
        panel.add(txtNew);

        JLabel lblConfirm = new JLabel("确认新密码：");
        lblConfirm.setBounds(30, 130, 100, 30);
        panel.add(lblConfirm);

        JPasswordField txtConfirm = new JPasswordField();
        txtConfirm.setBounds(140, 130, 180, 30);
        panel.add(txtConfirm);

        JButton btnChange = new JButton("修改密码");
        btnChange.setBounds(140, 180, 120, 35);
        panel.add(btnChange);

        JLabel lblMsg = new JLabel("");
        lblMsg.setForeground(Color.RED);
        lblMsg.setBounds(60, 170, 320, 20);
        panel.add(lblMsg);

        btnChange.addActionListener(e -> {
            String oldPwd = new String(txtOld.getPassword()).trim();
            String newPwd = new String(txtNew.getPassword()).trim();
            String confirmPwd = new String(txtConfirm.getPassword()).trim();

            if (oldPwd.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty()) {
                lblMsg.setText("所有字段都不能为空！");
                return;
            }
            if (!newPwd.equals(confirmPwd)) {
                lblMsg.setText("两次输入的新密码不一致！");
                return;
            }
            Users user = MainFrame.users;
            if (!user.getPassword().equals(oldPwd)) {
                lblMsg.setText("原密码错误！");
                return;
            }
            boolean result = UserDao.resetPassword(user.getUsername(), newPwd);
            if (result) {
                user.setPassword(newPwd);
                JOptionPane.showMessageDialog(this, "密码修改成功！");
                this.dispose(); // 关闭修改密码窗口
            } else {
                lblMsg.setText("密码修改失败！");
            }
        });
    }
}