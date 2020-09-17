package com.example.cst438_project1;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UpdateAssignmentInstrumentedTest {


    /**
     * Simple test for the intent of update assignment activity
     * @author Jonathan Quintero
     */
    @Test
    public void update_assignment_incoming(){
        int intended = 2;

        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int test_id = UpdateAssignment.UpdateAssignmentIntent(app, intended).getIntExtra(UpdateAssignment.UPDATE_ASSIGNMENT_ID, -1);

        assertEquals(intended, test_id);
    }
}
