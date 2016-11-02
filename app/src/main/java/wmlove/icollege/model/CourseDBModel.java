package wmlove.icollege.model;

import java.io.Serializable;

/**
 * Created by wmlove on 2016/10/19.
 */
public class CourseDBModel implements Serializable{

    private String id;

    private String courseCode;

    private String courseName;

    private String courseType;

    private String teacher;

    private float credit;

    private String day;

    private String startLine;

    private String deadLine;

    private String openWeek;

    private String closeWeek;

    private String isSingle;

    private String place;

    public CourseDBModel()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(String isSingle) {
        this.isSingle = isSingle;
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

    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getOpenWeek() {
        return openWeek;
    }

    public void setOpenWeek(String openWeek) {
        this.openWeek = openWeek;
    }

    public String getCloseWeek() {
        return closeWeek;
    }

    public void setCloseWeek(String closeWeek) {
        this.closeWeek = closeWeek;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "CourseDBModel{" +
                "id='" + id + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseType='" + courseType + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credit=" + credit +
                ", day='" + day + '\'' +
                ", startLine='" + startLine + '\'' +
                ", deadLine='" + deadLine + '\'' +
                ", openWeek='" + openWeek + '\'' +
                ", closeWeek='" + closeWeek + '\'' +
                ", isSingle='" + isSingle + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
