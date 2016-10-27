package wmlove.bistu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmlove on 2016/10/17.
 */
public class CourseModel {
    
    private String id;

    
    private String courseCode;

    
    private String courseName;

    
    private String courseType;

    
    private String teacher;

    
    private float credit;

    
    private List<String> courseTime = new ArrayList<>();

    
    private List<String> place = new ArrayList<String>();

    public CourseModel() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public List<String> getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(List<String> courseTime) {
        this.courseTime = courseTime;
    }

    public List<String> getPlace() {
        return place;
    }

    public void setPlace(List<String> place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "CourseModel{" +
                "id='" + id + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseType='" + courseType + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credit=" + credit +
                ", courseTime=" + courseTime +
                ", place=" + place +
                '}';
    }
}
