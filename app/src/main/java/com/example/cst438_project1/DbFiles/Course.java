package com.example.cst438_project1.DbFiles;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {
    private String courseName;
    private double currentGrade;
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int studentId;

    public Course(String courseName, double currentGrade, int studentId) {
        this.courseName = courseName;
        this.currentGrade = currentGrade;
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getCurrentGrade() {
        return currentGrade;
    }

    public int getStudentId() {
        return studentId;
    }
}
