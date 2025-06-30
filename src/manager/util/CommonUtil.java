package manager.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class CommonUtil
{
    public static Object[][] toArray(Collection<?> collection)
    {
        // 检查集合是否为空
        if (collection == null || collection.isEmpty())
        {
            return new Object[0][0];
        }

        // 获取集合中的第一个元素，用于确定字段数量
        Object firstElement = collection.iterator().next();
        Class<?> clazz = firstElement.getClass();
        Field[] fields = clazz.getDeclaredFields();

        // 创建二维数组，行数为集合的大小，列数为字段数量
        Object[][] result = new Object[collection.size()][fields.length];

        int rowIndex = 0;
        for (Object item : collection) {
            for (int colIndex = 0; colIndex < fields.length; colIndex++) {
                Field field = fields[colIndex];
                field.setAccessible(true); // 确保可以访问私有字段
                try {
                    result[rowIndex][colIndex] = field.get(item); // 获取字段值
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            rowIndex++;
        }

        return result;
    }

    //非空校验
    public static boolean isNotEmpty(String text) {
        if (text == null || text.trim().length() == 0) {
            return false;
        }
        return true;
    }

}
