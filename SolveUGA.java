class SolveUGA {
    public boolean validMountainArray(int[] arr) {
        int i = 1;
        while (i < arr.length) {
            if (arr[i] < arr[i - 1])
                break;
            i++;
        } // while
        if (arr[i] > arr[i - 1])
            return false;
        while (i < arr.length) {
            if (arr[i] > arr[i -1])
                return false;
            i++;
        } // while
        return true;
    } // vMA()
    public boolean validMountainArray(int[] arr) {
	int  hasPeaked = 0;
	int i  =  0;
	while (i < arr.length) {
		if (arr[i] > arr[i-1]) {
			hasPeaked++;
		}
		else if (arr[i] == arr[i-1]) {
			return false;
		}
		else if (arr[i] < arr[i-1]) {
			break;
		}
		i++;
	}
	if (hasPeaked < 1) {
		return false;
	}
	while  (i < arr.length) {
		if (arr[i] > arr[i-1]) {
			return false;
		}
	}
	return true;
    } // validMountainArray()
    // 9-23-22
    // Two Knights
    public int twoKnights(int k) {
        int res = 0;
        int sqr = 1;
        for (int r = 0; r < k; r++) {
            for (int c = 0; c < k; c++) {
                // add to res: the total number of squares on the board minus
                // the spaces where a knight is attacking minus
                // the spaces already checked
                int openSquares = (k * k) - possibleJumps(r, c, k) - sqr;
                res += openSquares;
                sqr++;
            } // for c
        } // for r
        return res;
    } // twoKnights()
    private int possibleJumps(int r, int c, int k) {
        int ct = 0;

        if (((k - 1) - c) >= 1) {
            if (r >= 2) ct++;
            if (((k - 1) - r) >= 2) ct++;

            if (((k - 1) - c) >= 2) {
                if (r >= 1) ct++;
                if (((k - 1) - r) >= 1) ct++;
            } // if
        } // if

        return ct;
    } // possibleJumps()
} // class
