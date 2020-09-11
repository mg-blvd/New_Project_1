package com.example.cst438_project1.DbFiles;


import androidx.room.ColumnInfo;

public class CourseBasicInfo {
    @ColumnInfo(name = "courseName")
    public String courseName;

    @ColumnInfo(name = "courseId")
    public Integer courseId;

    public String getCourseName() {
        return courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }
}
