package manager.frame.admin;

import manager.dao.CheckItemDao;
import manager.frame.MainFrame;
import manager.pojo.CheckItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/*
 * 检查项数据列表面板
 * 显示检查项的列表，并支持搜索、新增、编辑和删除功能。
 * 继承自JInternalFrame，可作为AdminPanel的内部窗口使用。
 */

public class DataTablePanel extends JInternalFrame
{
    private JTextField field1=new JTextField(10);
    private JTextField field2=new JTextField(10);

    private JTable table=new JTable(){
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };

    public DataTablePanel()
    {
        super("数据列表",true,true,true,true);
        this.setVisible(true);

        JPanel topPanel=new JPanel();
        this.add(topPanel, BorderLayout.NORTH);

        topPanel.add(new JLabel("名称:"));
        topPanel.add(field1);
        topPanel.add(new JLabel("代码:"));
        topPanel.add(field2);

        JButton button=new JButton("搜索");
        topPanel.add(button);
        JButton addBtn=new JButton("Add");
        topPanel.add(addBtn);
        JButton deleteBtn=new JButton("Delete");
        topPanel.add(deleteBtn);
        JButton editBtn=new JButton("Edit");
        topPanel.add(editBtn);

        JPanel panel=new JPanel(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        panel.add(table,BorderLayout.CENTER);
        panel.add(table.getTableHeader(),BorderLayout.NORTH);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        search();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                search();
            }
        });
        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                AdminPanel.setContent(new DataEditPanel(null));
            }
        });
        editBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //修改时在表格先选中
                int rownumber=table.getSelectedRow();
                if(rownumber<0)
                {
                    return;
                }
                AdminPanel.setContent(new DataEditPanel((Integer)table.getValueAt(rownumber,0)));
            }
        });

        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int rownumber=table.getSelectedRow();
                if(rownumber<0)
                {
                    return;
                }
                // 权限判断和删除调用
                boolean isAdmin = MainFrame.users.getUsername().equals("admin");
                Integer userId = isAdmin ? null : MainFrame.users.getUser_id();
                Integer cid = (Integer)table.getValueAt(rownumber, 0);

                int result = CheckItemDao.deleteCheckItem(cid, userId);
                if(result > 0){
                    JOptionPane.showMessageDialog(DataTablePanel.this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    search();
                }else{
                    JOptionPane.showMessageDialog(DataTablePanel.this, "删除失败", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void search()
    {
        boolean isAdmin = MainFrame.users.getUsername().equals("admin");
        TableModel tableModel;
        if (isAdmin) {
            // 管理员查全部
            tableModel = new DefaultTableModel(
                    CheckItemDao.queryAllCheckItem(field1.getText(), field2.getText()),
                    CheckItemDao.columnNames
            );
        } else {
            // 普通用户只查自己
            Object[][] data = CheckItemDao.queryCheckItemsByUserId(MainFrame.users.getUser_id(), field1.getText(), field2.getText());
            tableModel = new DefaultTableModel(
                    CheckItemDao.queryCheckItemsByUserId(MainFrame.users.getUser_id(), field1.getText(), field2.getText()),
                    CheckItemDao.columnNames
            );
        }
        table.setModel(tableModel);
    }
}