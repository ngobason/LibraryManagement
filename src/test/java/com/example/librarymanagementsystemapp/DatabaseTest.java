package com.example.librarymanagementsystemapp;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void testGetConnection_returnsConnection() {
        Database db = new Database();
        Connection conn = db.getConnection();
        assertNotNull(conn, "Connection should not be null");
        try {
            assertFalse(conn.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("SQLException thrown: " + e.getMessage());
        }
    }

    @Test
    void testMultipleConnections() {
        Database db = new Database();
        Connection conn1 = db.getConnection();
        Connection conn2 = db.getConnection();

        assertNotNull(conn1);
        assertNotNull(conn2);
        assertNotSame(conn1, conn2, "Each call should return a new Connection instance");
    }
}
