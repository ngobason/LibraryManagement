package com.example.librarymanagementsystemapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "testUser", "Test Full Name", "1234567890", "Male", "test@gmail.com");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, user.getId());
        assertEquals("testUser", user.getUserName());
        assertEquals("Test Full Name", user.getFullName());
        assertEquals("1234567890", user.getPhone());
        assertEquals("Male", user.getGender());
        assertEquals("test@gmail.com", user.getGmail());
    }

    @Test
    void testSetId() {
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    void testSetPhone() {
        user.setPhone("0987654321");
        assertEquals("0987654321", user.getPhone());
    }

    @Test
    void testConstructorWithNullValues() {
        User nullUser = new User(0, null, null, null, null, null);
        assertNull(nullUser.getUserName());
        assertNull(nullUser.getFullName());
        assertNull(nullUser.getPhone());
        assertNull(nullUser.getGender());
        assertNull(nullUser.getGmail());
    }

    @Test
    void testSetPhoneWithNull() {
        user.setPhone(null);
        assertNull(user.getPhone());
    }
}