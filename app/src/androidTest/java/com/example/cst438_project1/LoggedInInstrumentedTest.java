package com.example.cst438_project1;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoggedInInstrumentedTest {

    /**
     * Simple test for the intent of Logged In activity
     * @author Jonathan Quintero
     */
    @Test
    public void logged_in_intent_test(){
        int intended = 2;
        Context app = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int test_id = LoggedInHome.LoggedInIntent(app, 2).getIntExtra(LoggedInHome.LOGGED_IN_ID, -1);

        assertEquals(intended, test_id);
    }
}
