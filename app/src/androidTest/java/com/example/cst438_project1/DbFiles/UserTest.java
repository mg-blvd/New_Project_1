package com.example.cst438_project1.DbFiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void setId() {
        User user = new User("Bobby", "password1");
        user.setId(5);
        assertEquals(5, user.getId());
    }

    @Test
    public void getUsername() {
        User user = new User("Bobby", "password1");
        assertEquals("Bobby", user.getUsername());
    }

    @Test
    public void getPassword() {
        User user = new User("Bobby", "password1");
        assertEquals("password1", user.getPassword());
    }
}