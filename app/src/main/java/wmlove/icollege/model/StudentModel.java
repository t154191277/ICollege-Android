package wmlove.bistu.model;

/**
 * Created by wmlove on 2016/10/26.
 */
public class StudentModel {

    private String _class;

    private String department;

    private String id;

    private String idcart;

    private String marjoy;

    private String stuName;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcart() {
        return idcart;
    }

    public void setIdcart(String idcart) {
        this.idcart = idcart;
    }

    public String getMarjoy() {
        return marjoy;
    }

    public void setMarjoy(String marjoy) {
        this.marjoy = marjoy;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "_class='" + _class + '\'' +
                ", department='" + department + '\'' +
                ", id='" + id + '\'' +
                ", idcart='" + idcart + '\'' +
                ", marjoy='" + marjoy + '\'' +
                ", stuName='" + stuName + '\'' +
                '}';
    }
}
