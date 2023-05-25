/**
 * Return an array of arrays of size *returnSize.
 * The sizes of the arrays are returned as *returnColumnSizes array.
 * Note: Both returned array and *columnSizes array must be malloced, assume caller calls free().
 */
// 38/5 @ 4ms
int** generateMatrix(int n, int* returnSize, int** returnColumnSizes){
    int **res = malloc(sizeof(int *) * n);
    for (int i = 0; i < n; i++) {
        res[i] = (int *) malloc(sizeof(int) * n);
    } // for i

    int num = 1;

    int r = 0;
    int c = -1; // starting point for the algorithm

    int right = n - 1, down = n - 1, left = 0, up = 0;

    while (num <= n * n) {
        while (c < right) {
            c++;
            fprintf(stderr, "right, num: %d; %d %d\n", num, r, c); // debug
            //res[r * n + c] = num;
            res[r][c] = num;
            // res[r][c] = num;
            num++;
        } // while
        up++;

        if (num >= n * n) {
            fprintf(stderr, "breaking\n"); // debug
            // if n*n is odd, will be one center block
            break;
        } // if

        while (r < down) {
            r++;
            fprintf(stderr, "down, num: %d; %d %d\n", num, r, c); // debug
            //res[r * n + c] = num;
            res[r][c] = num;
            num++;
        } // while
        right--;

        while (c > left) {
            c--;
            fprintf(stderr, "left, num: %d; %d %d\n", num, r, c); // debug
            //res[r * n + c] = num;
            res[r][c] = num;
            num++;
        } // while
        down--;

        while (r > up) {
            r--;
            fprintf(stderr, "up, num: %d; %d %d\n", num, r, c); // debug
            //res[r * n + c] = num;
            res[r][c] = num;
            num++;
        } // while
        left++;
    } // while

    fprintf(stderr, "out of loop\n"); // debug


    *returnSize = n;

    *returnColumnSizes = malloc(n * sizeof(int));

    for (int i = 0; i < n; i++) {
        fprintf(stderr, "writing to out array of sizes\n"); // debug
        (*returnColumnSizes)[i] = n;
    } // for i

    return res;
} // generateMatrix()



int** generateMatrix0(int n, int* returnSize, int** returnColumnSizes){
    int *res = malloc(sizeof(int) * n * n);

    int num = 1;

    int r = 0;
    int c = -1; // starting point for the algorithm

    int right = n - 1, down = n - 1, left = 0, up = 0;

    while (num <= n * n) {
        while (c < right) {
            c++;
            fprintf(stderr, "right, num: %d; %d %d\n", num, r, c); // debug
            res[r * n + c] = num;
            // res[r][c] = num;
            num++;
        } // while
        up++;

        if (num >= n * n) {
            fprintf(stderr, "breaking\n"); // debug
            // if n*n is odd, will be one center block
            break;
        } // if

        while (r < down) {
            r++;
            fprintf(stderr, "down, num: %d; %d %d\n", num, r, c); // debug
            res[r * n + c] = num;
            num++;
        } // while
        right--;

        while (c > left) {
            c--;
            fprintf(stderr, "left, num: %d; %d %d\n", num, r, c); // debug
            res[r * n + c] = num;
            num++;
        } // while
        down--;

        while (r > up) {
            r--;
            fprintf(stderr, "up, num: %d; %d %d\n", num, r, c); // debug
            res[r * n + c] = num;
            num++;
        } // while
        left++;
    } // while

    fprintf(stderr, "out of loop\n"); // debug


    *returnSize = n;

    *returnColumnSizes = malloc(n * sizeof(int));

    for (int i = 0; i < n; i++) {
        fprintf(stderr, "writing to out array of sizes\n"); // debug
        (*returnColumnSizes)[i] = n;
    } // for i

    return res;
} // generateMatrix()


