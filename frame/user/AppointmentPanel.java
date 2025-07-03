package manager.frame.user;

import manager.pojo.Users;
import manager.util.SystemConstants;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AppointmentPanel extends JInternalFrame {
    public AppointmentPanel() {
        super("预约体检", true, true, true, true);
        this.setSize(500, 350);
        this.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblPackage = new JLabel("体检套餐：");
        lblPackage.setBounds(60, 40, 80, 30);
        panel.add(lblPackage);

        JComboBox<String> packageCombo = new JComboBox<>();
        // TODO: 从数据库读取所有套餐 populate packageCombo
        packageCombo.addItem("基础体检套餐");
        packageCombo.addItem("中老年体检套餐");
        packageCombo.addItem("女性专项套餐");
        packageCombo.setBounds(140, 40, 220, 30);
        panel.add(packageCombo);

        JLabel lblDate = new JLabel("预约日期：");
        lblDate.setBounds(60, 90, 80, 30);
        panel.add(lblDate);

        JTextField txtDate = new JTextField("2025-07-10"); // 可用JDateChooser等控件美化
        txtDate.setBounds(140, 90, 220, 30);
        panel.add(txtDate);

        JButton btnAppoint = new JButton("提交预约");
        btnAppoint.setBounds(200, 160, 120, 35);
        panel.add(btnAppoint);

        JLabel lblMsg = new JLabel("");
        lblMsg.setBounds(60, 210, 350, 30);
        lblMsg.setForeground(Color.RED);
        panel.add(lblMsg);

        btnAppoint.addActionListener(e -> {
            // TODO: 获取当前用户
            // Users user = MainFrame.users;
            // String packageName = (String)packageCombo.getSelectedItem();
            // String dateStr = txtDate.getText().trim();

            // TODO: 检查与插入预约表
            // appointmentDao.addAppointment(user.getUser_id(), packageId, dateStr);

            lblMsg.setText("预约成功，等待审核/体检！");
        });

        this.add(panel);
    }
}