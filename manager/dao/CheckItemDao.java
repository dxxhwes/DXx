package manager.dao;

import manager.common.BaseDao;
import manager.pojo.CheckItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 体检项（检查项）数据访问对象
 * 提供对checkitem表的增删改查操作
 */
public class CheckItemDao {
    // 表头字段，与CheckItem的toArray方法顺序一致
    public static final Object[] columnNames = {
            "ID", "代号", "名称", "参考值", "单位", "创建时间", "更新时间", "删除时间", "创建人", "状态"
    };

    /**
     * 管理员查询所有体检项（可按名称、代号模糊查询）
     */
    public static Object[][] queryAllCheckItem(String cname, String code) {
        List<CheckItem> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM checkitem WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (cname != null && !cname.trim().isEmpty()) {
            sql.append("AND cname LIKE ? ");
            params.add("%" + cname.trim() + "%");
        }
        if (code != null && !code.trim().isEmpty()) {
            sql.append("AND ccode LIKE ? ");
            params.add("%" + code.trim() + "%");
        }

        ResultSet rs = BaseDao.executeDQL(sql.toString(), params.toArray());
        try {
            while (rs != null && rs.next()) {
                list.add(buildCheckItemFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return toArray(list);
    }

    /**
     * 普通用户查询自己的体检项（可按名称、代号模糊查询）
     */
    public static Object[][] queryCheckItemsByUserId(int userId, String cname, String code) {
        List<CheckItem> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM checkitem WHERE user_id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (cname != null && !cname.trim().isEmpty()) {
            sql.append("AND cname LIKE ? ");
            params.add("%" + cname.trim() + "%");
        }
        if (code != null && !code.trim().isEmpty()) {
            sql.append("AND ccode LIKE ? ");
            params.add("%" + code.trim() + "%");
        }

        ResultSet rs = BaseDao.executeDQL(sql.toString(), params.toArray());
        try {
            while (rs != null && rs.next()) {
                list.add(buildCheckItemFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return toArray(list);
    }

    /**
     * 新增体检项（带user_id）
     */
    public static int addCheckItem(CheckItem checkItem) {
        String sql = "INSERT INTO checkitem(user_id, ccode, cname, refer_val, unit, create_date, option_user, status) " +
                "VALUES (?, ?, ?, ?, ?, NOW(), ?, 1)";
        Object[] params = {
                checkItem.getUserId(),
                checkItem.getCcode(),
                checkItem.getCname(),
                checkItem.getRefer_val(),
                checkItem.getUnit(),
                checkItem.getOption_user()
        };
        return BaseDao.executeDML(sql, params);
    }

    /**
     * 根据ccode查重（管理员/普通用户）
     */
    public static CheckItem queryCheckItemByCcode(String ccode, Integer userId, boolean isAdmin) {
        String sql;
        Object[] params;
        if (isAdmin) {
            sql = "SELECT * FROM checkitem WHERE ccode = ?";
            params = new Object[]{ccode};
        } else {
            sql = "SELECT * FROM checkitem WHERE ccode = ? AND user_id = ?";
            params = new Object[]{ccode, userId};
        }
        ResultSet rs = BaseDao.executeDQL(sql, params);
        CheckItem checkItem = null;
        try {
            if (rs != null && rs.next()) {
                checkItem = buildCheckItemFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return checkItem;
    }

    /**
     * 查询体检项详情（管理员/普通用户）
     */
    public static CheckItem queryCheckItemByCid(Integer cid, Integer userId, boolean isAdmin) {
        String sql;
        Object[] params;
        if (isAdmin) {
            sql = "SELECT * FROM checkitem WHERE cid = ?";
            params = new Object[]{cid};
        } else {
            sql = "SELECT * FROM checkitem WHERE cid = ? AND user_id = ?";
            params = new Object[]{cid, userId};
        }

        ResultSet rs = BaseDao.executeDQL(sql, params);
        CheckItem checkItem = null;
        try {
            if (rs != null && rs.next()) {
                checkItem = buildCheckItemFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return checkItem;
    }

    /**
     * 修改体检项（只能改自己的）
     */
    public static int updateCheckItem(CheckItem checkItem) {
        String sql = "UPDATE checkitem SET ccode = ?, cname = ?, refer_val = ?, unit = ?, upd_date = NOW() " +
                "WHERE cid = ? AND user_id = ?";
        Object[] params = {
                checkItem.getCcode(),
                checkItem.getCname(),
                checkItem.getRefer_val(),
                checkItem.getUnit(),
                checkItem.getCid(),
                checkItem.getUserId()
        };
        return BaseDao.executeDML(sql, params);
    }

    /**
     * 删除体检项（管理员/普通用户）
     */
    public static int deleteCheckItem(Integer cid, Integer userId) {
        String sql;
        Object[] params;
        if (userId == null) {
            sql = "DELETE FROM checkitem WHERE cid = ?";
            params = new Object[]{cid};
        } else {
            sql = "DELETE FROM checkitem WHERE cid = ? AND user_id = ?";
            params = new Object[]{cid, userId};
        }
        return BaseDao.executeDML(sql, params);
    }

    /**
     * 查询所有体检项（不传ID，查全部，适用于创建/编辑检查组时勾选项目）
     */
    public static List<CheckItem> getAllCheckItems() {
        List<CheckItem> items = new ArrayList<>();
        String sql = "SELECT * FROM checkitem";
        ResultSet rs = BaseDao.executeDQL(sql, new Object[]{});
        try {
            while (rs != null && rs.next()) {
                items.add(buildCheckItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return items;
    }

    /**
     * 根据ID列表批量查询体检项
     * 用于根据检查组下的item_id列表，查出全部体检项详情（如用于检查组页面展示）
     * 若ids为null或空，则返回全部体检项
     */
    public static List<CheckItem> getCheckItemsByIds(List<Integer> ids) {
        List<CheckItem> items = new ArrayList<>();
        String sql;
        Object[] params;
        if (ids == null || ids.isEmpty()) {
            // 查全部
            sql = "SELECT * FROM checkitem";
            params = new Object[]{};
        } else {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM checkitem WHERE cid IN (");
            for (int i = 0; i < ids.size(); i++) {
                sqlBuilder.append("?");
                if (i < ids.size() - 1) sqlBuilder.append(",");
            }
            sqlBuilder.append(")");
            sql = sqlBuilder.toString();
            params = ids.toArray();
        }
        ResultSet rs = BaseDao.executeDQL(sql, params);
        try {
            while (rs != null && rs.next()) {
                items.add(buildCheckItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return items;
    }

    /**
     * ResultSet转CheckItem对象
     */
    private static CheckItem buildCheckItemFromResultSet(ResultSet rs) throws SQLException {
        return new CheckItem(
                rs.getInt("cid"),
                rs.getString("ccode"),
                rs.getString("cname"),
                rs.getString("refer_val"),
                rs.getString("unit"),
                rs.getTimestamp("create_date"),
                rs.getTimestamp("upd_date"),
                rs.getTimestamp("delete_date"),
                rs.getString("option_user"),
                rs.getString("status"),
                rs.getInt("user_id")
        );
    }

    /**
     * List<CheckItem>转二维数组（表格展示用）
     */
    private static Object[][] toArray(List<CheckItem> list) {
        Object[][] arr = new Object[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            CheckItem c = list.get(i);
            arr[i][0] = c.getCid();
            arr[i][1] = c.getCcode();
            arr[i][2] = c.getCname();
            arr[i][3] = c.getRefer_val();
            arr[i][4] = c.getUnit();
            arr[i][5] = c.getCreate_date();
            arr[i][6] = c.getUpd_date();
            arr[i][7] = c.getDelete_date();
            arr[i][8] = c.getOption_user();
            arr[i][9] = c.getStatus();
        }
        return arr;
    }
}