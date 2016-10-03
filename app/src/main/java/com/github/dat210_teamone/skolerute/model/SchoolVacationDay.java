package com.github.dat210_teamone.skolerute.model;

import java.util.Date;

/**
 * Created by Nicolas on 14.09.2016.
 */
public class SchoolVacationDay implements java.io.Serializable {
    private Date date;
    private String name;
    private boolean studentDay;
    private boolean teacherDay;
    private boolean sfoDay;
    private String comment;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStudentDay() {
        return studentDay;
    }

    public void setStudentDay(boolean studentDay) {
        this.studentDay = studentDay;
    }

    public boolean isTeacherDay() {
        return teacherDay;
    }

    public void setTeacherDay(boolean teacherDay) {
        this.teacherDay = teacherDay;
    }

    public boolean isSfoDay() {
        return sfoDay;
    }

    public void setSfoDay(boolean sfoDay) {
        this.sfoDay = sfoDay;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
