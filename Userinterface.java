package muster4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class TaskManagerGUI extends Application {
    private TaskManager taskManager;

    private TextField titleField;
    private TextArea descriptionArea;
    private DatePicker datePicker;
    private TextField assigneeField;
    private CheckBox completedCheckBox;
    private TextField searchField;
    private ComboBox<String> dueDateFilterComboBox;
    private CheckBox completedFilterCheckBox;
    private Button createTaskButton;
    private Button updateTaskButton;
    private Button deleteTaskButton;
    private Button searchButton;
    private Button filterButton;
    private ListView<Task> taskListView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        taskManager = new TaskManager();

        // Create UI controls
        Label titleLabel = new Label("Title:");
        titleField = new TextField();

        Label descriptionLabel = new Label("Description:");
        descriptionArea = new TextArea();

        Label dueDateLabel = new Label("Due Date:");
        datePicker = new DatePicker();

        Label assigneeLabel = new Label("Assignee:");
        assigneeField = new TextField();

        Label completedLabel = new Label("Completed:");
        completedCheckBox = new CheckBox();

        createTaskButton = new Button("Create Task");
        updateTaskButton = new Button("Update Task");
        deleteTaskButton = new Button("Delete Task");

        Label searchLabel = new Label("Search:");
        searchField = new TextField();
        searchButton = new Button("Search");

        Label dueDateFilterLabel = new Label("Due Date Filter:");
        dueDateFilterComboBox = new ComboBox<>();
        dueDateFilterComboBox.getItems().addAll("Any", "Today", "This Week", "This Month");

        completedFilterCheckBox = new CheckBox("Completed Only");

        filterButton = new Button("Filter");

        taskListView = new ListView<>();

        // Set event handlers
        createTaskButton.setOnAction(event -> createTask());
        updateTaskButton.setOnAction(event -> updateTask());
        deleteTaskButton.setOnAction(event -> deleteTask());
        searchButton.setOnAction(event -> searchTasks());
        filterButton.setOnAction(event -> filterTasks());

        // Set up the layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleField, 1, 0);
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionArea, 1, 1);
        gridPane.add(dueDateLabel, 0, 2);
        gridPane.add(datePicker, 1, 2);
        gridPane.add(assigneeLabel, 0, 3);
        gridPane.add(assigneeField, 1, 3);
        gridPane.add(completedLabel, 0, 4);
        gridPane.add(completedCheckBox, 1, 4);
        gridPane.add(createTaskButton, 0, 5);
        gridPane.add(updateTaskButton, 1, 5);
        gridPane.add(deleteTaskButton, 2, 5);

        gridPane.add(searchLabel, 0, 6);
        gridPane.add(searchField, 1, 6);
        gridPane.add(searchButton, 2, 6);

        gridPane.add(dueDateFilterLabel, 0, 7);
        gridPane.add(dueDateFilterComboBox, 1, 7);
        gridPane.add(completedFilterCheckBox, 2, 7);
        gridPane.add(filterButton, 3, 7);

        gridPane.add(taskListView, 0, 8, 4, 1);

        // Set up the scene
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Manager");
        primaryStage.show();
    }

    private void createTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        LocalDate dueDate = datePicker.getValue();
        String assignee = assigneeField.getText();

        if (title.isEmpty() || assignee.isEmpty() || dueDate == null) {
            showErrorDialog("Please enter all task details.");
            return;
        }

        taskManager.createTask(title, description, dueDate.toString(), assignee);
        clearFields();
        updateTaskListView();
    }

    private void updateTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            showErrorDialog("Please select a task to update.");
            return;
        }

        String title = titleField.getText();
        String description = descriptionArea.getText();
        LocalDate dueDate = datePicker.getValue();
        String assignee = assigneeField.getText();

        if (title.isEmpty() || assignee.isEmpty() || dueDate == null) {
            showErrorDialog("Please enter all task details.");
            return;
        }

        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        taskManager.updateTask(selectedIndex, title, description, dueDate.toString(), assignee);
        clearFields();
        updateTaskListView();
    }

    private void deleteTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            showErrorDialog("Please select a task to delete.");
            return;
        }

        taskManager.deleteTask(selectedIndex);
        clearFields();
        updateTaskListView();
    }

    private void searchTasks() {
        String keyword = searchField.getText();

        if (keyword.isEmpty()) {
            showErrorDialog("Please enter a search keyword.");
            return;
        }

        List<Task> searchResults = taskManager.searchTasks(keyword);
        taskListView.setItems(FXCollections.observableArrayList(searchResults));
    }

    private void filterTasks() {
        boolean completedOnly = completedFilterCheckBox.isSelected();
        String dueDateFilter = dueDateFilterComboBox.getValue();

        List<Task> filteredTasks;

        if (dueDateFilter == null || dueDateFilter.equals("Any")) {
            filteredTasks = taskManager.filterTasks(completedOnly, null);
        } else if (dueDateFilter.equals("Today")) {
            LocalDate today = LocalDate.now();
            filteredTasks = taskManager.filterTasks(completedOnly, today.toString());
        } else if (dueDateFilter.equals("This Week")) {
            LocalDate endDate = LocalDate.now().plusWeeks(1);
            filteredTasks = taskManager.filterTasks(completedOnly, null, endDate.toString());
        } else if (dueDateFilter.equals("This Month")) {
            LocalDate endDate = LocalDate.now().plusMonths(1);
            filteredTasks = taskManager.filterTasks(completedOnly, null, endDate.toString());
        } else {
            filteredTasks = taskManager.filterTasks(completedOnly, null);
        }

        taskListView.setItems(FXCollections.observableArrayList(filteredTasks));
    }

    private void updateTaskListView() {
        List<Task> tasks = taskManager.getTasks();
        taskListView.setItems(FXCollections.observableArrayList(tasks));
    }

    private void clearFields() {
        titleField.clear();
        descriptionArea.clear();
        datePicker.setValue(null);
        assigneeField.clear();
        completedCheckBox.setSelected(false);
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}