package manager.dao;

import manager.common.BaseDao;
import manager.pojo.CheckGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 检查组管理DAO
 */
public class CheckGroupDao {

    /**
     * 新增检查组
     */
    public static int addCheckGroup(CheckGroup group) {
        String sql = "INSERT INTO check_group (group_name, description) VALUES (?, ?)";
        Object[] params = {group.getGroupName(), group.getDescription()};
        return BaseDao.executeDML(sql, params);
    }

    /**
     * 修改检查组
     */
    public static int updateCheckGroup(CheckGroup group) {
        String sql = "UPDATE check_group SET group_name = ?, description = ? WHERE group_id = ?";
        Object[] params = {group.getGroupName(), group.getDescription(), group.getGroupId()};
        return BaseDao.executeDML(sql, params);
    }

    /**
     * 删除检查组（同时可考虑删除group_item中的关联项）
     */
    public static int deleteCheckGroup(int groupId) {
        // 先删除关联表中的项
        String delItemsSql = "DELETE FROM group_item WHERE group_id = ?";
        BaseDao.executeDML(delItemsSql, groupId);

        // 再删除检查组本身
        String sql = "DELETE FROM check_group WHERE group_id = ?";
        return BaseDao.executeDML(sql, groupId);
    }

    /**
     * 查询所有检查组
     */
    public static List<CheckGroup> getAllCheckGroups() {
        String sql = "SELECT * FROM check_group";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{});
        List<CheckGroup> list = new ArrayList<>();
        try {
            while (rs != null && rs.next()) {
                CheckGroup group = new CheckGroup();
                group.setGroupId(rs.getInt("group_id"));
                group.setGroupName(rs.getString("group_name"));
                group.setDescription(rs.getString("description"));
                list.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return list;
    }

    /**
     * 根据ID查询检查组
     */
    public static CheckGroup getCheckGroupById(int groupId) {
        String sql = "SELECT * FROM check_group WHERE group_id = ?";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{groupId});
        CheckGroup group = null;
        try {
            if (rs != null && rs.next()) {
                group = new CheckGroup();
                group.setGroupId(rs.getInt("group_id"));
                group.setGroupName(rs.getString("group_name"));
                group.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return group;
    }

    /**
     * 查询某检查组下的所有检查项ID
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

    /**
     * 维护组与检查项的关联（先清空再批量添加）
     */
    public static void updateGroupItems(int groupId, List<Integer> itemIds) {
        // 先删除原有的
        String delSql = "DELETE FROM group_item WHERE group_id = ?";
        BaseDao.executeDML(delSql, groupId);
        // 再插入新的
        if (itemIds != null) {
            for (Integer itemId : itemIds) {
                String insSql = "INSERT INTO group_item (group_id, item_id) VALUES (?, ?)";
                BaseDao.executeDML(insSql, groupId, itemId);
            }
        }
    }
}