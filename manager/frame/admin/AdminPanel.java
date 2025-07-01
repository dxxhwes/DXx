package manager.frame.admin;

import manager.frame.LoginPanel;
import manager.frame.MainFrame;
import manager.util.SystemConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminPanel extends JPanel
{
    //内部窗口合计
    private static JDesktopPane contentPanel=new JDesktopPane();

    public AdminPanel()
    {
        this.setBounds(0,0, SystemConstants.FRAME_WIDTH,SystemConstants.FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        JMenuBar menuBar=new JMenuBar();
        menuBar.setBounds(0,0,SystemConstants.FRAME_WIDTH,50);

        this.add(menuBar,BorderLayout.NORTH);
        contentPanel.removeAll();
        contentPanel.repaint();

        this.add(contentPanel,BorderLayout.CENTER);

        JMenu parentMenu=new JMenu("检查项");

        parentMenu.addMouseListener(new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e)
        {
            setContent(new DataTablePanel());
        }
    });

        JMenu adminMenu=new JMenu("管理员");
        JMenu userMenu=new JMenu("用户");
        JMenu systemMenu=new JMenu("系统管理");

        menuBar.add(parentMenu);
        menuBar.add(adminMenu);
        menuBar.add(userMenu);
        menuBar.add(systemMenu);

        JMenuItem passwordMenu=new JMenuItem("修改密码");
        JMenuItem logoutMenu=new JMenuItem("退出登录");
        systemMenu.add(passwordMenu);
        systemMenu.add(logoutMenu);

        logoutMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e)
            {
                contentPanel.removeAll();
                contentPanel.repaint();
                MainFrame.setContent(new LoginPanel());
            }
        });

    }
    //切换内部窗口
    public static void setContent(JInternalFrame internalFrame)//内部窗口
    {
        internalFrame.setSize(SystemConstants.FRAME_WIDTH-15,SystemConstants.FRAME_HEIGHT-60);

        internalFrame.setVisible(true);
        contentPanel.removeAll();
        contentPanel.repaint();
        contentPanel.add(internalFrame);

    }
}
