package manager.frame;

import manager.pojo.Users;
import manager.util.SystemConstants;

import javax.swing.*;

public class MainFrame {
    public static final JFrame frame = new JFrame("健康管理系统！创建者：戴枭");

    public static Users users = null;

    public static void main(String[] args) {

        frame.setSize(SystemConstants.FRAME_WIDTH, SystemConstants.FRAME_HEIGHT);
        frame.setContentPane(new LoginPanel());
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public static void setContent(JPanel jPanel) {
        frame.setContentPane(jPanel);
        frame.revalidate(); // 刷新布局
        frame.repaint();    // 重绘界面
    }
}
