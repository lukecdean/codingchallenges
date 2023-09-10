class Solution {
    // 5/99 @ 1926ms
    public int countOrders(int n) {
        int res = 1;
        int resMod = 1_000_000_000 + 7;

        // for each order, pick two spots out of the total 'action spots'
        // there are 2 action spots for each order => n * 2
        for (int i = n; i > 1; i--) {
            // Gaussian n pick 2 equation
            int waysToPickNextOrder = ((i * 2) * ((i * 2) - 1)) / 2;
            int nextRes = 0;
            for (int j = 0; j < waysToPickNextOrder; j++) {
                nextRes += res;
                nextRes %= resMod;
            } // for j
            res = nextRes;
        } // for i

        return res;
    } // countOrders()
} // class Solution
