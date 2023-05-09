int* spiralOrder (int** matrix, int matrixSize, int* matrixColSize, int* returnSize) {
    int *res = malloc(matrixSize * *matrixColSize);
    int resindex = 0;

    int rows = matrixSize;
    int cols = matrixColSize;
    int row = 0;
    int col = 0;

    int padding = 0;

    int iters = rows < cols ? rows : cols;
    iters += iters & 1; // if odd

    while (padding < iters) {
        while (col < cols - padding) {
            printf("right"); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            col++;
        } // while
        col--;
        while (row < rows - padding) {
            printf("down"); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            row++;
        } // while
        row--;
        while (col >= padding) {
            printf("left"); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            col--;
        } // while
        col++;
        while (row >= padding + 1) {
            printf("up"); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            row--;
        } // while
        row++;
        padding++;
    } // while

    return res;
} // spiralOrder()
