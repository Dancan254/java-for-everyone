import java.util.Arrays;

/**
 * Exercise4Solution.java
 *
 * Solutions for Arrays Exercise Set 4: Two-Dimensional Arrays.
 * Covers: matrix transpose, row/column sums, diagonal sums, saddle points, Conway's Game of Life.
 */
public class Exercise4Solution {

    // --- Matrix Operations ---

    /** Returns the transpose of a matrix (rows become columns). */
    static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // The transposed matrix has dimensions cols x rows.
        int[][] result = new int[cols][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                result[c][r] = matrix[r][c];
            }
        }
        return result;
    }

    /** Returns an array where result[i] is the sum of row i. */
    static int[] rowSums(int[][] matrix) {
        int[] sums = new int[matrix.length];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                sums[r] += matrix[r][c];
            }
        }
        return sums;
    }

    /** Returns an array where result[j] is the sum of column j. */
    static int[] colSums(int[][] matrix) {
        int cols = matrix[0].length;
        int[] sums = new int[cols];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < cols; c++) {
                sums[c] += matrix[r][c];
            }
        }
        return sums;
    }

    /** Prints the matrix in a readable grid format. */
    static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%5d", val);
            }
            System.out.println();
        }
    }

    // --- Game of Life ---

    /** Returns the next generation grid according to Conway's Game of Life rules. */
    static boolean[][] nextGeneration(boolean[][] grid) {
        int rows   = grid.length;
        int cols   = grid[0].length;
        boolean[][] next = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int liveNeighbours = countLiveNeighbours(grid, r, c);

                if (grid[r][c]) {
                    // A living cell survives with 2 or 3 live neighbours.
                    next[r][c] = (liveNeighbours == 2 || liveNeighbours == 3);
                } else {
                    // A dead cell comes alive with exactly 3 live neighbours.
                    next[r][c] = (liveNeighbours == 3);
                }
            }
        }

        return next;
    }

    /** Counts the live neighbours of cell (r, c), treating out-of-bounds as dead. */
    static int countLiveNeighbours(boolean[][] grid, int r, int c) {
        int count = 0;
        int rows  = grid.length;
        int cols  = grid[0].length;

        // Check all 8 surrounding cells.
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Skip the cell itself.
                int nr = r + dr;
                int nc = c + dc;
                // Treat cells outside the grid as dead — do not wrap around.
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc]) {
                    count++;
                }
            }
        }

        return count;
    }

    /** Prints a Game of Life grid using # for living cells and . for dead cells. */
    static void printGrid(boolean[][] grid) {
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                System.out.print(cell ? "# " : ". ");
            }
            System.out.println();
        }
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 4.1: Matrix Operations ---
        System.out.println("=== Exercise 4.1: Matrix Operations ===");

        int[][] matrix = {
                {1,  2,  3,  4},
                {5,  6,  7,  8},
                {9, 10, 11, 12}
        };

        System.out.println("Original (3x4):");
        printMatrix(matrix);

        System.out.println("\nTranspose (4x3):");
        printMatrix(transpose(matrix));
        // Output (first column of original becomes first row of transpose):
        //     1    5    9
        //     2    6   10
        //     3    7   11
        //     4    8   12

        System.out.println("\nRow sums: " + Arrays.toString(rowSums(matrix)));
        // Output: Row sums: [10, 26, 42]

        System.out.println("Column sums: " + Arrays.toString(colSums(matrix)));
        // Output: Column sums: [15, 18, 21, 24]

        // --- Exercise 4.2: Diagonal Sums and Saddle Points ---
        System.out.println("\n=== Exercise 4.2: Diagonal Sums and Saddle Points ===");

        int[][] square = {
                { 3,  7,  2,  5},
                { 1,  4,  8,  6},
                { 9,  2,  3,  4},
                { 1,  5,  7, 11}
        };

        System.out.println("Square matrix:");
        printMatrix(square);

        // Main diagonal: (0,0), (1,1), (2,2), (3,3).
        int mainDiagSum = 0;
        for (int i = 0; i < square.length; i++) {
            mainDiagSum += square[i][i];
        }
        System.out.println("Main diagonal sum: " + mainDiagSum); // Output: 3+4+3+11 = 21

        // Anti-diagonal: (0,n-1), (1,n-2), ...
        int antiDiagSum = 0;
        int n = square.length;
        for (int i = 0; i < n; i++) {
            antiDiagSum += square[i][n - 1 - i];
        }
        System.out.println("Anti-diagonal sum: " + antiDiagSum); // Output: 5+8+2+1 = 16

        // Saddle points: minimum in its row AND maximum in its column.
        System.out.println("Saddle points:");
        boolean foundSaddle = false;
        for (int r = 0; r < square.length; r++) {
            // Find the minimum value in this row.
            int rowMin = square[r][0];
            for (int c = 1; c < square[r].length; c++) {
                if (square[r][c] < rowMin) rowMin = square[r][c];
            }

            // Check each cell with the row minimum value.
            for (int c = 0; c < square[r].length; c++) {
                if (square[r][c] == rowMin) {
                    // Is this also the maximum in its column?
                    int colMax = square[0][c];
                    for (int row2 = 1; row2 < square.length; row2++) {
                        if (square[row2][c] > colMax) colMax = square[row2][c];
                    }
                    if (rowMin == colMax) {
                        System.out.println("  Value " + rowMin + " at (" + r + ", " + c + ")");
                        foundSaddle = true;
                    }
                }
            }
        }
        if (!foundSaddle) {
            System.out.println("  No saddle points.");
        }

        // --- Exercise 4.3: Conway's Game of Life ---
        System.out.println("\n=== Exercise 4.3: Conway's Game of Life ===");

        // Blinker pattern: a horizontal line of 3 cells centred in a 5x5 grid.
        boolean[][] grid = new boolean[5][5];
        grid[2][1] = true;
        grid[2][2] = true;
        grid[2][3] = true;

        System.out.println("Generation 0 (horizontal blinker):");
        printGrid(grid);

        grid = nextGeneration(grid);
        System.out.println("Generation 1 (should be vertical blinker):");
        printGrid(grid);
        // Output:
        // . . . . .
        // . . # . .
        // . . # . .
        // . . # . .
        // . . . . .

        grid = nextGeneration(grid);
        System.out.println("Generation 2 (should be back to horizontal):");
        printGrid(grid);
        // Output:
        // . . . . .
        // . . . . .
        // . # # # .
        // . . . . .
        // . . . . .
    }
}
