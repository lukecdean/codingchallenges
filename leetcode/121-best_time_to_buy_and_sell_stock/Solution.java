class Solution {
    // 79/19 @ 2ms
    public int maxProfit(int[] prices) {
        if (prices.length == 0) {
            return 0;
        } // if
        int l = 0;
        int r = 0;
        // int res = (profit(l, r) > 0) ? profit(l, r) : 0;
        int res = 0;
        while (r < prices.length - 1) {
            r++;
            if (prices[r] < prices[l]) {
                l = r;
            } // if
            if (profit(prices, l, r) > res) {
                res = profit(prices, l, r);
            } // if
        } // while
        return res;
    }
    private int profit(int[] prices, int l, int r) {
        return prices[r] - prices[l];
    } // profit()
}
