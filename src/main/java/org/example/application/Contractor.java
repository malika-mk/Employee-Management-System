public class Contractor extends Employee {
    private double hourlyRate;
    private int maxHours; // Максимальные часы
    private int hoursWorked; // Отработанные часы

    public Contractor(String name, double hourlyRate, int maxHours) {
        super(name, "Contractor");
        this.hourlyRate = hourlyRate;
        this.maxHours = maxHours;
        this.hoursWorked = 0; // По умолчанию 0, пока не установлено
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return Math.min(7, 6) * hourlyRate; // Учитываем максимум часов
    }
}