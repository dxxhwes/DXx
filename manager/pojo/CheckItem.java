package manager.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 体检项（检查项）实体类
 * 对应checkitem表，封装所有字段属性及通用方法。
 */
public class CheckItem implements Serializable {
    private Integer cid;             // 主键ID
    private String ccode;            // 检查项代号
    private String cname;            // 检查项名称
    private String refer_val;        // 参考值
    private String unit;             // 单位
    private Timestamp create_date;   // 创建时间
    private Timestamp upd_date;      // 更新时间
    private Timestamp delete_date;   // 删除时间
    private String option_user;      // 操作人
    private String status;           // 状态
    private Integer userId;          // 所属用户ID

    /**
     * 全参数构造方法
     */
    public CheckItem(Integer cid, String ccode, String cname, String refer_val, String unit,
                     Timestamp create_date, Timestamp upd_date, Timestamp delete_date,
                     String option_user, String status, Integer userId) {
        this.cid = cid;
        this.ccode = ccode;
        this.cname = cname;
        this.refer_val = refer_val;
        this.unit = unit;
        this.create_date = create_date;
        this.upd_date = upd_date;
        this.delete_date = delete_date;
        this.option_user = option_user;
        this.status = status;
        this.userId = userId;
    }

    /**
     * 兼容旧代码的10参数构造方法（无userId）
     */
    public CheckItem(Integer cid, String ccode, String cname, String refer_val, String unit,
                     Timestamp create_date, Timestamp upd_date, Timestamp delete_date,
                     String option_user, String status) {
        this(cid, ccode, cname, refer_val, unit, create_date, upd_date, delete_date, option_user, status, null);
    }

    /**
     * 空参构造方法
     */
    public CheckItem() {}

    // Getter & Setter

    public Integer getCid() { return cid; }
    public void setCid(Integer cid) { this.cid = cid; }

    public String getCcode() { return ccode; }
    public void setCcode(String ccode) { this.ccode = ccode; }

    public String getCname() { return cname; }
    public void setCname(String cname) { this.cname = cname; }

    public String getRefer_val() { return refer_val; }
    public void setRefer_val(String refer_val) { this.refer_val = refer_val; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Timestamp getCreate_date() { return create_date; }
    public void setCreate_date(Timestamp create_date) { this.create_date = create_date; }

    public Timestamp getUpd_date() { return upd_date; }
    public void setUpd_date(Timestamp upd_date) { this.upd_date = upd_date; }

    public Timestamp getDelete_date() { return delete_date; }
    public void setDelete_date(Timestamp delete_date) { this.delete_date = delete_date; }

    public String getOption_user() { return option_user; }
    public void setOption_user(String option_user) { this.option_user = option_user; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    /**
     * 转为Object数组，顺序需与CheckItemDao.columnNames一致
     */
    public Object[] toArray() {
        return new Object[]{
                cid, ccode, cname, refer_val, unit, create_date, upd_date, delete_date, option_user, status
        };
    }

    @Override
    public String toString() {
        return "CheckItem{" +
                "cid=" + cid +
                ", ccode='" + ccode + '\'' +
                ", cname='" + cname + '\'' +
                ", refer_val='" + refer_val + '\'' +
                ", unit='" + unit + '\'' +
                ", create_date=" + create_date +
                ", upd_date=" + upd_date +
                ", delete_date=" + delete_date +
                ", option_user='" + option_user + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }

    /**
     * 主键判等，便于集合操作
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckItem)) return false;
        CheckItem that = (CheckItem) o;
        return Objects.equals(cid, that.cid);
    }

    @Override
    public int hashCode() {
        return cid != null ? cid.hashCode() : 0;
    }
}