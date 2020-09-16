package com.example.cst438_project1.DbFiles;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CourseDao {

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);

    @Update
    void update(Course course);

    @Query("SELECT * FROM course_table WHERE studentId = :userId")
    List<Course> getUserCourses(int userId);

    @Query("SELECT courseName, courseId FROM course_table WHERE studentId = :userId")
    List<CourseBasicInfo> getUserCourseBasicInfo(Integer userId);

    @Query("SELECT * FROM course_table WHERE courseId = :courseId")
    Course getCourseFromId(Integer courseId);
}
