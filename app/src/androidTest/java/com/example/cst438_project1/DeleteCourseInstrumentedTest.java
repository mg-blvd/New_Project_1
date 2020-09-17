package com.example.cst438_project1;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DeleteCourseInstrumentedTest {

    /**
     * Simple test for the intent of delete course
     * @author Jonathan Quintero
     */
    @Test
    public void delete_course_incoming(){
        int intended_id = 2;

        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int test_id = DeleteCourse.DeleteCourseIntent(app, intended_id).getIntExtra(DeleteCourse.DELETE_COURSE_ID, -1);

        assertEquals(intended_id, test_id);
    }
}
