package oop.solutions;

/**
 * ExerciseSet3Solution.java
 *
 * Solutions for Exercise Set 3: Inheritance.
 * Covers: base and subclass construction with super(), method overriding,
 *         super.method() delegation, abstract classes, polymorphic arrays,
 *         and concrete implementations of abstract methods.
 */
public class ExerciseSet3Solution {

    public static void main(String[] args) {

        // === Exercise 3.1: Vehicle Hierarchy ===
        System.out.println("=== Exercise 3.1: Vehicle Hierarchy ===");

        // Each subclass calls super() in its constructor to initialize the base fields.
        Car        car  = new Car("Toyota", "Camry", 2022, 4);
        Motorcycle moto = new Motorcycle("Harley-Davidson", "Sportster", 2021, true);
        Car        coupe = new Car("Honda", "Civic", 2023, 2);

        // Garage holds a mixed Vehicle array; printAll() calls the correct describe()
        // override for each element — this is runtime polymorphism.
        Garage garage = new Garage(new Vehicle[]{car, moto, coupe});
        garage.printAll();
        // Output:
        // 2022 Toyota Camry (4-door)
        // 2021 Harley-Davidson Sportster (sidecar: yes)
        // 2023 Honda Civic (2-door)

        // === Exercise 3.2: Employee Hierarchy ===
        System.out.println("\n=== Exercise 3.2: Employee Hierarchy ===");

        Manager    mgr  = new Manager("Alice", "MGR-01", 6000.00, 5, 10000.00);
        Manager    mgr2 = new Manager("Bob",   "MGR-02", 7000.00, 8, 15000.00);
        Contractor con1 = new Contractor("Carol", "CON-01", 0.0, 85.00, 2000);
        Contractor con2 = new Contractor("Dave",  "CON-02", 0.0, 60.00, 1800);

        Employee[] team = {mgr, mgr2, con1, con2};

        double totalCompensation = 0;
        for (Employee e : team) {
            double annual = e.calculateAnnualCompensation();
            totalCompensation += annual;
            System.out.printf("%s — Annual: $%.2f%n", e, annual);
        }
        // Output:
        // Manager{name='Alice', id='MGR-01', baseSalary=$6000.00, teamSize=5, bonus=$10000.00} — Annual: $82000.00
        // Manager{name='Bob', id='MGR-02', baseSalary=$7000.00, teamSize=8, bonus=$15000.00} — Annual: $99000.00
        // Contractor{name='Carol', id='CON-01', hourlyRate=$85.00, hours=2000} — Annual: $170000.00
        // Contractor{name='Dave', id='CON-02', hourlyRate=$60.00, hours=1800} — Annual: $108000.00

        System.out.printf("Total payroll: $%.2f%n", totalCompensation);
        // Output: Total payroll: $459000.00

        // === Exercise 3.3: Abstract Shape ===
        System.out.println("\n=== Exercise 3.3: Abstract Shape ===");

        // Abstract class Shape cannot be instantiated directly.
        // All three concrete subclasses must implement area() and perimeter().
        Shape circle    = new Circle("red", 5.0);
        Shape rectangle = new Rectangle("blue", 4.0, 6.0);
        Shape triangle  = new RightTriangle("green", 3.0, 4.0);

        // describe() is defined once in Shape and delegates to the overridden area()/perimeter().
        System.out.println(circle.describe());
        // Output: red Circle — area=78.54, perimeter=31.42
        System.out.println(rectangle.describe());
        // Output: blue Rectangle — area=24.00, perimeter=20.00
        System.out.println(triangle.describe());
        // Output: green RightTriangle — area=6.00, perimeter=12.00
    }

    // -------------------------------------------------------------------------
    // Exercise 3.1 — Vehicle Hierarchy
    // -------------------------------------------------------------------------

    /** Base class holding the fields common to all vehicles. */
    static class Vehicle {
        protected String make;
        protected String model;
        protected int    year;

        Vehicle(String make, String model, int year) {
            this.make  = make;
            this.model = model;
            this.year  = year;
        }

        /** Base description. Subclasses call super.describe() and append their details. */
        String describe() {
            return year + " " + make + " " + model;
        }
    }

    /** A passenger car with a known door count. */
    static class Car extends Vehicle {
        private int numberOfDoors;

        Car(String make, String model, int year, int numberOfDoors) {
            super(make, model, year); // Must be the first statement.
            this.numberOfDoors = numberOfDoors;
        }

        @Override
        String describe() {
            // Delegates to the parent, then appends car-specific detail.
            return super.describe() + " (" + numberOfDoors + "-door)";
        }
    }

    /** A motorcycle, which may or may not have a sidecar. */
    static class Motorcycle extends Vehicle {
        private boolean hasSidecar;

