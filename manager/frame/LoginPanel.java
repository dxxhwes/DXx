package manager.frame;

import manager.dao.UserDao;
import manager.frame.admin.AdminPanel;
import manager.frame.user.UserPanel;
import manager.pojo.Users;
import manager.util.SystemConstants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel
{
    private static final String DIR = LoginPanel.class.getClassLoader().getResource("manager/images").getPath();

    public LoginPanel() {
        this.setBounds(0, 0, SystemConstants.FRAME_WIDTH, SystemConstants.FRAME_HEIGHT);
        this.setLayout(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(new ImageIcon(DIR + "/arg.png").getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };

        panel.setBounds(200, 100, 500, 300);
        this.add(panel);

        Box vbox = Box.createVerticalBox();
        panel.add(vbox);

        vbox.add(Box.createVerticalStrut(15));

        Box box0 = Box.createHorizontalBox();
        JLabel title = new JLabel("医疗管理系统");
        title.setFont(new Font("微软雅黑", Font.BOLD, 30));
        box0.add(title);
        vbox.add(box0);

        vbox.add(Box.createVerticalStrut(20));

        Font font = new Font("微软雅黑", Font.BOLD, 20);
        Border border = BorderFactory.createLoweredSoftBevelBorder();

        Box box1 = Box.createHorizontalBox();
        JLabel nameLabel = new JLabel("账号");
        nameLabel.setFont(font);

        box1.add(nameLabel);

        JTextField nameField = new JTextField(15);
        box1.add(nameField);
        vbox.add(box1);

        vbox.add(Box.createVerticalStrut(15));

        Box box2 = Box.createHorizontalBox();
        JLabel pwdLabel = new JLabel("密码");
        pwdLabel.setFont(font);

        box2.add(pwdLabel);

        JPasswordField pwdField = new JPasswordField(15);
        box2.add(pwdField);

        vbox.add(box2);
        vbox.add(Box.createVerticalStrut(15));

        JRadioButton admin = new JRadioButton("管理员", true);
        JRadioButton user = new JRadioButton("用户", false);

        admin.setFont(font);
        user.setFont(font);
        admin.setOpaque(false);
        admin.setFocusPainted(false);
        user.setOpaque(false);
        user.setFocusPainted(false);

        ButtonGroup bg = new ButtonGroup();
        bg.add(admin);
        bg.add(user);

        Box box3 = Box.createHorizontalBox();
        box3.add(admin);
        box3.add(Box.createHorizontalStrut(10));
        box3.add(user);
        vbox.add(box3);
        vbox.add(Box.createVerticalStrut(15));

        // 登录按钮（文本）
        JButton login = new JButton("登录");
        login.setFont(font);

        // 注册按钮（文本）
        JButton reg = new JButton("注册账号");
        reg.setFont(font);

        // 忘记密码按钮（文本）
        JButton forgot = new JButton("忘记密码");
        forgot.setFont(font);

        // 横向排列三个按钮
        Box box4 = Box.createHorizontalBox();
        box4.add(login);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(reg);
        box4.add(Box.createHorizontalStrut(10));
        box4.add(forgot);
        vbox.add(box4);

        // 登录事件
        login.addActionListener(e -> {
            JPanel panel1 = null;
            String username = nameField.getText();
            String password = new String(pwdField.getPassword());
            Users users = null;
            if (admin.isSelected()) {
                users = new UserDao().getUserByName(username, 1);
                panel1 = new AdminPanel();
            } else {
                users = new UserDao().getUserByName(username, 2);
                panel1 = new UserPanel();
            }
            if (users == null || !users.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(login.getParent(), "用户名或密码错误", "系统提示", JOptionPane.WARNING_MESSAGE);
            } else {
                MainFrame.setContent(panel1);
                MainFrame.users = users;
            }
        });

// 注册按钮事件
        reg.addActionListener(e -> {
            MainFrame.setContent(new RegisterPanel());
        });

// 忘记密码按钮事件
        forgot.addActionListener(e -> {
            MainFrame.setContent(new ForgotPasswordPanel());
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(new ImageIcon(DIR + "/bn.png").getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
