package manager.dao;

import manager.common.BaseDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * 检查组-检查项关联表DAO
 */
public class GroupItemDao {

    /*
     * 新增关联（给某组添加某项）
     */
    public static int addGroupItem(int groupId, int itemId) {
        String sql = "INSERT INTO group_item (group_id, item_id) VALUES (?, ?)";
        return BaseDao.executeDML(sql, groupId, itemId);
    }

    /*
     * 删除某组的所有关联项
     */
    public static int deleteItemsByGroupId(int groupId) {
        String sql = "DELETE FROM group_item WHERE group_id = ?";
        return BaseDao.executeDML(sql, groupId);
    }

    /*
     * 删除某检查项在所有组的关联
     */
    public static int deleteGroupsByItemId(int itemId) {
        String sql = "DELETE FROM group_item WHERE item_id = ?";
        return BaseDao.executeDML(sql, itemId);
    }

    /*
     * 查询某组下所有检查项的ID
     */
    public static List<Integer> getItemIdsByGroupId(int groupId) {
        String sql = "SELECT item_id FROM group_item WHERE group_id = ?";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{groupId});
        List<Integer> itemIds = new ArrayList<>();
        try {
            while (rs != null && rs.next()) {
                itemIds.add(rs.getInt("item_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return itemIds;
    }

    /*
     * 查询某项在哪些组中
     */
    public static List<Integer> getGroupIdsByItemId(int itemId) {
        String sql = "SELECT group_id FROM group_item WHERE item_id = ?";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{itemId});
        List<Integer> groupIds = new ArrayList<>();
        try {
            while (rs != null && rs.next()) {
                groupIds.add(rs.getInt("group_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return groupIds;
    }

    /*
     * 批量为某组设置检查项（先清空后添加）
     */
    public static void setGroupItems(int groupId, List<Integer> itemIds) {
        deleteItemsByGroupId(groupId);
        if (itemIds != null) {
            for (Integer itemId : itemIds) {
                addGroupItem(groupId, itemId);
            }
        }
    }
}
