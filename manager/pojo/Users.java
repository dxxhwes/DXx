package manager.pojo;

import java.sql.Date;

public class Users {
    private int user_id;
    private Date bir;
    private String tel;
    private String sex;
    private String uname;
    private String password;
    private String username;

    // 无参构造函数
    public Users() {
    }

    // 全参构造函数
    public Users(int user_id, Date bir, String tel, String sex, String uname, String password, String username) {
        this.user_id = user_id;
        this.bir = bir;
        this.tel = tel;
        this.sex = sex;
        this.uname = uname;
        this.password = password;
        this.username = username;
    }

    // Getter 和 Setter 方法
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getBir() {
        return bir;
    }

    public void setBir(Date bir) {
        this.bir = bir;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}