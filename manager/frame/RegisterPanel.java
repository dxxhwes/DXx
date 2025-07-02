package manager.frame;

import manager.dao.UserDao;
import manager.pojo.Users;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    public RegisterPanel() {
        setLayout(null);

        JLabel title = new JLabel("用户注册");
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setBounds(220, 30, 200, 40);
        add(title);

        JLabel lblUser = new JLabel("账号：");
        lblUser.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblUser.setBounds(150, 110, 80, 30);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtUser.setBounds(230, 110, 200, 30);
        add(txtUser);

        JLabel lblPwd = new JLabel("密码：");
        lblPwd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblPwd.setBounds(150, 170, 80, 30);
        add(lblPwd);

        JPasswordField txtPwd = new JPasswordField();
        txtPwd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtPwd.setBounds(230, 170, 200, 30);
        add(txtPwd);

        JButton btnReg = new JButton("注册");
        btnReg.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnReg.setBounds(230, 230, 120, 35);
        btnReg.setBackground(new Color(66, 133, 244));
        btnReg.setForeground(Color.WHITE);
        add(btnReg);

        JButton btnBack = new JButton("返回登录");
        btnBack.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnBack.setBounds(360, 230, 120, 35);
        btnBack.setBackground(new Color(247, 181, 0));
        btnBack.setForeground(Color.WHITE);
        add(btnBack);

        JLabel lblMsg = new JLabel("");
        lblMsg.setForeground(Color.RED);
        lblMsg.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        lblMsg.setBounds(230, 280, 240, 30);
        add(lblMsg);

        btnReg.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = new String(txtPwd.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                lblMsg.setText("账号和密码不能为空！");
                return;
            }
            if (UserDao.isUsernameExists(username)) {
                lblMsg.setText("账号已存在！");
                return;
            }
            // 只传账号和密码，其他字段设为null或默认
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(password);

            boolean result = UserDao.register(user);
            if (result) {
                JOptionPane.showMessageDialog(this, "注册成功，请登录！");
                manager.frame.MainFrame.setContent(new LoginPanel());
            } else {
                lblMsg.setText("注册失败，请重试！");
            }
        });

        btnBack.addActionListener(e -> {
            manager.frame.MainFrame.setContent(new LoginPanel());
        });
    }
}