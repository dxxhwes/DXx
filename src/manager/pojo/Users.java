package manager.pojo;


import java.sql.Date;

//存users的表数据
public class Users
{
    private int user_id;
    private String username;
    private String password;
    private String uname;
    private String sex;
    private String tel;
    private Date bir;

    public Users(int user_id, Date bir, String tel, String sex, String uname, String password, String username) {
        this.user_id = user_id;
        this.bir = bir;
        this.tel = tel;
        this.sex = sex;
        this.uname = uname;
        this.password = password;
        this.username = username;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setBir(Date bir) {
        this.bir = bir;
    }

    public int getUser_id() {
        return user_id;
    }

    public Date getBir() {
        return bir;
    }

    public String getTel() {
        return tel;
    }

    public String getSex() {
        return sex;
    }

    public String getUname() {
        return uname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
