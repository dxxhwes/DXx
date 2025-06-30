package manager.frame.user;

import manager.util.SystemConstants;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel
{
    private static JDesktopPane contentPanel=new JDesktopPane();

    public UserPanel()
    {
        this.setBounds(0,0, SystemConstants.FRAME_WIDTH,SystemConstants.FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        JMenuBar menuBar=new JMenuBar();
        menuBar.setBounds(0,0,SystemConstants.FRAME_WIDTH,60);

        this.add(menuBar);
        contentPanel.removeAll();
        contentPanel.repaint();

        this.add(contentPanel,BorderLayout.CENTER);

        JMenu parentMenu=new JMenu("个人信息");
        JMenu systemMenu=new JMenu("系统管理");

        menuBar.add(parentMenu);
        menuBar.add(systemMenu);

        JMenuItem passwordMenu=new JMenuItem("修改密码");
        JMenuItem logoutMenu=new JMenuItem("退出登录");
        systemMenu.add(passwordMenu);
        systemMenu.add(logoutMenu);
    }


    public static void setContent(JInternalFrame internalFrame)//内部窗口
    {
        internalFrame.setSize(SystemConstants.FRAME_WIDTH-15,SystemConstants.FRAME_HEIGHT-60);

        internalFrame.setVisible(true);
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.add(internalFrame);

    }
}
