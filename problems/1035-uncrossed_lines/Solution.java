class Solution {
    public int maxUncrossedLines(int[] a, int[] b) {
        // the locations of integers in the b array
        // saves search time later
        Map<Integer, List<Integer>> numlocs = new HashMap<>;
        for (int j = 0; j < b.length; j++) {
            if (!numlocs.containsKey(b[j])) {
                numlocs.put(b[j], new ArrayList<Integer>);
            } // if

            numlocs.get(b[j]).add(j);
        } // for j

        return uncrossed(new HashMap<Integer, Integer>, numlocs, a, b, 0, 0);
    } // mUL()

    // returns the max number of uncrossed lines after a[i] b[j] 
    private int uncrossed(Map<Integer, Integer> uncrossed, Map<Integer, List<Integer>> numlocs,
            int[] a, int[] b, int i, int j)
    {

    } // uncrossed()
} // class
