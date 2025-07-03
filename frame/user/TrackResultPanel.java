package manager.frame.user;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TrackResultPanel extends JInternalFrame {
    public TrackResultPanel() {
        super("体检预约与结果跟踪", true, true, true, true);
        this.setSize(750, 400);
        this.setVisible(true);

        String[] columns = {"预约日期", "体检套餐", "状态", "体检日期", "体检结果"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // TODO: 查询当前用户所有预约及结果，填充model
        model.addRow(new Object[]{"2025-07-10", "基础体检套餐", "已完成", "2025-07-12", "血压正常，血糖偏高"});
        model.addRow(new Object[]{"2025-06-01", "女性专项套餐", "已完成", "2025-06-03", "一切正常"});

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}