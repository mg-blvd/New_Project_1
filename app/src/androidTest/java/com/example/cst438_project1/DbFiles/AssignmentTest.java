package com.example.cst438_project1.DbFiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignmentTest {

    @Test
    public void setAssignmentId() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        Integer x = 111;
        assignment.setAssignmentId(x);
        assertEquals(x, assignment.getAssignmentId());
    }

    @Test
    public void getAssignmentName() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        assertEquals("Homework", assignment.getAssignmentName());
    }

    @Test
    public void getScore() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        assertTrue(50.0 == assignment.getScore());
    }

    @Test
    public void getMaxScore() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        assertTrue(100.0 == assignment.getMaxScore());
    }

    @Test
    public void getCourseId() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        Integer x = 777;
        assertEquals(x, assignment.getCourseId());
    }

    @Test
    public void getStudentId() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        Integer x = 999;
        assertEquals(x, assignment.getStudentId());
    }

    /**
     * Testing name setter
     * @author Misael Guijarro
     */
    @Test
    public void setAssignmentName() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        assignment.setAssignmentName("Completed HW");
        assertEquals("Completed HW", assignment.getAssignmentName());
    }

    /**
     * testing score setter
     * @author Misael Guijarro
     */
    @Test
    public void setAssignmentScore() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        assignment.setMaxScore(5.5);
        assertEquals(5.5, (double) assignment.getMaxScore(), 0.0002);
    }

    /**
     * testing max score setter
     * @author Misael Guijarro
     */
    @Test
    public void setAssignmentMaxScore() {
        Assignment assignment = new Assignment("Homework", 50.0, 100.0, 777, 999);
        assignment.setMaxScore(5.5);
        assertEquals(5.5, (double) assignment.getMaxScore(), 0.0002);
    }


}