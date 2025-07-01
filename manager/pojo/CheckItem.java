package manager.pojo;

import java.sql.Date;

//CheckItem表数据
public class CheckItem
{
    private Integer cid;
    private String ccode;
    private String cname;
    private String refer_val;
    private String unit;
    private Date create_date;
    private Date upd_date;
    private Date delete_date;
    private String option_user;
    private String status;
    private Integer userId; // 新增字段

    // 新增：带userId的构造方法（11参数）
    public CheckItem(Integer cid, String ccode, String cname, String refer_val, String unit,
                     Date create_date, Date upd_date, Date delete_date,
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

    // 兼容旧代码：原10参数构造方法
    public CheckItem(Integer cid, String ccode, String cname, String refer_val, String unit,
                     Date create_date, Date upd_date, Date delete_date,
                     String option_user, String status) {
        this(cid, ccode, cname, refer_val, unit, create_date, upd_date, delete_date, option_user, status, null);
    }

    // 空参构造方法
    public CheckItem() {}

    public Integer getcId() {
        return cid;
    }

    public String getCcode() {
        return ccode;
    }

    public String getCname() {
        return cname;
    }

    public String getRefer_val() {
        return refer_val;
    }

    public String getUnit() {
        return unit;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public Date getUpd_date() {
        return upd_date;
    }

    public Date getDelete_date() {
        return delete_date;
    }

    public String getOption_user() {
        return option_user;
    }

    public String getStatus() {
        return status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setcId(Integer cid) {
        this.cid = cid;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setRefer_val(String refer_val) {
        this.refer_val = refer_val;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public void setUpd_date(Date upd_date) {
        this.upd_date = upd_date;
    }

    public void setDelete_date(Date delete_date) {
        this.delete_date = delete_date;
    }

    public void setOption_user(String option_user) {
        this.option_user = option_user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
