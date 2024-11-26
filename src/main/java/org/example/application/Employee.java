public abstract class Employee {
    private String name;
    private String employeeType;

    public Employee(String name, String employeeType) {
        this.name = name;
        this.employeeType = employeeType;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public abstract double calculateSalary();
}
