package manager.dao;

import manager.common.BaseDao;
import manager.pojo.CheckItem;
import manager.util.CommonUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckItemDao {
    // 建议字段顺序与CheckItem的toArray方法一致
    public static final Object[] columnNames = {
            "ID", "代号", "名称", "参考值", "单位", "创建时间", "更新时间", "删除时间", "创建人", "状态"
    };

    // 管理员查全部体检项
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
        // 推荐只查未删除且有效的数据
        // sql.append("AND (delete_date IS NULL OR delete_date = '') AND status = 1 ");

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
        // 建议先打印检查
        System.out.println("查到" + list.size() + "条检查项数据");
        return toArray(list);
    }

    // 普通用户查自己体检项
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
        // sql.append("AND (delete_date IS NULL OR delete_date = '') AND status = 1 ");

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

    // 新增体检项（带user_id）
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

    // 查重
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

    // 查详情
    public static CheckItem queryCheckItemByCid(Integer cid, Integer userId, boolean isAdmin) {
        System.out.println("调用queryCheckItemsByUserId时传入的userId = " + userId);
        Object[][] data = CheckItemDao.queryCheckItemsByUserId(userId, null, null);

        String sql;
        Object[] params;
        if (isAdmin) {
            sql = "SELECT * FROM checkitem WHERE cid = ?";
            params = new Object[]{cid};
        } else {
            sql = "SELECT * FROM checkitem WHERE cid = ? AND user_id = ?";
            params = new Object[]{cid, userId};
        }

        // 打印SQL和参数
        System.out.println("SQL: " + sql);
        System.out.print("参数: ");
        if (params != null) {
            for (Object p : params) {
                System.out.print(p + " ");
            }
        }
        System.out.println();

        ResultSet rs = BaseDao.executeDQL(sql, params);
        CheckItem checkItem = null;
        try {
            if (rs != null && rs.next()) {
                checkItem = buildCheckItemFromResultSet(rs);
                System.out.println("查到数据: " + checkItem);
            } else {
                System.out.println("未查到数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
        }
        return checkItem;
    }

    // 修改体检项（只能改自己的）
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

    // 删除体检项
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

    // ResultSet转CheckItem
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

    // toArray方法，避免CommonUtil出错
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