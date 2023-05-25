// 100/61 @ 0ms
int* spiralOrder (int** matrix, int matrixSize, int* matrixColSize, int* returnSize) {
    int *res = malloc(sizeof(int) * matrixSize * *matrixColSize);
    int resindex = 0;

    int rows = matrixSize;
    int cols = *matrixColSize;
    int row = 0;
    int col = -1;

    int l = 0;
    int r = cols - 1;
    int u = 0;
    int d = rows - 1;

    int iters = rows < cols ? rows : cols;
    iters += iters & 1; // if odd

    while (iters) {
        col++;
        if (!(l <= col && col <= r && u <= row && row <= d)) {
            break;
        } // if
        while (col <= r) {
            //printf("right, r: %d, c: %d\n", row, col); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            col++;
        } // while
        col--;
        u++;

        row++;
        if (!(l <= col && col <= r && u <= row && row <= d)) {
            break;
        } // if
        while (row <= d) {
            //printf("down, r: %d, c: %d\n", row, col); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            row++;
        } // while
        row--;
        r--;

        col--;
        if (!(l <= col && col <= r && u <= row && row <= d)) {
            break;
        } // if
        while (col >= l) {
            //printf("left, r: %d, c: %d\n", row, col); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            col--;
        } // while
        col++;
        d--;

        row--;
        if (!(l <= col && col <= r && u <= row && row <= d)) {
            break;
        } // if
        while (row >= u) {
            //printf("up, r: %d, c: %d\n", row, col); //debug
            res[resindex] = matrix[row][col];
            resindex++;
            row--;
        } // while
        row++;
        l++;

        iters--;
    } // while

    *returnSize = resindex;
    return res;
} // spiralOrder()
