package com.example.cst438_project1.DbFiles;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CourseDaoTest {
    private UserDao userDao;
    private CourseDao courseDao;
    private StudentDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudentDatabase.class).build();
        userDao = db.getUserDao();
        courseDao = db.getCourseDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void insert() {
        User u1 = new User("Bobby", "password1");
        userDao.insert(u1);
        u1 = userDao.getUserWithUsername("Bobby");

        Course c1 = new Course("Math", u1.getId());
        courseDao.insert(c1);


        List<Course> listCourses = courseDao.getUserCourses(u1.getId());
        assertEquals(c1, listCourses.get(0));
    }

    @Test
    public void delete() {
        User user = new User("xxX_L33TSTU_Xxx", "lolpwn");
        userDao.insert(user);
        user = userDao.getUserWithUsername("xxX_L33TSTU_Xxx");

        Course c1 = new Course("English", user.getId());
        courseDao.insert(c1);
        List<Course> listCourses = courseDao.getUserCourses(user.getId());
        Course returnedCourse = listCourses.get(0);
        assertEquals(c1.getStudentId(), returnedCourse.getStudentId());
        assertEquals(c1.getCourseName(), returnedCourse.getCourseName());

        courseDao.delete(listCourses.get(0));
        listCourses = courseDao.getUserCourses(user.getId());
        assertTrue(listCourses.size() == 0);
    }

    @Test
    public void update() {
        User user = new User("xxX_L33TSTU_Xxx", "lolpwn");
        userDao.insert(user);
        user = userDao.getUserWithUsername("xxX_L33TSTU_Xxx");

        Course c1 = new Course("English", user.getId());
        courseDao.insert(c1);
        List<Course> listCourses = courseDao.getUserCourses(user.getId());
        assertEquals(c1, listCourses.get(0));

        c1 = listCourses.get(0);
        c1.setCurrentGrade(90);
        assertNotEquals(c1, courseDao.getUserCourses(user.getId()).get(0));

        courseDao.update(c1);
        List<Course> newCourses = courseDao.getUserCourses(user.getId());
        assertEquals(c1, newCourses.get(0));
    }

    @Test
    public void getUserCourses() {
        User user = new User("xxX_L33TSTU_Xxx", "lolpwn");
        userDao.insert(user);
        user = userDao.getUserWithUsername("xxX_L33TSTU_Xxx");

        Course c1 = new Course("Math", user.getId());
        Course c2 = new Course("English", user.getId());
        Course c3 = new Course("Science", user.getId());
        courseDao.insert(c1);
        courseDao.insert(c2);
        courseDao.insert(c3);

        List<Course> listCourses = courseDao.getUserCourses(user.getId());
        assertEquals(c1, listCourses.get(0));
        assertEquals(c2, listCourses.get(1));
        assertEquals(c3, listCourses.get(2));
    }

    /**
     * Testing the basic course info return
     * @author Misael Guijarro
     */
    @Test
    public void getUserCourseBasicInfo() {
        User user = new User("basic_tester", "basic");
        userDao.insert(user);

        Course c1 = new Course("example1", user.getId());
        Course c2 = new Course("example2", user.getId());
        Course c3 = new Course("example3", user.getId());
        courseDao.insert(c1);
        courseDao.insert(c2);
        courseDao.insert(c3);

        List<Course> fullCourseInfo = courseDao.getUserCourses(user.getId());
        List<CourseBasicInfo> basicInfos = courseDao.getUserCourseBasicInfo(user.getId());

        for(int i = 0; i < 3; i++) {
            Course curCourse = fullCourseInfo.get(i);
            CourseBasicInfo shortVer = basicInfos.get(i);
            String courseName = curCourse.getCourseName();
            Integer courseId = curCourse.getCourseId();

            assertTrue(courseName.equals(shortVer.getCourseName()));
            assertTrue(courseId == shortVer.getCourseId());
        }
    }

    /**
     * Tests the get course from course id function.
     * @author Misael Guijarro
     */
    @Test
    public void getCourseFromId(){
        User user = new User("courseIdTester", "courseId");
        userDao.insert(user);

        Course c1 = new Course("example1", user.getId());
        Course c2 = new Course("example2", user.getId());
        Course c3 = new Course("example3", user.getId());
        courseDao.insert(c1);
        courseDao.insert(c2);
        courseDao.insert(c3);
        List<Course> fullCourseInfo = courseDao.getUserCourses(user.getId());

        for(Course course : fullCourseInfo) {
            Course queriedCourse = courseDao.getCourseFromId(course.getCourseId());
            assertEquals(queriedCourse, course);
        }
    }
}