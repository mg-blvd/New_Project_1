package com.example.cst438_project1.DbFiles;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AssignmentDaoTest {
    private UserDao userDao;
    private CourseDao courseDao;
    private AssignmentDao assignmentDao;
    private StudentDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudentDatabase.class).build();
        userDao = db.getUserDao();
        courseDao = db.getCourseDao();
        assignmentDao = db.getAssignmentDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void insert() {
        User user = new User("xxX_L33TSTU_Xxx", "lolpwn");
        userDao.insert(user);
        user = userDao.getUserWithUsername("xxX_L33TSTU_Xxx");

        Course c1 = new Course("Math", user.getId());
        courseDao.insert(c1);

        List<Course> courseList = courseDao.getUserCourses(user.getId());

        Assignment a1 = new Assignment("Assign 1",
                50.0,
                100.0,
                courseList.get(0).getCourseId(),
                user.getId());
        assignmentDao.insert(a1);
        List<Assignment> assignmentList = assignmentDao.getUserAllAssignments(user.getId());
        assertEquals(a1, assignmentList.get(0));
    }

    @Test
    public void delete() {
        User user = new User("xxX_L33TSTU_Xxx", "lolpwn");
        userDao.insert(user);
        user = userDao.getUserWithUsername("xxX_L33TSTU_Xxx");

        Course c1 = new Course("Math", user.getId());
        courseDao.insert(c1);

        List<Course> courseList = courseDao.getUserCourses(user.getId());

        Assignment a1 = new Assignment("Assign 1",
                50.0,
                100.0,
                courseList.get(0).getCourseId(),
                user.getId());
        assignmentDao.insert(a1);
        List<Assignment> assignmentList = assignmentDao.getUserAllAssignments(user.getId());
        assertEquals(a1, assignmentList.get(0));

        assignmentDao.delete(assignmentList.get(0));
        assignmentList = assignmentDao.getUserAllAssignments(user.getId());
        assertTrue(assignmentList.size() == 0);
    }

    @Test
    public void getUserAllAssignments() {
        String englishNames[] = {"Reading 1", "Essay", "Poem Analysis"};
        String mathNames[] = {"Homework 1", "Quiz 1", "Midterm"};
        double englishScores[] = {75.0, 98.0, 60.0};
        double mathScores[] = {100.0, 85.0, 80.0};

        User user = new User("xxX_L33TSTU_Xxx", "lolpwn");
        userDao.insert(user);
        user = userDao.getUserWithUsername("xxX_L33TSTU_Xxx");

        Course c1 = new Course("English", user.getId());
        courseDao.insert(c1);

        Course c2 = new Course("Math", user.getId());
        courseDao.insert(c2);

        List<Course> courseList = courseDao.getUserCourses(user.getId());

        List<Assignment> testList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Assignment english = populateAssignments(user.getId(), courseList.get(0).getCourseId(), englishNames[i], englishScores[i]);
            Assignment math = populateAssignments(user.getId(), courseList.get(1).getCourseId(), mathNames[i], mathScores[i]);
            testList.add(english);
            testList.add(math);
            assignmentDao.insert(english);
            assignmentDao.insert(math);
        }

        assertEquals(testList, assignmentDao.getUserAllAssignments(user.getId()));
    }

    @Test
    public void getUserSpecificCourseAssignments() {
        String englishNames[] = {"Reading 1", "Essay", "Poem Analysis"};
        String mathNames[] = {"Homework 1", "Quiz 1", "Midterm"};
        double englishScores[] = {75.0, 98.0, 60.0};
        double mathScores[] = {100.0, 85.0, 80.0};

        User user = new User("xxX_L33TSTU_Xxx", "lolpwn");
        userDao.insert(user);
        user = userDao.getUserWithUsername("xxX_L33TSTU_Xxx");

        Course c1 = new Course("English", user.getId());
        courseDao.insert(c1);

        Course c2 = new Course("Math", user.getId());
        courseDao.insert(c2);

        List<Course> courseList = courseDao.getUserCourses(user.getId());

        List<Assignment> testList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Assignment english = populateAssignments(user.getId(), courseList.get(0).getCourseId(), englishNames[i], englishScores[i]);
            Assignment math = populateAssignments(user.getId(), courseList.get(1).getCourseId(), mathNames[i], mathScores[i]);
            testList.add(math);
            assignmentDao.insert(english);
            assignmentDao.insert(math);
        }

        assertEquals(testList, assignmentDao.getUserSpecificCourseAssignments(user.getId(), courseList.get(1).getCourseId()));
    }

    @Ignore
    public Assignment populateAssignments(int userID, int courseID, String name, double score) {
        return new Assignment(name, score, 100.0, courseID, userID);
    }
}