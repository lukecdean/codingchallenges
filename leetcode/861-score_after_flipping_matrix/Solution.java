// Luke Dean 2023-8-25
class Solution {
    // 100/100 @ 0ms
    public int matrixScore(int[][] grid) {
        //printGrid(grid); // debug
        // make most significant bits 1
        for (int row = 0; row < grid.length; row++) {
            if (grid[row][0] == 0) {
                flip(grid, row, 0, 0, 1);
            } // if
        } // for row

        //printGrid(grid); // debug
        // flip cols which have more 0s than 1s
        for (int col = 0; col < grid[0].length; col++) {
            if (moreZeros(grid, col)) {
                flip(grid, 0, col, 1, 0);
            } // if
        } // for col

        //printGrid(grid); // debug
        // calculate score
        int score = 0;
        int rowVal = 1;
        for (int col = grid[0].length - 1; col >= 0; col--) {
            for (int row = 0; row < grid.length; row++) {
                score += rowVal * grid[row][col];
            } // for row
            rowVal *= 2;
        } // for col

        return score;
    } // matrixScore()

    private void flip(int[][] grid, int row, int col, int deltaRow, int deltaCol) {
        while (row < grid.length && col < grid[0].length) {
            grid[row][col] = grid[row][col] == 0 ? 1 : 0;
            row += deltaRow;
            col += deltaCol;
        } // while
    } // flip()

    private boolean moreZeros(int[][] grid, int col) {
        int ones = 0;
        for (int row = 0; row < grid.length; row++) {
            ones += grid[row][col];
        } // for

        return ones < (grid.length - ones);
    } // moreZeros()

    void printGrid(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                System.out.print(grid[row][col]);
            } //for col
            System.out.println();
        } // for row
        System.out.println();
    } // printGrid()
}
