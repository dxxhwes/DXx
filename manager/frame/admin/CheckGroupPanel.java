package manager.frame.admin;

import manager.dao.CheckGroupDao;
import manager.pojo.CheckGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CheckGroupPanel extends JInternalFrame {
    private JTable table = new JTable();
    private DefaultTableModel tableModel;

    public CheckGroupPanel() {
        super("检查组管理", true, true, true, true);
        this.setSize(600, 400);
        this.setVisible(true);

        JPanel topPanel = new JPanel();
        JButton addBtn = new JButton("新增");
        JButton editBtn = new JButton("编辑");
        JButton deleteBtn = new JButton("删除");

        topPanel.add(addBtn);
        topPanel.add(editBtn);
        topPanel.add(deleteBtn);

        this.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"组ID", "组名", "描述"});
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshTable();

        addBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "输入组名：");
            if (name == null || name.trim().isEmpty()) return;
            String desc = JOptionPane.showInputDialog(this, "输入描述：");
            CheckGroup group = new CheckGroup();
            group.setGroupName(name.trim());
            group.setDescription(desc != null ? desc.trim() : "");
            CheckGroupDao.addCheckGroup(group);
            refreshTable();
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;
            Integer groupId = (Integer) tableModel.getValueAt(row, 0);
            String oldName = (String) tableModel.getValueAt(row, 1);
            String oldDesc = (String) tableModel.getValueAt(row, 2);
            String newName = JOptionPane.showInputDialog(this, "新组名：", oldName);
            if (newName == null || newName.trim().isEmpty()) return;
            String newDesc = JOptionPane.showInputDialog(this, "新描述：", oldDesc);
            CheckGroup group = new CheckGroup();
            group.setGroupId(groupId);
            group.setGroupName(newName.trim());
            group.setDescription(newDesc != null ? newDesc.trim() : "");
            CheckGroupDao.updateCheckGroup(group);
            refreshTable();
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;
            Integer groupId = (Integer) tableModel.getValueAt(row, 0);
            int result = JOptionPane.showConfirmDialog(this, "确认删除该检查组？", "警告", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                CheckGroupDao.deleteCheckGroup(groupId);
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        List<CheckGroup> groups = CheckGroupDao.getAllCheckGroups();
        tableModel.setRowCount(0);
        for (CheckGroup g : groups) {
            tableModel.addRow(new Object[]{g.getGroupId(), g.getGroupName(), g.getDescription()});
        }
    }
}