        Motorcycle(String make, String model, int year, boolean hasSidecar) {
            super(make, model, year);
            this.hasSidecar = hasSidecar;
        }

        @Override
        String describe() {
            return super.describe() + " (sidecar: " + (hasSidecar ? "yes" : "no") + ")";
        }
    }

    /**
     * Holds an array of vehicles and provides a printAll() method.
     * Because vehicles are stored by the base type, the correct
     * describe() override is selected at runtime for each element.
     */
    static class Garage {
        private Vehicle[] vehicles;

        Garage(Vehicle[] vehicles) {
            this.vehicles = vehicles;
        }

        void printAll() {
            for (Vehicle v : vehicles) {
                System.out.println(v.describe()); // Dynamic dispatch selects the right override.
            }
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 3.2 — Employee Hierarchy
    // -------------------------------------------------------------------------

    /** Abstract base class for all employee types. */
    static abstract class Employee {
        protected String name;
        protected String id;
        protected double baseSalary;

        Employee(String name, String id, double baseSalary) {
            this.name       = name;
            this.id         = id;
            this.baseSalary = baseSalary;
        }

        /** Each subclass defines its own compensation formula. */
        abstract double calculateAnnualCompensation();
    }

    /** A salaried manager with a fixed bonus on top of the monthly base salary. */
    static class Manager extends Employee {
        private int    teamSize;
        private double bonus;

        Manager(String name, String id, double baseSalary, int teamSize, double bonus) {
            super(name, id, baseSalary); // Constructor chaining via super().
            this.teamSize = teamSize;
            this.bonus    = bonus;
        }

        /** Annual compensation = 12 months of base salary + one-time bonus. */
        @Override
        double calculateAnnualCompensation() {
            return baseSalary * 12 + bonus;
        }

        @Override
        public String toString() {
            return String.format("Manager{name='%s', id='%s', baseSalary=$%.2f, teamSize=%d, bonus=$%.2f}",
                    name, id, baseSalary, teamSize, bonus);
        }
    }

    /** A contractor paid by the hour. No fixed salary is used in the formula. */
    static class Contractor extends Employee {
        private double hourlyRate;
        private int    hoursPerYear;

        Contractor(String name, String id, double baseSalary, double hourlyRate, int hoursPerYear) {
            super(name, id, baseSalary);
            this.hourlyRate   = hourlyRate;
            this.hoursPerYear = hoursPerYear;
        }

        /** Annual compensation = hourly rate multiplied by total hours worked. */
        @Override
        double calculateAnnualCompensation() {
            return hourlyRate * hoursPerYear;
        }

        @Override
        public String toString() {
            return String.format("Contractor{name='%s', id='%s', hourlyRate=$%.2f, hours=%d}",
                    name, id, hourlyRate, hoursPerYear);
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 3.3 — Abstract Shape
    // -------------------------------------------------------------------------

    /**
     * Abstract base defining the structure of a shape.
     * area() and perimeter() must be supplied by each concrete subclass.
     * describe() is a template method that composes a summary using them.
     */
    static abstract class Shape {
        private String color;

        Shape(String color) {
            this.color = color;
        }

        String getColor()            { return color; }
        void   setColor(String c)    { color = c;    }

        abstract double area();
        abstract double perimeter();

        /**
         * Template method: concrete logic that relies on the abstract hooks above.
         * Subclasses do not need to override this.
         */
        String describe() {
            return String.format("%s %s — area=%.2f, perimeter=%.2f",
                    color, getClass().getSimpleName(), area(), perimeter());
        }
    }

    /** A circle defined by its radius. */
    static class Circle extends Shape {
        private double radius;

        Circle(String color, double radius) {
            super(color);
            this.radius = radius;
        }

        @Override
        public double area()      { return Math.PI * radius * radius; }

        @Override
        public double perimeter() { return 2 * Math.PI * radius; }
    }

    /** A rectangle defined by width and height. */
    static class Rectangle extends Shape {
        private double width;
        private double height;

        Rectangle(String color, double width, double height) {
            super(color);
            this.width  = width;
            this.height = height;
        }

        @Override
        public double area()      { return width * height; }

        @Override
        public double perimeter() { return 2 * (width + height); }
    }

    /** A right triangle defined by its two legs. The hypotenuse is derived. */
    static class RightTriangle extends Shape {
        private double a; // First leg.
        private double b; // Second leg.

        RightTriangle(String color, double a, double b) {
            super(color);
            this.a = a;
            this.b = b;
        }

        @Override
        public double area()      { return 0.5 * a * b; }

        @Override
        public double perimeter() {
            double hypotenuse = Math.sqrt(a * a + b * b);
            return a + b + hypotenuse;
        }
    }
}
