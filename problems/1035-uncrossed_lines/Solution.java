class Solution {
    public int maxUncrossedLines(int[] a, int[] b) {
        // dp[i][j] is the num of uncrossed lines inclusively after a[i] b[j]
        int[][] dp = new int[a.length][b.length];
        for (int r = 0; r < a.length; r++) {
            for (int c = 0; c < b.length; c++) {
                dp[r][c] = -1;
            } // for c
        } // for r

        

        // the locations of integers in the b array
        Map<Integer, List<Integer>> numlocs = new HashMap<>;
        for (int j = 0; j < b.length; j++) {
            if (!numlocs.containsKey(b[j])) {
                numlocs.put(b[j], new ArrayList<Integer>);
            } // if

            numlocs.get(b[j]).add(j);
        } // for j

    } // mUL()

    private int uncrossed(int[][] dp, int[] a, int[] b, int i, int j) {

    } // uncrossed()
} // class
