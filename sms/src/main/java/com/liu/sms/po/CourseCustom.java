package com.liu.sms.po;

import java.util.List;

/**
 * Course扩展类    课程
 */
public class CourseCustom extends Course {

    //所属院系名
    private String collegeName;

    // 所属老师
    private String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
