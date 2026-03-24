package arrays;
import java.util.Arrays;

public class ArraysClassDemo {
    public static void main(String[] args) {

        int[][] matrix = new int[3][3];
        int[] nums  = {5,4, 3, 2, 1};
        Arrays.sort(nums, 3, 4);
        System.out.println(Arrays.toString(nums));
        int[][] grid = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };

        //get the row
        int lengthRows = grid.length;

        //get the cols
        int lengthCols = grid[0].length;
        int row2Length = grid[1].length;
        int row3Length = grid[2].length;
    }
}
