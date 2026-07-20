package object;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

public class BaseTask {
    private String text;
    private Boolean isCompleted;
    private LocalDateTime taskChangeDate;
    private final int internalID;
    // private final UUID taskID;

    public BaseTask() {
        text = "";
        isCompleted = false;
        taskChangeDate = LocalDateTime.now();
        internalID = 0;
    }
    public BaseTask(String text) {
        this.text = text;
        isCompleted = false;
        taskChangeDate = LocalDateTime.now();
        internalID = 0;
    }
    public BaseTask(String text, Boolean isCompleted, LocalDateTime date, int internalID) {
        this.text = text;
        this.isCompleted = isCompleted;
        this.taskChangeDate = date;
        this.internalID = internalID;
    }

    public void setText(String text) {
        this.text = text;
        updateTaskDate();
    }

    public void changeTaskStatus() {
        isCompleted = !isCompleted;
        updateTaskDate();
    }

    public LocalDateTime getDate() {
        return taskChangeDate;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public String getText() {
        return text;
    }

    public int getInternalID() {
        return internalID;
    }

    private void updateTaskDate() {
        taskChangeDate = LocalDateTime.now();
    }
}
