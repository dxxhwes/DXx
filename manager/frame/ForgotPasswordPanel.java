package manager.frame;

import manager.dao.UserDao;

import javax.swing.*;
import java.awt.*;

public class ForgotPasswordPanel extends JPanel {
    public ForgotPasswordPanel() {
        setLayout(null);

        JLabel title = new JLabel("找回密码");
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setBounds(220, 30, 200, 40);
        add(title);

        JLabel lblUser = new JLabel("账号：");
        lblUser.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblUser.setBounds(150, 100, 80, 30);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtUser.setBounds(230, 100, 200, 30);
        add(txtUser);

        JLabel lblPwd = new JLabel("新密码：");
        lblPwd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblPwd.setBounds(150, 160, 80, 30);
        add(lblPwd);

        JPasswordField txtPwd = new JPasswordField();
        txtPwd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtPwd.setBounds(230, 160, 200, 30);
        add(txtPwd);

        JButton btnReset = new JButton("重置密码");
        btnReset.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnReset.setBounds(230, 220, 120, 35);
        btnReset.setBackground(new Color(247, 181, 0));
        btnReset.setForeground(Color.WHITE);

        JButton btnBack = new JButton("返回登录");
        btnBack.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnBack.setBounds(360, 220, 120, 35);
        btnBack.setBackground(new Color(66, 133, 244));
        btnBack.setForeground(Color.WHITE);

        add(btnReset);
        add(btnBack);

        JLabel lblMsg = new JLabel("");
        lblMsg.setForeground(Color.RED);
        lblMsg.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        lblMsg.setBounds(230, 270, 250, 30);
        add(lblMsg);

        // 重置密码事件
        btnReset.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String newPwd = new String(txtPwd.getPassword()).trim();

            if (username.isEmpty() || newPwd.isEmpty()) {
                lblMsg.setText("账号和新密码不能为空！");
                return;
            }
            boolean result = UserDao.resetPassword(username, newPwd);
            if (result) {
                JOptionPane.showMessageDialog(this, "密码重置成功，请返回登录！");
                manager.frame.MainFrame.setContent(new LoginPanel());
            } else {
                lblMsg.setText("账号不存在或重置失败！");
            }
        });

        // 返回登录事件
        btnBack.addActionListener(e -> {
            manager.frame.MainFrame.setContent(new LoginPanel());
        });
    }
}