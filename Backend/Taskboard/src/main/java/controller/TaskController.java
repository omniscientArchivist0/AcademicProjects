package controller;

import object.BaseTask;
import object.Filter;
import object.TaskFactory;
import ui.BaseUI;
import ui.TerminalUI;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskController {

    private Filter filter = Filter.ALL;
    private List<BaseTask> currentList = new ArrayList<>();
    private final BaseUI ui = new TerminalUI();

    // Logica - Operações

    private void createTask() {
        var connection = DatabaseController.openConnection();

        ui.createTask();
        String text = ui.taskTextInput();
        boolean isCompleted = ui.taskBooleanInput();
        BaseTask baseTask = new BaseTask(text, isCompleted, LocalDateTime.now(), 0);

        DatabaseController.storeTask(TaskFactory.TaskObjectToTaskRecord(baseTask), connection);

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.print("Connection não foi fechada.");
        }
    }

    private void deleteTask() {
        var connection = DatabaseController.openConnection();

        ui.deleteTask();
        BaseTask baseTask = ui.selectTask(currentList);
        DatabaseController.removeTask(baseTask.getInternalID(), connection);

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.print("Connection não foi fechada.");
        }
    }

    private void updateTaskStatus() {
        var connection = DatabaseController.openConnection();

        ui.updateTask();
        BaseTask baseTask = ui.selectTask(currentList);
        baseTask.changeTaskStatus();
        DatabaseController.updateTask(connection, TaskFactory.TaskObjectToTaskRecord(baseTask), baseTask.getInternalID());

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.print("Connection não foi fechada.");
        }
    }

    private void showTasks() {
        var connection = DatabaseController.openConnection();

        List<BaseTask> baseTaskList = TaskFactory.TaskRecordsToTaskObjects(DatabaseController.getTasks(connection));
        List<BaseTask> baseTaskListFinal = new ArrayList<>();

        for (BaseTask baseTask : baseTaskList) {
            switch(filter) {
                case Filter.FALSE: {
                    if (baseTask.isCompleted() == false) {
                        baseTaskListFinal.add(baseTask);
                    }
                }
                break;
                case Filter.TRUE: {
                    if (baseTask.isCompleted() == true) {
                        baseTaskListFinal.add(baseTask);
                    }
                }
                break;
                default: baseTaskListFinal.add(baseTask);
            }
        }

        currentList = baseTaskListFinal;
        ui.showList(baseTaskListFinal);
    }

    private void changeFilter() {
        filter = ui.setFilter();
    }

    // Logica - Fluxo de Execução

    public void taskApplication() {

        var connection = DatabaseController.openConnection();
        DatabaseController.setupDB(connection);

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.print("Connection não foi fechada.");
        }

        boolean loop = true;
        while (loop) {
            int choice = ui.boot();
            switch(choice) {
                case 1: createTask();
                break;
                case 2: showTasks();
                break;
                case 3: changeFilter();
                break;
                case 4: deleteTask();
                break;
                case 5: updateTaskStatus();
                break;
                case 6: loop = false;
                break;
                default: System.out.println("\n// Por favor, insira um numero valido. //\n");
            }

        }
    }

}
