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
} // class
