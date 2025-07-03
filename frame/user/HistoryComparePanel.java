package manager.frame.user;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistoryComparePanel extends JInternalFrame {
    public HistoryComparePanel() {
        super("病史对比与健康跟踪", true, true, true, true);
        this.setSize(750, 350);
        this.setVisible(true);

        String[] columns = {"体检日期", "体检项目", "结果", "参考值", "历史趋势"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // TODO: 查询用户历次体检某些项目，对比展示
        model.addRow(new Object[]{"2025-06-03", "血压", "130/85", "120/80", "略升高"});
        model.addRow(new Object[]{"2025-07-12", "血压", "128/82", "120/80", "改善"});
        model.addRow(new Object[]{"2025-06-03", "血糖", "6.8", "3.9-6.1", "偏高"});
        model.addRow(new Object[]{"2025-07-12", "血糖", "6.2", "3.9-6.1", "改善"});

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}