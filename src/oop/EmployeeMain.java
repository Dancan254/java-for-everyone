package oop;

import java.util.Arrays;

public class EmployeeMain {
    public static void main(String[] args) {
        Employee employee = new Employee("Chalo", 7000, "PR");
        employee.setSalary(10000);
        employee.setName("Ian");
        System.out.println(employee);
        int[] arr = new int[5];
        arr[0] = 10;
        arr[0] = 90;

        System.out.println(Arrays.toString(arr));
    }
}
