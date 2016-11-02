package wmlove.icollege.model;

import java.io.Serializable;

/**
 * Created by wmlove on 2016/10/13.
 */
public class UserModel implements Serializable{
    private String id;
    private String passwd;

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
