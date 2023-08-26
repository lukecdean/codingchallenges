// Luke Dean 2023-8-25
class Solution {
    // 99/72 @ 8ms
    public int findLongestChain(int[][] pairs) {
        if (pairs.length < 1) {
            return 0;
        } // if
        // sort pairs by left
        Arrays.sort(pairs, (int[] a, int[] b) -> a[0] - b[0]);

        int[] previousPair = pairs[0];
        int chainLength = 1;

        int[] currentPair;
        for (int p = 1; p < pairs.length; p++) {
            currentPair = pairs[p];
            if (currentPair[0] <= previousPair[1]) {
                // if current does not follow previous
                if (currentPair[1] < previousPair[1]) {
                    // if currentPair ends earlier than previousPair,
                    // use it instead
                    previousPair = currentPair;
                } // if
                // else, discard
            } else {
                // it follows
                previousPair = currentPair;
                chainLength++;
            } // if
        } // for pair

        return chainLength;
    }
}
