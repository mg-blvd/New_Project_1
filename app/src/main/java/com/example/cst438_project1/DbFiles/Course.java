package com.example.cst438_project1.DbFiles;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName = "course_table")
public class Course {
    private String courseName;
    private double currentGrade;
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int studentId;

    public Course(String courseName, int studentId){
        this.courseName = courseName;
        this.studentId = studentId;
    }

    @Ignore
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

    public void setCurrentGrade(double currentGrade) {
        this.currentGrade = currentGrade;
    }

    public int getStudentId() {
        return studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Double.compare(course.currentGrade, currentGrade) == 0 &&
                studentId == course.studentId &&
                courseName.equals(course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, currentGrade, studentId);
    }
}
