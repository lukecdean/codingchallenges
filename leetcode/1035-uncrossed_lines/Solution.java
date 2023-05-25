class Solution {
    public int maxUncrossedLines(int[] a, int[] b) {
        // the locations of integers in the b array
        // saves search time later
        Map<Integer, List<Integer>> numlocs = new HashMap<>();
        for (int j = 0; j < b.length; j++) {
            if (!numlocs.containsKey(b[j])) {
                numlocs.put(b[j], new ArrayList<Integer>());
            } // if

            numlocs.get(b[j]).add(j);
        } // for j

        return uncrossed(new HashMap<Integer, Integer>(), numlocs, a, b, 0, 0);
    } // mUL()

    // returns the max number of uncrossed lines after a[i] b[j] 
    private int uncrossed(Map<Integer, Integer> uncrossed, Map<Integer, List<Integer>> numLocs,
            int[] a, int[] b, int i, int j)
    {
        System.out.printf("called uncr: i: %d, j: %d\n", i, j); // debug
        if (i >= a.length || j >= b.length) {
            return 0;
        } if (uncrossed.containsKey(key(i, j))) {
            return uncrossed.get(key(i, j));
        } // if

        int maxUncrossed = 0;
        // int maxUncrossedi = -1, maxUncrossedj = -1; // del
        for (int ii = i; ii < a.length; ii++) {
            int iiNum = a[ii];
            if (!numLocs.containsKey(iiNum)) {
                // nothing to draw a line to
                continue;
            } // if

            int numLocsPtr = 0; // first loc of a[ii] in b
            // find the first loc of the corresponding a[ii] in b after j
            // this will be the only loc to draw a line to if a line is to be drawn
            while (numLocsPtr < numLocs.get(iiNum).size() && numLocs.get(iiNum).get(numLocsPtr) < j) {
                numLocsPtr++;
            } // while

            if (numLocsPtr >= numLocs.get(iiNum).size()) {
                // no valid index in b to draw to
                continue;
            } // if

            int jj = numLocs.get(iiNum).get(numLocsPtr);
           
            // evaluate if it is worth it to draw a line here
            // cross at current indeces
            int currUncrossed = 1;
            // add the number of uncrossed beyond
            currUncrossed += uncrossed(uncrossed, numLocs, a, b, ii + 1, jj + 1);

            if (maxUncrossed < currUncrossed) {
                maxUncrossed = currUncrossed;
                //maxUncrossedi = ii; // del
                //maxUncrossedj = jj; // del
            } // if
        } // for ii

        //uncrossed.put(key(maxUncrossedi, maxUncrossedj), maxUncrossed);
        System.out.printf("put i: %d, j: %d, val: %d\n", i, j, maxUncrossed); // debug
        uncrossed.put(key(i, j), maxUncrossed);

        return maxUncrossed;
    } // uncrossed()

    private int key(int i, int j) {
        return i << 9 & j;
    } // key()
} // class
