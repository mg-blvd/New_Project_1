package com.example.cst438_project1.DbFiles;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private UserDao userDao;
    private StudentDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StudentDatabase.class).build();
        userDao = db.getUserDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void insert() {
        User user = new User("Bobby", "password1");
        userDao.insert(user);

        User findUser = userDao.getUserWithUsername(user.getUsername());
        assertEquals(findUser, user);
    }

    @Test
    public void delete() {
        User user = new User("Gone", "NoMore");
        userDao.insert(user);

        User findUser = userDao.getUserWithUsername(user.getUsername());
        assertEquals(user, findUser);

        userDao.delete(findUser);
        User deletedUser = userDao.getUserWithUsername(findUser.getUsername());
        assertNull(deletedUser);
    }

    @Test
    public void getUserPassword() {
        User user = new User("Rocky", "supercalifragilisticexpialidosus"); //Spelling may be incorrect
        userDao.insert(user);

        String findPassword = userDao.getUserPassword(user.getUsername());
        assertEquals(user.getPassword(), findPassword);
    }

    @Test
    public void getUserWithUsername() {
        User u1 = new User("Bobby", "password1");
        User u2 = new User("Ricky", "12345");
        userDao.insert(u1);
        userDao.insert(u2);

        User userArr[] = {
                userDao.getUserWithUsername(u1.getUsername()),
                userDao.getUserWithUsername(u2.getUsername())
        };

        assertEquals(u1, userArr[0]);
        assertEquals(u2, userArr[1]);
    }

    @Test
    public void getUserIdFromUsername() {
        User u1 = new User("Bobby", "password1");
        User u2 = new User("Ricky", "12345");
        userDao.insert(u1);
        userDao.insert(u2);

        User userArr[] = {
                userDao.getUserWithUsername(u1.getUsername()),
                userDao.getUserWithUsername(u2.getUsername())
        };

        assertEquals(1, userArr[0].getId());
        assertEquals(2, userArr[1].getId());
    }
}