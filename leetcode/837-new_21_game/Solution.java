class Solution {
    // This sol errs by assuming all paths to a point are equally likely
    public double new21Game0(int n, int k, int maxPts) {
        if (n >= k - 1 + maxPts) {
            // n is the max possible score
            return 1;
        } // if

        long[] paths = new long[k + maxPts];

        // the first maxPts nums have an automatic +1 paths to be rolled
        // ie paths after the first roll
        for (int i = 1; i <= maxPts; i++) {
            paths[i] = 1;
        } // for i

        for (int i = 1; i < k; i++) {
            for (int j = 1; j <= maxPts; j++) {
                paths[i + j] += paths[i];
            } // for j
        } // for i

        for (int i = 0; i < paths.length; i++) {
            System.out.printf("%d: %d\n", i, paths[i]); // debug
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
