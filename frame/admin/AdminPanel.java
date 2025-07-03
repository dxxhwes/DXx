package manager.frame.admin;

import manager.frame.LoginPanel;
import manager.frame.MainFrame;
import manager.util.SystemConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 管理员主面板，支持检查项管理、检查组管理、管理员和用户管理等功能
 */
public class AdminPanel extends JPanel {
    // 内部窗口合计
    private static JDesktopPane contentPanel = new JDesktopPane();

    public AdminPanel() {
        this.setBounds(0, 0, SystemConstants.FRAME_WIDTH, SystemConstants.FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, SystemConstants.FRAME_WIDTH, 50);

        this.add(menuBar, BorderLayout.NORTH);
        contentPanel.removeAll();
        contentPanel.repaint();
        this.add(contentPanel, BorderLayout.CENTER);

        // 检查项管理菜单
        JMenu itemMenu = new JMenu("检查项");
        itemMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new DataTablePanel());
            }
        });

        // 检查组管理菜单
        JMenu groupMenu = new JMenu("检查组");
        groupMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new CheckGroupPanel());
            }
        });

        // 管理员管理菜单
        JMenu adminMenu = new JMenu("管理员");
        adminMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new UserTablePanel(1)); // role_id=1 显示管理员列表
            }
        });

        // 用户管理菜单
        JMenu userMenu = new JMenu("用户");
        userMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new UserTablePanel(2)); // role_id=2 显示普通用户列表
            }
        });

        JMenu systemMenu = new JMenu("系统管理");

        menuBar.add(itemMenu);
        menuBar.add(groupMenu);
        menuBar.add(adminMenu);
        menuBar.add(userMenu);
        menuBar.add(systemMenu);

        JMenuItem passwordMenu = new JMenuItem("修改密码");
        JMenuItem logoutMenu = new JMenuItem("退出登录");
        systemMenu.add(passwordMenu);
        systemMenu.add(logoutMenu);
        passwordMenu.addActionListener(e -> {
            setContent(new manager.frame.ChangePasswordPanel());
        });

        logoutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                contentPanel.removeAll();
                contentPanel.repaint();
                MainFrame.setContent(new LoginPanel());
            }
        });
    }

    // 切换内部窗口
    public static void setContent(JInternalFrame internalFrame) {
        internalFrame.setSize(SystemConstants.FRAME_WIDTH - 15, SystemConstants.FRAME_HEIGHT - 60);
        internalFrame.setVisible(true);
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.add(internalFrame);
    }
}