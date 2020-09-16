package com.example.cst438_project1;

import com.example.cst438_project1.DbFiles.CourseBasicInfo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CourseBasicInfoTest {
    CourseBasicInfo b1, b2;

    /**
     * Setup for the other test
     * @author Misael Guijarro
     */
    @Before
    public void beforeEverythingElse()
    {
        b1 = new CourseBasicInfo();
        b1.courseName = "Maths";
        b1.courseId = 1;

        b2 = new CourseBasicInfo();
        b2.courseId = 2;
        b2.courseName = "History";
    }

    /**
     * tests the get courseName function
     * @author Misael Guijarro
     */
    @Test
    public void getCourseName() {
        assertEquals("Maths", b1.getCourseName());
        assertEquals("History", b2.getCourseName());
    }

    @Test
    public void getCourseId() {
        assertEquals(1,(int) b1.getCourseId());
        assertEquals(2, (int) b2.getCourseId());
    }

    /**
     * Testing the equals
     * @author Misael Guijarro
     */
    @Test
    public void equals() {
        CourseBasicInfo new1 = b1;
        CourseBasicInfo new2 = b2;
        CourseBasicInfo new3 = new CourseBasicInfo();
        new3.courseName = "Maths";
        new3.courseId = 1;
        CourseBasicInfo new4 = new CourseBasicInfo();
        new4.courseName = "History";
        new4.courseId = 2;

        assertEquals(new1, b1);
        assertEquals(new2, b2);
        assertEquals(new4, b2);
        assertEquals(new3, b1);
    }
}
