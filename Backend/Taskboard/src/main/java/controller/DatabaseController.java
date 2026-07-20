package controller;

import object.BaseTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

    public static final String CONNECTION_STRING = "jdbc:sqlite:task.db";
    public static final String INITIAL_TABLE = "CREATE TABLE IF NOT EXISTS tasks (id integer PRIMARY KEY, task text NOT NULL, task_change_date text NOT NULL, is_completed boolean NOT NULL);";
    public static final String INSERT_TASK = "INSERT INTO tasks(task, task_change_date, is_completed) VALUES (?, ?, ?)";
    public static final String SELECT_TASK_GLOBAL = "SELECT id, task, task_change_date, is_completed FROM tasks";
    public static final String SELECT_TASK_SPECIFIC_ID = "SELECT id, task, task_change_date, is_completed FROM tasks WHERE id = ?";
    public static final String SELECT_TASK_SPECIFIC_TEXT = "SELECT id, task, task_change_date, is_completed FROM tasks WHERE task = ?";
    public static final String DELETE_TASK = "DELETE FROM tasks WHERE id = ?";
    public static final String UPDATE_TASK = "UPDATE tasks SET task = ?, task_change_date = ?, is_completed = ? WHERE id = ?";

    public record TaskRecord(int id, String task, String date, boolean is_completed) {

    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.print("Connection não foi estabelecida.");
            return null;
        }
    }

    public static void setupDB(Connection connection) {
        try {
            var statement = connection.createStatement();
             statement.execute(INITIAL_TABLE);
             System.out.println("A tabela principal (tasks) foi criada.");
        }
             catch (SQLException e) {
                 throw new RuntimeException(e);
        }
    }

    public static TaskRecord searchTask(Connection connection, String text) {
        try (var statement = connection.prepareStatement(SELECT_TASK_SPECIFIC_TEXT)) {
            statement.setString(1, text);
            var resultSet = statement.executeQuery();
            return new TaskRecord(
                    resultSet.getInt("id"),
                    resultSet.getString("task"),
                    resultSet.getString("task_change_date"),
                    resultSet.getBoolean("is_completed"));
        } catch (SQLException e) {
            System.out.print("A tabela foi incapaz de ser selecionada.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<TaskRecord> getTasks(Connection connection) {
        var result = new ArrayList<TaskRecord>();

        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery(SELECT_TASK_GLOBAL)) {
            while (resultSet.next()) {
                var taskRecord = new TaskRecord(
                        resultSet.getInt("id"),
                        resultSet.getString("task"),
                        resultSet.getString("task_change_date"),
                        resultSet.getBoolean("is_completed"));

                result.add(taskRecord);
            }

        } catch (SQLException e) {
            System.out.print("A tabela foi incapaz de ser selecionada.");
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static TaskRecord getTask(Connection connection, int id) {
        try (var statement = connection.prepareStatement(SELECT_TASK_SPECIFIC_ID)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            return new TaskRecord(
                    resultSet.getInt("id"),
                    resultSet.getString("task"),
                    resultSet.getString("task_change_date"),
                    resultSet.getBoolean("is_completed"));
        } catch (SQLException e) {
            System.out.print("A tabela foi incapaz de ser selecionada.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void updateTask(Connection connection, TaskRecord taskRecord, int id) {
        try (var statement = connection.prepareStatement(UPDATE_TASK)) {
            statement.setString(1, taskRecord.task());
            statement.setString(2, taskRecord.date());
            statement.setBoolean(3, taskRecord.is_completed());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.printf("Falha ao atualizar: %s%n", taskRecord);
            System.out.println(e.getMessage());
        }
    }

    public static int storeTask(TaskRecord taskRecord, Connection connection) {
        try (var statement = connection.prepareStatement(INSERT_TASK)) {
            statement.setString(1, taskRecord.task());
            statement.setString(2, taskRecord.date());
            statement.setBoolean(3, taskRecord.is_completed());
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.printf("Falha ao inserir: %s%n", taskRecord);
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
    public static void removeTask(int id, Connection connection) {
        try (var statement = connection.prepareStatement(DELETE_TASK)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.print("Falha ao deletar.");
            System.out.println(e.getMessage());
        }
    }
}
