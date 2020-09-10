package com.example.cst438_project1.DbFiles;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Assignment.class, Course.class}, version = 2, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {
    public static final String DB_NAME = "com.example.cst438_project1.DbFiles.STUDENT_GRADES_DB";
    public static final String USER_TABLE = "com.example.cst438_project1.DbFiles.USER_TABLE";
    public static final String ASSIGNMENT_TABLE = "com.example.cst438_project1.DbFiles.ASSIGNMENT_TABLE";
    public static final String COURSE_TABLE = "com.example.cst438_project1.DbFiles.COURSE_TABLE";

    public abstract UserDao getUserDao();
    public abstract AssignmentDao getAssignmentDao();
    public abstract CourseDao getCourseDao();



}
