package controller;

import object.BaseTask;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseControllerTest {

    final String CONNECTION_STRING = "jdbc:sqlite::memory:";

    private Connection specificOpenConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.print("Connection not established");
            return null;
        }
    }

    private DatabaseController.TaskRecord genericTask(String text) {
        return new DatabaseController.TaskRecord(0, text, ZonedDateTime.now().toLocalDate().toString(), false);
    }

    @Test
    void isDatabaseBeingCreated() {
        Connection connection = specificOpenConnection();

        try {
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            System.out.println("Connection não existe ou foi fechada prematuramente.");
        }

        assertDoesNotThrow(() -> DatabaseController.setupDB(connection));

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.print("Connection não foi fechada.");
        }
    }

    @Test
    void isDatabaseReturningMultiple() {
        var connection = specificOpenConnection();
        DatabaseController.setupDB(connection);
        assertDoesNotThrow(() -> DatabaseController.storeTask(genericTask("Hello"), connection));
        assertDoesNotThrow(() -> DatabaseController.storeTask(genericTask("Hello"), connection));
        List<DatabaseController.TaskRecord> taskRecordList;
        taskRecordList = DatabaseController.getTasks(connection);
        assertNotNull(taskRecordList);
        assertEquals(2, taskRecordList.size());

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.print("Connection não foi fechada.");
        }
    }

    @Test
    void isDatabaseSearchingForData() {
        var connection = specificOpenConnection();
        DatabaseController.setupDB(connection);
        assertDoesNotThrow(() -> DatabaseController.storeTask(genericTask("Hello"), connection));
        var taskRecord = DatabaseController.getTask(connection, 1);
        assertNotNull(taskRecord);
        System.out.println(taskRecord);

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.print("Connection não foi fechada.");
        }
    }

    @Test
    void isDatabaseUpdatingData() {
        var connection = specificOpenConnection();
        DatabaseController.setupDB(connection);
        assertDoesNotThrow(() -> DatabaseController.storeTask(genericTask("Hello"), connection));
        var taskRecord = DatabaseController.getTask(connection, 1);
        assertNotNull(taskRecord);
        DatabaseController.updateTask(connection, genericTask("Task Test 001"), 1);
        assertEquals("Task Test 001", Objects.requireNonNull(DatabaseController.getTask(connection, 1)).task());
    }
}