package manager.util;

import javax.swing.*;
import javax.swing.text.JTextComponent;



public class SystemVerifier
{
    public static InputVerifier emptyVerify(String fieldName, Integer minLen, Integer maxLen) {
        return new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                //去掉回车验证
                input.setVerifyInputWhenFocusTarget(false);
                //拿到文字
                String text = ((JTextComponent) input).getText();
                //拿到文字的长度
                int len = text.trim().length();
                if (len == 0) {
                    JOptionPane.showMessageDialog(null, fieldName + "不能为空", "系统提示",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                if (minLen != null && len < minLen) {
                    JOptionPane.showMessageDialog(null, fieldName + "长度不能小于" + minLen, "系统提示",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                if (maxLen != null && len > maxLen) {
                    JOptionPane.showMessageDialog(null, fieldName + "长度不能大于" + maxLen, "系统提示",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                return true;
            }
        };
    }

}
