public class PartTimeEmployee extends Employee {
    private double hourlyRate; // Переименуем hourlyWage в hourlyRate
    private int hoursWorked;

    public PartTimeEmployee(String name, double hourlyRate, int hoursWorked) {
        super(name, "Part-time");
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked; // Теперь всё согласовано
    }
}
