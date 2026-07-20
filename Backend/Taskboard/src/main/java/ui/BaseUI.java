package ui;

import object.Filter;
import object.BaseTask;

import java.util.List;

public interface BaseUI {
    public void createTask();
    public void deleteTask();
    public BaseTask selectTask(List<BaseTask> baseTaskList);
    public void updateTask();
    public void showList(List<BaseTask> baseTaskList);
    public Filter setFilter();
    public String taskTextInput();
    public boolean taskBooleanInput();
    public int boot();
}
