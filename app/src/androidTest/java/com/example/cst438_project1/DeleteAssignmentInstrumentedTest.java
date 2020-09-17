package com.example.cst438_project1;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class DeleteAssignmentInstrumentedTest {
    Context mContext;

    @Before
    public void init() {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    /**
     * made a test that makes makes sure we can get an intent
     */
    @Test
    public void goToDelete() {
        int intended = 2;
        int test_id = DeleteAssignment.DeleteAssignmentIntent(mContext, intended).getIntExtra(DeleteAssignment.DELETE_ASSIGNMENT_ID, -1);
        assertEquals(intended, test_id);
    }

}
