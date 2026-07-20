package object;

import controller.DatabaseController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskFactory {

    public static List<BaseTask> TaskRecordsToTaskObjects (List<DatabaseController.TaskRecord> taskRecordList) {
        List<BaseTask> baseTaskList = new ArrayList<>();

        for (DatabaseController.TaskRecord task : taskRecordList) {
            baseTaskList.add(TaskRecordToTaskObject(task));
        }

        return baseTaskList;
    }

    public static DatabaseController.TaskRecord TaskObjectToTaskRecord(BaseTask task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = task.getDate().format(formatter);
        return new DatabaseController.TaskRecord(task.getInternalID(), task.getText(), formattedDate, task.isCompleted());
    }

    public static BaseTask TaskRecordToTaskObject(DatabaseController.TaskRecord taskRecord) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return new BaseTask(taskRecord.task(), taskRecord.is_completed(), LocalDateTime.parse(taskRecord.date(), formatter), taskRecord.id());
    }
}
