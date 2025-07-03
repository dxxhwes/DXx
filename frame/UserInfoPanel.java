package manager.frame;

import manager.pojo.Users;
import manager.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

public class UserInfoPanel extends JInternalFrame {
    public UserInfoPanel() {
        super("个人信息", true, true, true, true);
        this.setSize(400, 320);
        this.setVisible(true);

        Users user = MainFrame.users;  // 假设MainFrame.users存放当前登录用户对象

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblUsername = new JLabel("账号：");
        lblUsername.setBounds(60, 30, 80, 30);
        panel.add(lblUsername);
        JTextField txtUsername = new JTextField(user.getUsername());
        txtUsername.setBounds(140, 30, 180, 30);
        txtUsername.setEditable(false);
        panel.add(txtUsername);

        JLabel lblUname = new JLabel("姓名：");
        lblUname.setBounds(60, 70, 80, 30);
        panel.add(lblUname);
        JTextField txtUname = new JTextField(user.getUname());
        txtUname.setBounds(140, 70, 180, 30);
        txtUname.setEditable(false);
        panel.add(txtUname);

        JLabel lblSex = new JLabel("性别：");
        lblSex.setBounds(60, 110, 80, 30);
        panel.add(lblSex);
        JTextField txtSex = new JTextField(user.getSex());
        txtSex.setBounds(140, 110, 180, 30);
        txtSex.setEditable(false);
        panel.add(txtSex);

        JLabel lblTel = new JLabel("电话：");
        lblTel.setBounds(60, 150, 80, 30);
        panel.add(lblTel);
        JTextField txtTel = new JTextField(user.getTel());
        txtTel.setBounds(140, 150, 180, 30);
        txtTel.setEditable(false);
        panel.add(txtTel);

        JLabel lblBir = new JLabel("生日：");
        lblBir.setBounds(60, 190, 80, 30);
        panel.add(lblBir);
        JTextField txtBir = new JTextField(user.getBir()==null?"":user.getBir().toString());
        txtBir.setBounds(140, 190, 180, 30);
        txtBir.setEditable(false);
        panel.add(txtBir);

        this.add(panel);
    }
}
