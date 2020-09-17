package com.example.cst438_project1;

import com.example.cst438_project1.DbFiles.Assignment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateAssignmentUnitTest {
    public UpdateAssignment updateAssignment;

    /**
     * Testing whether update assignment can detect a null Assignment
     * @author Jonathan Quintero
     */
    @Test
    public void update_assignment_assignments_exist(){
        Assignment tester = new Assignment("testAssignment", 10.0, 10.0, 1, 1);
        updateAssignment = new UpdateAssignment();
        assertEquals(true, updateAssignment.check_if_possible(tester));
    }
}
