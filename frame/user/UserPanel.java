package manager.frame.user;

import manager.util.SystemConstants;
import manager.frame.UserInfoPanel;
import manager.frame.ChangePasswordPanel;
import manager.frame.MainFrame;
import manager.frame.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 用户主面板
 */
public class UserPanel extends JPanel
{
    private static JDesktopPane contentPanel = new JDesktopPane();

    public UserPanel()
    {
        this.setBounds(0, 0, SystemConstants.FRAME_WIDTH, SystemConstants.FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, SystemConstants.FRAME_WIDTH, 60);

        this.add(menuBar, BorderLayout.NORTH); // 正确添加到北侧
        contentPanel.removeAll();
        contentPanel.repaint();
        this.add(contentPanel, BorderLayout.CENTER);

        JMenu infoMenu = new JMenu("个人信息");
        JMenu systemMenu = new JMenu("系统管理");

        menuBar.add(infoMenu);
        menuBar.add(systemMenu);

        // 个人信息点击事件
        infoMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new UserInfoPanel());
            }
        });
        //预约功能
        JMenu appointMenu = new JMenu("预约体检");
        appointMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new AppointmentPanel());
            }
        });

        JMenu trackMenu = new JMenu("体检跟踪/结果");
        trackMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new TrackResultPanel());
            }
        });

        JMenu historyMenu = new JMenu("病史对比");
        historyMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setContent(new HistoryComparePanel());
            }
        });
        menuBar.add(appointMenu);
        menuBar.add(trackMenu);
        menuBar.add(historyMenu);

        JMenuItem passwordMenu = new JMenuItem("修改密码");
        JMenuItem logoutMenu = new JMenuItem("退出登录");
        systemMenu.add(passwordMenu);
        systemMenu.add(logoutMenu);

        // 修改密码事件
        passwordMenu.addActionListener(e -> setContent(new ChangePasswordPanel()));

        // 退出登录事件
        logoutMenu.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.repaint();
            MainFrame.setContent(new LoginPanel());
        });
    }

    public static void setContent(JInternalFrame internalFrame)//内部窗口
    {
        internalFrame.setSize(SystemConstants.FRAME_WIDTH - 15, SystemConstants.FRAME_HEIGHT - 60);
        internalFrame.setVisible(true);
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.add(internalFrame);
    }
}