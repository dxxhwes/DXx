package manager.dao;

import manager.common.BaseDao;
import manager.pojo.CheckItem;
import manager.util.CommonUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckItemDao {
    public static final Object[] columnNames = {"ID", "代号", "名称", "参考值", "单位", "创建时间", "更新时间", "删除时间", "创建人", "状态"};

    // 1. 管理员查全部体检项
    public static Object[][] queryAllCheckItem(String cname, String code) {
        List<CheckItem> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from checkitem where 1=1 ");

        List<Object> params = new ArrayList<>();
        if (cname != null && !cname.trim().isEmpty()) {
            sql.append("and cname like ? ");
            params.add("%" + cname.trim() + "%");
        }
        if (code != null && !code.trim().isEmpty()) {
            sql.append("and ccode like ? ");
            params.add("%" + code.trim() + "%");
        }

        // 可选：只显示未删除且有效的数据（根据业务需求启用）
        // sql.append("and (delete_date is null or delete_date = '') and status = 1 ");

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
        return CommonUtil.toArray(list);
    }

    // 2. 普通用户查自己体检项
    public static Object[][] queryCheckItemsByUserId(int userId, String cname, String code) {
        List<CheckItem> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from checkitem where user_id = ? ");

        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (cname != null && !cname.trim().isEmpty()) {
            sql.append("and cname like ? ");
            params.add("%" + cname.trim() + "%");
        }
        if (code != null && !code.trim().isEmpty()) {
            sql.append("and ccode like ? ");
            params.add("%" + code.trim() + "%");
        }

        // 可选：只显示未删除且有效的数据（根据业务需求启用）
        // sql.append("and (delete_date is null or delete_date = '') and status = 1 ");

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
        return CommonUtil.toArray(list);
    }

    // 3. 新增体检项（带user_id）
    public static int addCheckItem(CheckItem checkItem) {
        String sql = "insert into checkitem(user_id, ccode, cname, refer_val, unit, create_date, option_user, status) " +
                "values (?, ?, ?, ?, ?, now(), ?, 1)";
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

    // 4. 查重（管理员查全表，用户查自己）
    public static CheckItem queryCheckItemByCcode(String ccode, Integer userId, boolean isAdmin) {
        String sql;
        Object[] params;
        if (isAdmin) {
            sql = "select * from checkitem where ccode = ?";
            params = new Object[]{ccode};
        } else {
            sql = "select * from checkitem where ccode = ? and user_id = ?";
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

    // 5. 查详情（管理员查全表，用户查自己）
    public static CheckItem queryCheckItemByCid(Integer cid, Integer userId, boolean isAdmin) {
        String sql;
        Object[] params;
        if (isAdmin) {
            sql = "select * from checkitem where cid = ?";
            params = new Object[]{cid};
        } else {
            sql = "select * from checkitem where cid = ? and user_id = ?";
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

    // 6. 修改体检项（只能改自己的）
    public static int updateCheckItem(CheckItem checkItem) {
        String sql = "update checkitem set ccode = ?, cname = ?, refer_val = ?, unit = ?, upd_date = now() " +
                "where cid = ? and user_id = ?";
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

    // 7. 删除体检项（管理员可删任意，用户只能删自己）
    public static int deleteCheckItem(Integer cid, Integer userId) {
        String sql;
        Object[] params;
        if (userId == null) {
            // 管理员删除，不加user_id条件
            sql = "delete from checkitem where cid = ?";
            params = new Object[]{cid};
        } else {
            // 普通用户只能删自己的
            sql = "delete from checkitem where cid = ? and user_id = ?";
            params = new Object[]{cid, userId};
        }
        return BaseDao.executeDML(sql, params);
    }

    // 统一的ResultSet转CheckItem方法
    private static CheckItem buildCheckItemFromResultSet(ResultSet rs) throws SQLException {
        return new CheckItem(
                rs.getInt("cid"),
                rs.getString("ccode"),
                rs.getString("cname"),
                rs.getString("refer_val"),
                rs.getString("unit"),
                rs.getDate("create_date"),
                rs.getDate("upd_date"),
                rs.getDate("delete_date"),
                rs.getString("option_user"),
                rs.getString("status"),
                rs.getInt("user_id")
        );
    }
}