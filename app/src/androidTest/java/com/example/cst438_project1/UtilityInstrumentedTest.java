package com.example.cst438_project1;

import android.content.Context;

import com.example.cst438_project1.DbFiles.Assignment;
import com.example.cst438_project1.DbFiles.AssignmentDao;
import com.example.cst438_project1.DbFiles.Course;
import com.example.cst438_project1.DbFiles.CourseDao;
import com.example.cst438_project1.DbFiles.StudentDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UtilityInstrumentedTest {
    CourseDao mCourseDao;
    private AssignmentDao assignmentDao;
    private StudentDatabase db;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudentDatabase.class).build();
        mCourseDao = db.getCourseDao();
        assignmentDao = db.getAssignmentDao();
    }

    /**
     * Makes sure the utility percentage function is working
     * @author Misael Guijarro
     */
    @Test
    public void testPercentGrade() {
        assertEquals(80.00, Utility.computeGrade(4.0, 5.0), 0.001);
        assertEquals(100.00, Utility.computeGrade(5.0, 5.0), 0.001);
        assertEquals(0, Utility.computeGrade(0.0, 5.0), 0.001);
    }

    /**
     * Makes sure the letter grade and message are correct
     * @author Misael Guijarro
     */
    @Test
    public void testLetterGrade() {
        assertEquals("A - 100.0", Utility.getLetterGrade(100.0));
        assertEquals("A - 90.0", Utility.getLetterGrade(90.0));
        assertEquals("B - 89.99", Utility.getLetterGrade(89.99));
        assertEquals("B - 85.0", Utility.getLetterGrade(85.0));
        assertEquals("B - 80.0", Utility.getLetterGrade(80.0));
        assertEquals("C - 79.99", Utility.getLetterGrade(79.99));
        assertEquals("C - 75.0", Utility.getLetterGrade(75.0));
        assertEquals("C - 70.0", Utility.getLetterGrade(70.0));
        assertEquals("D - 69.99", Utility.getLetterGrade(69.99));
        assertEquals("D - 65.0", Utility.getLetterGrade(65.0));
        assertEquals("D - 60.0", Utility.getLetterGrade(60.0));
        assertEquals("F - 59.99", Utility.getLetterGrade(59.99));
        assertEquals("F - 25.0", Utility.getLetterGrade(25.0));
        assertEquals("F - 0.0", Utility.getLetterGrade(0.0));
    }

    /**
     * making sure that the grades get updated correctly when calling the recalculate grade function
     * @author Misael Guijarro
     */
    @Test
    public void testGradeUpdateIsWorking() {
        List<Assignment> assignments = new ArrayList<>();
        //Creating a tester course and inserting to dao
        Course course = new Course("tester", 0.0, 500);
        mCourseDao.insert(course);
        List<Course> courses = mCourseDao.getUserCourses(500);
        int courseId = courses.get(0).getCourseId();

        //creating assignments to pass into dao
        assignments.add(new Assignment("H1", 4.0,
                5.0, courseId, 500));
        assignments.add(new Assignment("H2", 5.0,
                5.0, courseId, 500));
        assignments.add(new Assignment("H3", 4.0,
                5.0, courseId, 500));

        for(Assignment assignment : assignments) {
            assignmentDao.insert(assignment);
        }

        assignments = assignmentDao.getUserSpecificCourseAssignments(500, courseId);

        //Recalculate grade after adding some assignment to the dao
        Utility.recalculateGrade(courses.get(0), assignments, mCourseDao);
        Course updatedCourse = mCourseDao.getCourseFromId(courseId);
        assertEquals(86.66, updatedCourse.getCurrentGrade(), 0.02);

        //Removing an assignment from the dao
        assignmentDao.delete(assignments.get(1));
        assignments = assignmentDao.getUserSpecificCourseAssignments(500, courseId);

        //recalculating grade again
        Utility.recalculateGrade(updatedCourse, assignments, mCourseDao);
        updatedCourse = mCourseDao.getCourseFromId(courseId);
        assertEquals(80.00, updatedCourse.getCurrentGrade(), 0.02);
    }
}
