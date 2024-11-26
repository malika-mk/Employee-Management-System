import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeManagerApp extends Application {

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> typeColumn;

    @FXML
    private TableColumn<Employee, String> salaryColumn;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField hourlyRateField;

    @FXML
    private TextField hoursField;

    private ObservableList<Employee> employeeList;

    @FXML
    private void calculateSalaries() {
        System.out.println("Пересчёт зарплат начат...");
        for (Employee employee : employeeList) {
            double salary = employee.calculateSalary();
            System.out.println("Сотрудник: " + employee.getName() + ", Зарплата: " + salary);
        }
        employeeTable.refresh();
        System.out.println("Пересчёт зарплат завершён.");
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/application/hello-view.fxml"));
            VBox root = fxmlLoader.load();

            // Создаём сцену и устанавливаем её на stage
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Employee Manager");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Выводим ошибки в консоль
        }
        // Initialize Employee List
        employeeList = FXCollections.observableArrayList();

        // Initialize TableView
        employeeTable = new TableView<>();
        employeeTable.setItems(employeeList);

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName() != null ? data.getValue().getName() : ""));

        typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmployeeType()));

        salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().calculateSalary())));

        employeeTable.getColumns().addAll(nameColumn, typeColumn, salaryColumn);

        // Input Fields
        nameField = new TextField();
        typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList("Full-time", "Part-time", "Contractor"));

        hourlyRateField = new TextField();
        hoursField = new TextField();

        // Buttons
        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> addEmployee());

        Button calculateButton = new Button("Calculate Salaries");
        calculateButton.setOnAction(e -> calculateSalaries());

        // Layout
        GridPane inputForm = new GridPane();
        inputForm.setHgap(10);
        inputForm.setVgap(10);
        inputForm.addRow(0, new Label("Name:"), nameField);
        inputForm.addRow(1, new Label("Type:"), typeComboBox);
        inputForm.addRow(2, new Label("Hourly Rate:"), hourlyRateField);
        inputForm.addRow(3, new Label("Hours:"), hoursField);

        VBox root = new VBox(10, employeeTable, inputForm, addButton, calculateButton);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Employee Manager");
        primaryStage.show();
    }

    private void addEmployee() {
        String name = nameField.getText();
        String type = typeComboBox.getValue();
        if (name.isEmpty() || type == null) {
            showAlert("Error", "Name and Type must be filled.");
            return;
        }

        try {
            switch (type) {
                case "Full-time":
                    employeeList.add(new FullTimeEmployee(name, Double.parseDouble(hourlyRateField.getText())));
                    break;
                case "Part-time":
                    employeeList.add(new PartTimeEmployee(name, Double.parseDouble(hourlyRateField.getText()), Integer.parseInt(hoursField.getText())));
                    break;
                case "Contractor":
                    employeeList.add(new Contractor(name, Double.parseDouble(hourlyRateField.getText()), Integer.parseInt(hoursField.getText())));
                    break;
                default:
                    showAlert("Error", "Invalid employee type.");
            }
            handleClearFields();
        } catch (NumberFormatException ex) {
            showAlert("Error", "Invalid number format for hourly rate or hours worked.");
        }
    }


    @FXML
    private void handleClearFields() {
        nameField.clear();
        typeComboBox.getSelectionModel().clearSelection();
        hourlyRateField.clear();
        hoursField.clear();
    }

    @FXML
    private void handleAddEmployee() {

        System.out.println("Кнопка Add Employee нажата!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

