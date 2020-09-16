package com.example.cst438_project1.DbFiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourseTest {

    @Test
    public void setCourseId() {
        Course course = new Course("English", 999);
        course.setCourseId(6);
        assertEquals(6, course.getCourseId());
    }

    @Test
    public void getCourseName() {
        Course course = new Course("English", 999);
        assertEquals("English", course.getCourseName());
    }

    @Test
    public void getCurrentGrade() {
        Course course = new Course("English", 50.0, 999);
        assertTrue(50.0 == course.getCurrentGrade());
    }

    @Test
    public void setCurrentGrade() {
        Course course = new Course("English", 50.0, 999);
        course.setCurrentGrade(100.0);
        assertTrue(100.0 == course.getCurrentGrade());
    }

    @Test
    public void getStudentId() {
        Course course = new Course("English", 50.0, 999);
        assertEquals(999, course.getStudentId());
    }
}