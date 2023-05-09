// 48/61 @ 20ms
int diagonalSum(int** mat, int matSize, int* matColSize){
    int sum = 0;

    for (int i = 0; i < matSize; i++) {
        //     primary d   secondary d
        sum += mat[i][i] + mat[matSize - 1 - i][i];
    } // for

    if (matSize & 1) {
        // if odd, there is a center ie a double count
        sum -= mat [matSize / 2][matSize / 2];
    } // if

    return sum;
} // dS()
