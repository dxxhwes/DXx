package manager.dao;

import manager.common.BaseDao;
import manager.pojo.CheckItem;
import manager.util.CommonUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CheckItemDao
{
    public static final Object[] columnNames={"ID","代号","名称","参考值","单位","创建时间","更新时间","删除时间","创建人","状态"};

    public static Object[][] queryAllCheckItem(String cname,String code) {
        Object[] obj = null;
        List<CheckItem> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select * ");
        sql.append("from CheckItem ");

        boolean hasCname = cname != null || !cname.equals(""); // 条件查询
        boolean hasCode = code != null || !code.equals(""); // 条件查询
        List<Object> params = new ArrayList<>();
        if (hasCname || hasCode) { // 条件查询
            sql.append("where ");
            if (hasCname) {
                sql.append("cname like ? ");
                params.add("%" + cname + "%");
            }
            if (hasCode) {
                if (hasCname) {
                    sql.append("and ");
                }
                sql.append("ccode like ? ");
                params.add("%" + code + "%");
            }
        }

        ResultSet rs = BaseDao.executeDQL(sql.toString(), params.toArray());
        try {
            while (rs.next()) {
                CheckItem checkItem = new CheckItem(rs.getInt("cid"),
                        rs.getString("ccode"),
                        rs.getString("cname"),
                        rs.getString("refer_val"),
                        rs.getString("unit"),
                        rs.getDate("create_date"),
                        rs.getDate("upd_date"),
                        rs.getDate("delete_date"),
                        rs.getString("option_user"),
                        rs.getString("status"));
                list.add(checkItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonUtil.toArray(list);
    }

    public static int addCheckItem(CheckItem checkItem)
    {
        String sql="INSERT into checkitem(ccode,cname,refer_val,unit,create_date,option_user,status)\n" +
                "VALUES (?,?,?,?,now(),?,1)";
        Object[] params={
                checkItem.getCcode(),
                checkItem.getCname(),
                checkItem.getRefer_val(),
                checkItem.getUnit(),
                checkItem.getOption_user()
        };
        return BaseDao.executeDML(sql,params);
    }

    //根据ccode查重
    public static CheckItem queryCheckItemByCcode(String ccode)
    {
        String sql="select * from checkitem where ccode=?";
        Object[] params={ccode};
        ResultSet rs = BaseDao.executeDQL(sql,params);
        CheckItem checkItem = null;
        try {
            while (rs.next()) {
                checkItem = new CheckItem(rs.getInt("cid"),
                        rs.getString("ccode"),
                        rs.getString("cname"),
                        rs.getString("refer_val"),
                        rs.getString("unit"),
                        rs.getDate("create_date"),
                        rs.getDate("upd_date"),
                        rs.getDate("delete_date"),
                        rs.getString("option_user"),
                        rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkItem;
    }

    //cid查询
    public static CheckItem queryCheckItemByCid(Integer cid)
    {
        String sql="select * from checkitem where cid=?";
        Object[] params={cid};
        ResultSet rs = BaseDao.executeDQL(sql,params);
        CheckItem checkItem = null;
        try {
            while (rs.next()) {
                checkItem = new CheckItem(rs.getInt("cid"),
                        rs.getString("ccode"),
                        rs.getString("cname"),
                        rs.getString("refer_val"),
                        rs.getString("unit"),
                        rs.getDate("create_date"),
                        rs.getDate("upd_date"),
                        rs.getDate("delete_date"),
                        rs.getString("option_user"),
                        rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkItem;
    }

    //修改
    public static int updateCheckItem(CheckItem checkItem)
    {
        String sql="update checkitem set ccode = ?,cname=? , refer_val = ?,unit = ?,upd_date = now() \n" +
                "where cid = ?";
        Object[] params={
                checkItem.getCcode(),
                checkItem.getCname(),
                checkItem.getRefer_val(),
                checkItem.getUnit(),
                checkItem.getcId()
        };
        return BaseDao.executeDML(sql,params);
    }

    public static int deleteCheckItem(Integer cid)
    {
        String sql="delete from checkitem where cid=?";
        Object[] params={cid};
        return BaseDao.executeDML(sql,params);
    }
}
