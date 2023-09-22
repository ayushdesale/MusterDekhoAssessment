package muster3;

import java.util.ArrayList;
import java.util.List;

class Task {
    private String title;
    private String description;
    private String assignedUser;
    private boolean completed;
    private String dueDate;

    public Task(String title, String description, String assignedUser, boolean completed, String dueDate) {
        this.title = title;
        this.description = description;
        this.assignedUser = assignedUser;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    // Getters and setters for the task properties

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

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public List<Task> searchTasks(String keyword) {
        List<Task> searchResults = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getTitle().contains(keyword) || task.getDescription().contains(keyword) || task.getAssignedUser().contains(keyword)) {
                searchResults.add(task);
            }
        }

        return searchResults;
    }

    public List<Task> filterTasks(boolean completedOnly, String dueDate) {
        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {
            if ((completedOnly && task.isCompleted()) || (!completedOnly && !task.isCompleted())) {
                if (dueDate == null || task.getDueDate().equals(dueDate)) {
                    filteredTasks.add(task);
                }
            }
        }

        return filteredTasks;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a task manager
        TaskManager taskManager = new TaskManager();

        // Create some tasks
        Task task1 = new Task("Task 1", "Description 1", "User 1", false, "2023-09-30");
        Task task2 = new Task("Task 2", "Description 2", "User 2", true, "2023-10-15");
        Task task3 = new Task("Task 3", "Description 3", "User 1", false, "2023-10-31");

        // Add tasks to the task manager
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Search for tasks
        List<Task> searchResults = taskManager.searchTasks("User");
        System.out.println("Search Results:");
        for (Task task : searchResults) {
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Assigned User: " + task.getAssignedUser());
            System.out.println("--------------------");
        }

        System.out.println();

        // Filter tasks
        List<Task> filteredTasks = taskManager.filterTasks(true, "2023-10-15");
        System.out.println("Filtered Tasks:");
        for (Task task : filteredTasks) {
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Assigned User: " + task.getAssignedUser());
            System.out.println("Due Date: " + task.getDueDate());
            System.out.println("--------------------");
        }
    }
}