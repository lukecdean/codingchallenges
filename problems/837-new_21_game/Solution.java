class Solution {
    public double new21Game(int n, int k, int maxPts) {
        if (n == k - 1 + maxPts) {
            // n is the max possible score
            return 1;
        } // if

        int[] paths = new int[k + maxPts];

        for (int i = 0; i < k; i++) {
            for (int j = 1; j <= maxPts; j++) {
                paths[i + j] += paths[i] + 1;
            } // for j
            //System.out.printf("%d,", paths[i]); // debug
        } // for i

        long totalPathsToAnEnd = 0;
        long totalPathsToAnEndUpToN = 0;
        for (int i = k; i < paths.length; i++) {
            totalPathsToAnEnd += paths[i];
            if (i == n) {
                totalPathsToAnEndUpToN = totalPathsToAnEnd;
            } // if
        } // for i

        System.out.printf("end: %d, endN: %d", totalPathsToAnEnd, totalPathsToAnEndUpToN); // debug
        return (double) totalPathsToAnEndUpToN / (double) totalPathsToAnEnd;
    } // new21Game()
}
