package manager.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//发送sql
public class BaseDao
{
    //增删改 查

    //查询返回结果会在resultset(结果集
    public static ResultSet executeDQL(String sql,Object[] params)
    {
        Connection conn=DBConn.getConn();
        PreparedStatement st=null;
        ResultSet rs=null;

        try{
            st=conn.prepareStatement(sql);

            if(params!=null)
            {
                for(int i=0;i<params.length;i++)
                {
                    st.setObject(i+1,params[i]);
                }
            }
            rs=st.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }


    //增删改 返回值int类型 代表更改的行数 更改失败返回0以下
    public static int executeDML(String sql,Object... params)
    {
        Connection conn=DBConn.getConn();
        PreparedStatement st=null;
        int r=0;

        try{
            st=conn.prepareStatement(sql);

            if(params!=null)
            {
                for(int i=0;i<params.length;i++)
                {
                    st.setObject(i+1,params[i]);
                }
            }
            r=st.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return r;
    }


}
