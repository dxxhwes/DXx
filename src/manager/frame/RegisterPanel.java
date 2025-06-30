package manager.frame;

import manager.dao.UserDao;
import manager.pojo.user;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    public RegisterPanel() {
        setLayout(null);

        JLabel title = new JLabel("用户注册");
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setBounds(210, 30, 200, 40);
        add(title);

        JLabel lblUser = new JLabel("用户名：");
        lblUser.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblUser.setBounds(150, 100, 80, 30);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtUser.setBounds(230, 100, 200, 30);
        add(txtUser);

        JLabel lblPwd = new JLabel("密码：");
        lblPwd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblPwd.setBounds(150, 150, 80, 30);
        add(lblPwd);

        JPasswordField txtPwd = new JPasswordField();
        txtPwd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtPwd.setBounds(230, 150, 200, 30);
        add(txtPwd);

        JLabel lblName = new JLabel("姓名：");
        lblName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblName.setBounds(150, 200, 80, 30);
        add(lblName);

        JTextField txtName = new JTextField();
        txtName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtName.setBounds(230, 200, 200, 30);
        add(txtName);

        JLabel lblTel = new JLabel("电话：");
        lblTel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblTel.setBounds(150, 250, 80, 30);
        add(lblTel);

        JTextField txtTel = new JTextField();
        txtTel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtTel.setBounds(230, 250, 200, 30);
        add(txtTel);

        JLabel lblBir = new JLabel("生日：");
        lblBir.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblBir.setBounds(150, 300, 80, 30);
        add(lblBir);

        JTextField txtBir = new JTextField("yyyy-mm-dd");
        txtBir.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtBir.setBounds(230, 300, 200, 30);
        add(txtBir);

        JButton btnRegister = new JButton("注册");
        btnRegister.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnRegister.setBounds(230, 360, 90, 35);
        btnRegister.setBackground(new Color(37, 174, 96));
        btnRegister.setForeground(Color.WHITE);

        JButton btnBack = new JButton("返回登录");
        btnBack.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnBack.setBounds(340, 360, 120, 35);
        btnBack.setBackground(new Color(66, 133, 244));
        btnBack.setForeground(Color.WHITE);

        add(btnRegister);
        add(btnBack);

        JLabel lblMsg = new JLabel("");
        lblMsg.setForeground(Color.RED);
        lblMsg.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        lblMsg.setBounds(230, 410, 230, 30);
        add(lblMsg);

        // 注册按钮事件
        btnRegister.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = new String(txtPwd.getPassword()).trim();
            String uname = txtName.getText().trim();
            String tel = txtTel.getText().trim();
            String bir = txtBir.getText().trim();

            if (username.isEmpty() || password.isEmpty() || uname.isEmpty() || tel.isEmpty() || bir.isEmpty()) {
                lblMsg.setText("请填写所有字段！");
                return;
            }
            // 用户名唯一性校验
            if (UserDao.isUsernameExists(username)) {
                lblMsg.setText("该用户名已存在！");
                return;
            }

            user user = new user();
            user.setUsername(username);
            user.setPassword(password);
            user.setUname(uname);
            user.setTel(tel);
            try {
                user.setBir(java.sql.Date.valueOf(bir));
            } catch (Exception ex) {
                lblMsg.setText("生日格式错误！");
                return;
            }

            boolean result = UserDao.register(user);
            if (result) {
                JOptionPane.showMessageDialog(this, "注册成功！请返回登录。");
                manager.frame.MainFrame.setContent(new LoginPanel());
            } else {
                lblMsg.setText("注册失败，请重试！");
            }
        });

        // 返回登录事件
        btnBack.addActionListener(e -> {
            manager.frame.MainFrame.setContent(new LoginPanel());
        });
    }
}