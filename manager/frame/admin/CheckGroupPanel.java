package manager.frame.admin;

import manager.dao.CheckGroupDao;
import manager.dao.CheckItemDao;
import manager.pojo.CheckGroup;
import manager.pojo.CheckItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 检查组管理面板，支持对检查组的增删改查，并可查询显示所选组包含的检查项
 * 新增/编辑组时支持勾选检查项
 */
public class CheckGroupPanel extends JInternalFrame {
    private JTable groupTable;
    private DefaultTableModel groupTableModel;

    private JTable itemTable;
    private DefaultTableModel itemTableModel;

    public CheckGroupPanel() {
        super("检查组管理", true, true, true, true);
        this.setSize(800, 500);
        this.setVisible(true);

        initUI();
        refreshGroupTable();
    }

    /**
     * 初始化界面与事件
     */
    private void initUI() {
        JPanel topPanel = new JPanel();
        JButton addBtn = new JButton("新增");
        JButton editBtn = new JButton("编辑");
        JButton deleteBtn = new JButton("删除");
        topPanel.add(addBtn);
        topPanel.add(editBtn);
        topPanel.add(deleteBtn);
        this.add(topPanel, BorderLayout.NORTH);

        // 检查组表
        groupTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"组ID", "组名", "描述"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        groupTable = new JTable(groupTableModel);
        groupTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 检查项表
        itemTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"检查项ID", "名称", "单位", "参考值", "状态"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        itemTable = new JTable(itemTableModel);

        // 布局
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(groupTable), new JScrollPane(itemTable));
        splitPane.setDividerLocation(250);
        this.add(splitPane, BorderLayout.CENTER);

        // 事件
        addBtn.addActionListener(e -> addGroup());
        editBtn.addActionListener(e -> editGroup());
        deleteBtn.addActionListener(e -> deleteGroup());

        // 选中组时查询检查项
        groupTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showGroupItems();
            }
        });
    }

    /**
     * 刷新检查组表
     */
    private void refreshGroupTable() {
        List<CheckGroup> groups = CheckGroupDao.getAllCheckGroups();
        groupTableModel.setRowCount(0);
        for (CheckGroup g : groups) {
            groupTableModel.addRow(new Object[]{g.getGroupId(), g.getGroupName(), g.getDescription()});
        }
        if (groupTableModel.getRowCount() > 0) {
            groupTable.setRowSelectionInterval(0, 0);
            showGroupItems();
        } else {
            itemTableModel.setRowCount(0);
        }
    }

    /**
     * 查询并显示当前选中组的检查项
     */
    private void showGroupItems() {
        int row = groupTable.getSelectedRow();
        if (row < 0) {
            itemTableModel.setRowCount(0);
            return;
        }
        Integer groupId = (Integer) groupTableModel.getValueAt(row, 0);
        List<Integer> itemIds = CheckGroupDao.getItemIdsByGroupId(groupId);
        List<CheckItem> items = CheckItemDao.getCheckItemsByIds(itemIds);
        itemTableModel.setRowCount(0);
        for (CheckItem item : items) {
            itemTableModel.addRow(new Object[]{
                    item.getCid(),       // 检查项ID
                    item.getCname(),     // 名称
                    item.getUnit(),      // 单位
                    item.getRefer_val(), // 参考值
                    item.getStatus()     // 状态
            });
        }
    }

    /**
     * 新增检查组（弹窗选择项并保存）
     */
    private void addGroup() {
        CheckGroupEditDialog dialog = new CheckGroupEditDialog(
                SwingUtilities.getWindowAncestor(this), "新增检查组", null, null
        );
        dialog.setVisible(true);
        if (dialog.isOk()) {
            CheckGroup group = new CheckGroup();
            group.setGroupName(dialog.getGroupName());
            group.setDescription(dialog.getGroupDesc());
            CheckGroupDao.addCheckGroup(group);

            // 获取新插入组ID（假设列表最后一个是新增的）
            List<CheckGroup> groups = CheckGroupDao.getAllCheckGroups();
            Integer groupId = groups.get(groups.size() - 1).getGroupId();

            // 保存组与检查项关联
            CheckGroupDao.updateGroupItems(groupId, dialog.getSelectedItemIds());
            refreshGroupTable();
        }
    }

    /**
     * 编辑检查组（弹窗回显项并保存）
     */
    private void editGroup() {
        int row = groupTable.getSelectedRow();
        if (row < 0) return;
        Integer groupId = (Integer) groupTableModel.getValueAt(row, 0);
        CheckGroup oldGroup = CheckGroupDao.getCheckGroupById(groupId);
        List<Integer> checkedItems = CheckGroupDao.getItemIdsByGroupId(groupId);

        CheckGroupEditDialog dialog = new CheckGroupEditDialog(
                SwingUtilities.getWindowAncestor(this), "编辑检查组", oldGroup, checkedItems
        );
        dialog.setVisible(true);
        if (dialog.isOk()) {
            oldGroup.setGroupName(dialog.getGroupName());
            oldGroup.setDescription(dialog.getGroupDesc());
            CheckGroupDao.updateCheckGroup(oldGroup);
            CheckGroupDao.updateGroupItems(groupId, dialog.getSelectedItemIds());
            refreshGroupTable();
        }
    }

    private void deleteGroup() {
        int row = groupTable.getSelectedRow();
        if (row < 0) return;
        Integer groupId = (Integer) groupTableModel.getValueAt(row, 0);
        int result = JOptionPane.showConfirmDialog(this, "确认删除该检查组？", "警告", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            CheckGroupDao.deleteCheckGroup(groupId);
            refreshGroupTable();
        }
    }

    private String inputText(String msg) {
        String s = JOptionPane.showInputDialog(this, msg);
        if (s == null || s.trim().isEmpty()) return null;
        return s.trim();
    }
    private String inputText(String msg, String init) {
        String s = JOptionPane.showInputDialog(this, msg, init);
        if (s == null || s.trim().isEmpty()) return null;
        return s.trim();
    }
}

