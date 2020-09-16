package com.example.cst438_project1.DbFiles;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AssignmentDao {

    @Insert
    void insert(Assignment assignment);

    @Delete
    void delete(Assignment assignment);

    @Update
    void update(Assignment assignment);

    @Query("SELECT * from assignment_table WHERE studentId = :userId")
    List<Assignment> getUserAllAssignments(Integer userId);

    @Query("SELECT * from assignment_table WHERE studentId = :userId AND courseId = :courseId")
    List<Assignment> getUserSpecificCourseAssignments(Integer userId, Integer courseId);

}
