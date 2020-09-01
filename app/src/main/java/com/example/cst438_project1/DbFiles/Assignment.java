package com.example.cst438_project1.DbFiles;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assignment_table")
public class Assignment {
    private String assignmentName;
    private Double score;
    private Double maxScore;
    private Integer courseId;
    private Integer studentId;
    @PrimaryKey(autoGenerate = true)
    private Integer assignmentId;

    public Assignment(String assignmentName, Double score, Double maxScore, Integer courseId, Integer studentId) {
        this.assignmentName = assignmentName;
        this.score = score;
        this.maxScore = maxScore;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public Double getScore() {
        return score;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }
}
