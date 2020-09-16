package com.example.cst438_project1.DbFiles;


import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;

/**
 * This class will allow us to get the basic info for a course: it's name and the ID.
 * @author Misael Guijarro
 */
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CourseBasicInfo course = (CourseBasicInfo) obj;

        return getCourseName().equals(course.getCourseName()) &&
                getCourseId().equals(course.getCourseId());
    }
}
