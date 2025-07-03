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
        lblUser.setBounds(150, 80, 80, 30);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtUser.setBounds(230, 80, 200, 30);
        add(txtUser);

        JLabel lblPwd = new JLabel("密码：");
        lblPwd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblPwd.setBounds(150, 120, 80, 30);
        add(lblPwd);

        JPasswordField txtPwd = new JPasswordField();
        txtPwd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtPwd.setBounds(230, 120, 200, 30);
        add(txtPwd);

        JLabel lblName = new JLabel("姓名：");
        lblName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblName.setBounds(150, 160, 80, 30);
        add(lblName);

        JTextField txtName = new JTextField();
        txtName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtName.setBounds(230, 160, 200, 30);
        add(txtName);

        JLabel lblSex = new JLabel("性别：");
        lblSex.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblSex.setBounds(150, 200, 80, 30);
        add(lblSex);

        JRadioButton male = new JRadioButton("男", true);
        JRadioButton female = new JRadioButton("女", false);
        male.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        female.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        ButtonGroup sexGroup = new ButtonGroup();
        sexGroup.add(male);
        sexGroup.add(female);
        male.setBounds(230, 200, 60, 30);
        female.setBounds(290, 200, 60, 30);
        add(male);
        add(female);

        JLabel lblTel = new JLabel("电话：");
        lblTel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblTel.setBounds(150, 240, 80, 30);
        add(lblTel);

        JTextField txtTel = new JTextField();
        txtTel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtTel.setBounds(230, 240, 200, 30);
        add(txtTel);

        JLabel lblBir = new JLabel("生日：");
        lblBir.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        lblBir.setBounds(150, 280, 80, 30);
        add(lblBir);

        JTextField txtBir = new JTextField();
        txtBir.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtBir.setBounds(230, 280, 200, 30);
        txtBir.setToolTipText("格式: yyyy-MM-dd");
        add(txtBir);

        JButton btnReg = new JButton("注册");
        btnReg.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnReg.setBounds(230, 340, 120, 35);
        btnReg.setBackground(new Color(66, 133, 244));
        btnReg.setForeground(Color.WHITE);
        add(btnReg);

        JButton btnBack = new JButton("返回登录");
        btnBack.setFont(new Font("微软雅黑", Font.BOLD, 18));
        btnBack.setBounds(360, 340, 120, 35);
        btnBack.setBackground(new Color(247, 181, 0));
        btnBack.setForeground(Color.WHITE);
        add(btnBack);

        JLabel lblMsg = new JLabel("");
        lblMsg.setForeground(Color.RED);
        lblMsg.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        lblMsg.setBounds(230, 390, 280, 30);
        add(lblMsg);

        btnReg.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = new String(txtPwd.getPassword()).trim();
            String uname = txtName.getText().trim();
            String sex = male.isSelected() ? "男" : "女";
            String tel = txtTel.getText().trim();
            String birStr = txtBir.getText().trim();

            if (username.isEmpty() || password.isEmpty() || uname.isEmpty() || tel.isEmpty() || birStr.isEmpty()) {
                lblMsg.setText("所有字段都不能为空！");
                return;
            }
            if (UserDao.isUsernameExists(username)) {
                lblMsg.setText("账号已存在！");
                return;
            }
            // 生日格式校验
            java.sql.Date birDate;
            try {
                birDate = java.sql.Date.valueOf(birStr); // yyyy-MM-dd
            } catch (Exception ex) {
                lblMsg.setText("生日格式错误，应为yyyy-MM-dd");
                return;
            }
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(password);
            user.setUname(uname);
            user.setSex(sex);
            user.setTel(tel);
            user.setBir(birDate);

            boolean result = UserDao.register(user);
            if (result) {
                Users regUser = UserDao.getUserByUsername(username);
                if (regUser != null) {
                    int userId = regUser.getUser_id();
                    int roleId = 2; // 普通用户
                    UserDao.addUserRole(userId, roleId);
                }
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