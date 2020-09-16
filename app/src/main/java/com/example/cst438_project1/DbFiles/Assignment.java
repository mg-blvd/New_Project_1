package com.example.cst438_project1.DbFiles;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return assignmentName.equals(that.assignmentName) &&
                score.equals(that.score) &&
                maxScore.equals(that.maxScore) &&
                courseId.equals(that.courseId) &&
                studentId.equals(that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentName, score, maxScore, courseId, studentId);
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }
}