/**
 * 检查组编辑（新建/编辑）对话框，支持勾选检查项
 */
class CheckGroupEditDialog extends JDialog {
    private JTextField txtName = new JTextField(20);
    private JTextField txtDesc = new JTextField(20);
    private JPanel checkItemPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    private List<JCheckBox> itemCheckBoxes = new ArrayList<>();
    private boolean isOk = false; // 是否点击了确定

    public CheckGroupEditDialog(Window owner, String title, CheckGroup group, List<Integer> checkedItemIds) {
        super(owner, title, ModalityType.APPLICATION_MODAL);

        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.add(new JLabel("组名:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("描述:"));
        formPanel.add(txtDesc);
        add(formPanel, BorderLayout.NORTH);

        // 检查项复选框（推荐用getAllCheckItems）
        List<CheckItem> allItems = CheckItemDao.getAllCheckItems();
        checkItemPanel.removeAll();
        itemCheckBoxes.clear();
        for (CheckItem item : allItems) {
            JCheckBox box = new JCheckBox(item.getCname() + "（" + item.getCcode() + "）");
            box.setSelected(checkedItemIds != null && checkedItemIds.contains(item.getCid()));
            box.putClientProperty("cid", item.getCid());
            itemCheckBoxes.add(box);
            checkItemPanel.add(box);
        }
        add(new JScrollPane(checkItemPanel), BorderLayout.CENTER);

        // 按钮
        JPanel btnPanel = new JPanel();
        JButton okBtn = new JButton("确定");
        JButton cancelBtn = new JButton("取消");
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        if (group != null) {
            txtName.setText(group.getGroupName());
            txtDesc.setText(group.getDescription());
        }

        okBtn.addActionListener(e -> {
            if (txtName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "组名不能为空！");
                return;
            }
            isOk = true;
            setVisible(false);
        });
        cancelBtn.addActionListener(e -> {
            isOk = false;
            setVisible(false);
        });

        setSize(400, 400);
        setLocationRelativeTo(owner);
    }

    public boolean isOk() {
        return isOk;
    }

    public String getGroupName() {
        return txtName.getText().trim();
    }

    public String getGroupDesc() {
        return txtDesc.getText().trim();
    }

    /** 获取选中的检查项ID */
    public List<Integer> getSelectedItemIds() {
        List<Integer> ids = new ArrayList<>();
        for (JCheckBox box : itemCheckBoxes) {
            if (box.isSelected()) {
                ids.add((Integer) box.getClientProperty("cid"));
            }
        }
        return ids;
    }
}