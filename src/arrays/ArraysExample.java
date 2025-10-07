package arrays;

import java.util.Arrays;

public class ArraysExample {
    public static void main(String[] args) {
        // datatype[] variableName;
        //static way
        int[] myArray = {10, 20, 30, 40};
        System.out.println(Arrays.toString(myArray));
        //dynamic way
        int[] dynamicArray = new int[5];
        System.out.println(Arrays.toString(dynamicArray));
        // [0, 0, 0, 0, 0]
        //assign values
        //variableName[index] = value;
        dynamicArray[0] = 90;
        dynamicArray[1] = 100;
        dynamicArray[2] = 200;
        dynamicArray[3] = 300;
        dynamicArray[4] = 400;
        //print the third element
        System.out.println("Third element: " + dynamicArray[2]);
        //System.out.println(Arrays.toString(dynamicArray));
        printArray(new int[]{1,2,3,5});

        //indexing starts from zero
        //last index - arrayLength - 1
        int lengthDynamic = dynamicArray.length;
        System.out.println("Length of dynamic array: " + lengthDynamic);
        System.out.println(dynamicArray[lengthDynamic - 1]);
        double[] doubleArray = new double[5];
        System.out.println(doubleArray);

        int[] arr = new int[5];
        System.out.println(arr);

        //Iterating through arrays
        String[] names = {"Stewie", "Jojo", "Adongo", "Alex"};
        int length = names.length; // 4
        //traditional fori
        for (int i = 0; i < length; i++) {
            System.out.println("Name " + i + " " + names[i]);
        }
        //for each
        for (String name : names){
            System.out.println("Name: " + name);
        }

        int[] nums = {1,2,4};
        System.out.println("Before");
        System.out.println(Arrays.toString(nums));
        System.out.println("=".repeat(20));
        for (int num : nums){
            num = num * 2;
            System.out.println("doubled: " + num);
        }
        System.out.println("=".repeat(20));
        System.out.println("After");
        System.out.println(Arrays.toString(nums));

        System.out.println("Using the Traditional");
        System.out.println("Before");
        System.out.println(Arrays.toString(nums));
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nums[i] * 2;
        }
        System.out.println("=".repeat(20));
        System.out.println("After");
        System.out.println(Arrays.toString(nums));

        System.out.println("finding min element");
        int[] fin = {4, 7, 9, 3, 90, 2};
        int min = findMin(fin);

        System.out.println("Min element is: " + min);

        System.out.println("Finding element");
        int target = 9;
        int index = findElement(fin, target);
        System.out.println("Found " + target + " at index " + index);

        int[] original = {1, 2, 4, 5};
        int[] copy = new int[2];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = original[i];
        }

        System.out.println("Orignal: " + Arrays.toString(original));
        System.out.println("Copy: " + Arrays.toString(copy));
    }
    static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    static int findMin(int[] arr){
        //have the min element -  as first
        int minElement = arr[0];

        for (int i = 1; i < arr.length; i++) {
            //compare the minElement  === arr[i]
            //if arr[i] < miElem => new minElem == arr[i]
            if (arr[i] < minElement){
                minElement = arr[i];
            }
        }
        return minElement;
    }
    static int findMax(int[] arr){
        //have the min element -  as first
        int maxElement = arr[0];

        for (int i = 1; i < arr.length; i++) {
            //compare the minElement  === arr[i]
            //if arr[i] < miElem => new minElem == arr[i]
            if (arr[i] > maxElement){
                maxElement = arr[i];
            }
        }
        return maxElement;
    }

    static int sum(int[] nums){
        int sum = 0;
        for (int num : nums){
            sum += num;
        }
        return sum;
    }

    static int suml(int[] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    static int findElement(int[] arr, int target){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target){
                return i;
            }
        }

        return -1;
    }
}
