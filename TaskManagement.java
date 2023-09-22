package muster2;

import java.util.ArrayList;
import java.util.List;

class Task {
    private String title;
    private String description;
    private String dueDate;
    private boolean isComplete;
    private String assignedTo;

    public Task(String title, String description, String dueDate, String assignedTo) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isComplete = false; // Initially, the task is incomplete
        this.assignedTo = assignedTo;
    }

    // Getters and setters for Task properties
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nDescription: " + description + "\nDue Date: " + dueDate
                + "\nAssigned To: " + assignedTo + "\nStatus: " + (isComplete ? "Complete" : "Incomplete");
    }
}

class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void updateTask(int index, Task updatedTask) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, updatedTask);
        }
    }

    public void markTaskComplete(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setComplete(true);
        }
    }

    public void markTaskIncomplete(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setComplete(false);
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}

public class TaskManagementSystem {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Task 1", "Description for Task 1", "2023-09-30", "User 1");
        Task task2 = new Task("Task 2", "Description for Task 2", "2023-10-15", "User 2");

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Print all tasks
        List<Task> allTasks = taskManager.getAllTasks();
        for (int i = 0; i < allTasks.size(); i++) {
            System.out.println("Task " + (i + 1) + ":\n" + allTasks.get(i));
            System.out.println();
        }
    }
}