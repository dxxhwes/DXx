package manager.frame.admin;

import manager.dao.CheckItemDao;
import manager.frame.MainFrame;
import manager.pojo.CheckItem;
import manager.util.SystemVerifier;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataEditPanel extends JInternalFrame
{
    public DataEditPanel(Integer id)
    {
        super("添加检查项", true, true, true, true);
        this.setVisible(true);

        //创建Panel
        JPanel panel = new JPanel();
        this.add(panel);

        //创建垂直包裹所有
        Box box = Box.createVerticalBox();
        panel.add(box);
        box.add(Box.createVerticalStrut(55));

        Box box1 = Box.createHorizontalBox();
        box1.add(new JLabel("代码："));
        JTextField field1 = new JTextField(10);

        field1.setInputVerifier(SystemVerifier.emptyVerify("代码", 2, null));
        box1.add(field1);
        box.add(box1);
        box.add(Box.createVerticalStrut(5));

        Box box2 = Box.createHorizontalBox();
        box2.add(new JLabel("名称："));
        JTextField field2 = new JTextField(10);
        box2.add(field2);
        box.add(box2);
        box.add(Box.createVerticalStrut(5));

        Box box3 = Box.createHorizontalBox();
        box3.add(new JLabel("参考值："));
        JTextField field3 = new JTextField(10);
        box3.add(field3);
        box.add(box3);
        box.add(Box.createVerticalStrut(5));

        Box box4 = Box.createHorizontalBox();
        box4.add(new JLabel("单位："));
        JTextField field4 = new JTextField(10);
        box4.add(field4);
        box.add(box4);
        box.add(Box.createVerticalStrut(5));

        JButton btn = new JButton("提交");
        Box boxBtn = Box.createHorizontalBox();
        boxBtn.add(Box.createHorizontalStrut(40));
        boxBtn.add(btn);
        box.add(boxBtn);

        // ----------- 标准三参数调用 -----------
        boolean isAdmin = MainFrame.users.getUsername().equals("admin");
        Integer userId = isAdmin ? null : MainFrame.users.getUser_id();

        if(id!=null){
            CheckItem checkItem = CheckItemDao.queryCheckItemByCid(id, userId, isAdmin);
            field1.setText(checkItem.getCcode());
            field2.setText(checkItem.getCname());
            field3.setText(checkItem.getRefer_val());
            field4.setText(checkItem.getUnit());
        }

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (!field1.getInputVerifier().verify(field1))
                {
                    return;
                }

                CheckItem checkItem = new CheckItem();
                checkItem.setCid(id);
                checkItem.setCcode(field1.getText());
                checkItem.setCname(field2.getText());
                checkItem.setRefer_val(field3.getText());
                checkItem.setUnit(field4.getText());
                checkItem.setOption_user(MainFrame.users.getUname());
                // ----------- 用户身份写入 -----------
                checkItem.setUserId(MainFrame.users.getUser_id());

                if(id==null){
                    // ----------- 标准三参数查重 -----------
                    CheckItem CheckItem1 = CheckItemDao.queryCheckItemByCcode(
                            field1.getText(),
                            userId,
                            isAdmin
                    );
                    if(CheckItem1!=null){
                        JOptionPane.showMessageDialog(btn.getParent(), "检查项编号重复", "系统提示", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int i = CheckItemDao.addCheckItem(checkItem);
                    if (i > 0)
                    {
                        AdminPanel.setContent(new DataTablePanel());
                    } else
                    {
                        JOptionPane.showMessageDialog(btn.getParent(), "检查项添加失败", "系统提示", JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    int i=CheckItemDao.updateCheckItem(checkItem);
                    if(i>0)
                    {
                        AdminPanel.setContent(new DataTablePanel());
                    }else{
                        JOptionPane.showMessageDialog(btn.getParent(),"检查项修改失败","系统提示",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }
}