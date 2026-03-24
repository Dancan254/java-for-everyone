package oop;

public class Employee {
    // Step 1: Private fields
    private String name;
    private double salary;
    private String department;

    public Employee(){
        this("Unknown", 0, "Unknown");
    }
    // Step 2: Constructor
    public Employee(String name, double salary, String department) {
        this.name = name;
        this.salary = salary <= 0 ? 0 : salary;
//        setSalary(salary);       // Use setter for validation even in constructor
        this.department = department;
    }

    // Step 3: Getters
    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    // Step 4: Setters with validation
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            System.out.println("Salary cannot be negative.");
        }
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
